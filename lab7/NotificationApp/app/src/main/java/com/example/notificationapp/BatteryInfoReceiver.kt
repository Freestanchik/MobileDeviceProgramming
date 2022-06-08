package com.example.notificationapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class BatteryInfoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_LOW) {
            val builder = context?.let {
                NotificationCompat.Builder(it, "1")
                    .setSmallIcon(com.google.android.material.R.drawable.ic_clock_black_24dp)
                    .setContentTitle("Battery info")
                    .setContentText("LOW BATTERY")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }

            context?.let {
                NotificationManagerCompat.from(it).apply {
                    builder?.let { it1 -> this.notify(Random.nextInt(), it1.build()) }
                }
            }
        }
    }
}