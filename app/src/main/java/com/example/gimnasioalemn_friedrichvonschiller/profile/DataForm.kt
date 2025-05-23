package com.example.gimnasioalemn_friedrichvonschiller.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.gimnasioalemn_friedrichvonschiller.databinding.FragmentDataFormBinding

class DataForm : Fragment() {
    private var _binding: FragmentDataFormBinding? = null
    private val binding get() = _binding!!

    private var userRole: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDataFormBinding.inflate(inflater, container, false)

        loadUserData()
        setupListeners()

        return binding.root
    }

    private fun loadUserData() {
        val sharedPreferences =
            requireContext().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)

        binding.userName.text = sharedPreferences.getString("USER_NAME", "")
        binding.userId.text = sharedPreferences.getString("USER_ID", "")
        binding.userEmail.text = sharedPreferences.getString("USER_EMAIL", "")
        binding.userPhoneNumber.text = sharedPreferences.getString("USER_PHONE_NUMBER", "")
        binding.userGrade.text = sharedPreferences.getString("USER_GRADE", "")

        userRole = sharedPreferences.getString("USER_ROL", "")
        binding.layoutGrade.isVisible = userRole == "student"
    }

    private fun setupListeners() {
        binding.btnClose.setOnClickListener {
            closeDataForm()
        }
    }

    private fun closeDataForm() {
        binding.root.isEnabled = true
        binding.root.visibility = View.GONE

        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
