package com.example.notificationapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class AirplaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val builder = context?.let {
                NotificationCompat.Builder(it, "2")
                    .setContentTitle("AirplaneMode INFO")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }

            if (intent.getBooleanExtra("state", false)
            ) {
                builder?.setSmallIcon(com.google.android.material.R.drawable.ic_mtrl_checked_circle)
                builder?.setContentText("AirplaneMode active")
            } else {
                builder?.setSmallIcon(com.google.android.material.R.drawable.ic_mtrl_chip_close_circle)
                builder?.setContentText("AirplaneMode inactive")
            }

            context?.let {
                NotificationManagerCompat.from(it).apply {
                    builder?.let { it1 -> this.notify(Random.nextInt(), it1.build()) }
                }
            }
        }
    }
}