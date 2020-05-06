package com.example.todoapp.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.todoapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d("Firebase",p0?.from)
        Log.d("Firebase", p0?.data.toString())
        setUpNotofication(p0?.notification?.body)

    }

    private fun setUpNotofication(message: String?) {
        val channelId ="Todo Id"
        val ringtone= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification=NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Todo Notes App")
                .setContentText(message)
                .setSound(ringtone)
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            val channel=NotificationChannel(channelId,"ToDo-App",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0,notification.build())

    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}