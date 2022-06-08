package com.example.notificationapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val batteryInfoChannel =
                NotificationChannel("1", "Battery info", NotificationManager.IMPORTANCE_LOW)
            val airplaneModeChannel =
                NotificationChannel("2", "Airplane mode info", NotificationManager.IMPORTANCE_HIGH)
            val cameraButtonChannel =
                NotificationChannel("3", "Camera button info", NotificationManager.IMPORTANCE_HIGH)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(batteryInfoChannel)
            notificationManager.createNotificationChannel(airplaneModeChannel)
            notificationManager.createNotificationChannel(cameraButtonChannel)

        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Log.d(MainActivity::class.simpleName, "Permission result: $it")
        }

    private val airplaneReceiver = AirplaneModeReceiver()
    private val batteryReceiver = BatteryInfoReceiver()
    private val cameraButtonReceiver = CameraButtonReceiver()

    override fun onStart() {
        super.onStart()

        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_LOW)
        val airplaneModeFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        val cameraButtonFilter = IntentFilter(Intent.ACTION_CAMERA_BUTTON)

        registerReceiver(batteryReceiver, batteryFilter)
        registerReceiver(airplaneReceiver, airplaneModeFilter)
        registerReceiver(cameraButtonReceiver, cameraButtonFilter)

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(batteryReceiver)
        unregisterReceiver(airplaneReceiver)
        unregisterReceiver(cameraButtonReceiver)

    }

    override fun onResume() {
        super.onResume()
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}