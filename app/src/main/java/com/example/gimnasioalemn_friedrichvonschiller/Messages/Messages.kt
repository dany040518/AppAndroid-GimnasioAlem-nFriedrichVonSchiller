package com.example.gimnasioalemn_friedrichvonschiller.Messages

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gimnasioalemn_friedrichvonschiller.CreateMessage
import com.example.gimnasioalemn_friedrichvonschiller.database.MessagesHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityMessagesBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Message
import com.example.gimnasioalemn_friedrichvonschiller.utils.NavigationBarHelper

class Messages : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesBinding
    private lateinit var messagesHelper: MessagesHelper

    private var userRole: String? = null
    private var userGrade: String? = null
    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar helper
        messagesHelper = MessagesHelper()

        // Leer SharedPreferences aquí
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        userRole = sharedPreferences.getString("USER_ROL", null)
        userGrade = sharedPreferences.getString("USER_GRADE", null)
        userName = sharedPreferences.getString("USER_NAME", null)

        initUI()
        initComponents()
        loadMessages()
    }

    private fun initUI() {
        // Configurar el menú de navegación
        val navigationBarHelper = NavigationBarHelper(this)
        navigationBarHelper.setupNavigationBar(binding.root)

        binding.btnMessage.isVisible = userRole == "teacher"
        binding.btnMessage.isEnabled = userRole == "teacher"

        binding.tvNameGrade.isVisible = userRole == "student"
        binding.viewBlack.isVisible = userRole == "student"

        binding.tvNameGrade.text = userGrade ?: ""
    }

    private fun initComponents() {
        binding.btnMessage.setOnClickListener {
            showCreateMessageFragment()
        }
    }

    private fun showCreateMessageFragment() {
        binding.containerCreateMessage.visibility = View.VISIBLE
        binding.root.isEnabled = false

        supportFragmentManager.beginTransaction()
            .replace(binding.containerCreateMessage.id, CreateMessage())
            .addToBackStack(null)
            .commit()
    }

    private fun loadMessages() {
        when {
            userRole == "student" && !userGrade.isNullOrEmpty() -> {
                messagesHelper.getMessagesByGrade(
                    userGrade!!,
                    onResult = { messagesList -> setupRecyclerView(messagesList) },
                    onError = { showError(it) }
                )
            }
            userRole == "teacher" && !userName.isNullOrEmpty() -> {
                messagesHelper.getMessagesByTeacher(
                    userName!!,
                    onResult = { messagesList -> setupRecyclerView(messagesList) },
                    onError = { showError(it) }
                )
            }
            userRole == "admin" -> {
                messagesHelper.getAllMessages(
                    onResult = { messagesList -> setupRecyclerView(messagesList) },
                    onError = { showError(it) }
                )
            }
            else -> {
                Toast.makeText(this, "Rol o datos de usuario inválidos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(messagesList: List<Message>) {
        val adapter = MessageAdapter(messagesList)
        binding.rvMessages.layoutManager = LinearLayoutManager(this)
        binding.rvMessages.adapter = adapter
    }

    private fun showError(message: String) {
        Toast.makeText(this, "Error al cargar mensajes: $message", Toast.LENGTH_SHORT).show()
    }
}