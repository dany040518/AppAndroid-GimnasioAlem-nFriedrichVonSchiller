package com.example.gimnasioalemn_friedrichvonschiller;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
/*
class MyFirebaseMessagingService : FirebaseMessagingService() {
/*
    override fun onMessageReceived(remoteMessage:RemoteMessage) {
        remoteMessage.notification?.let {
            showNotification(it.title, it.body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendTokenToServer(token)
    }

    private fun showNotification(title: String?, body: String?) {
        // Construye y muestra notificación usando NotificationManager
    }

    fun sendTokenToServer(token: String) {
        val userId = getCurrentUserId() // Método para obtener id de usuario logueado
        val db = Firebase.database.reference
        db.child("users").child(userId).child("fcmToken").setValue(token)
    }

}*/