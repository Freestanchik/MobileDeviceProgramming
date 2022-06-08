package com.example.dialogapp

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dialogapp.receivers.AirplaneModeReceiver
import com.example.dialogapp.receivers.BatteryReceiver
import com.example.dialogapp.receivers.CameraButtonReceiver

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Log.d(MainActivity::class.simpleName, "Permission result: $it")
        }

    private val airplaneReceiver = AirplaneModeReceiver()
    private val batteryReceiver = BatteryReceiver()
    private val cameraButtonReceiver = CameraButtonReceiver()

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_LOW)
        val cameraButtonFilter = IntentFilter(Intent.ACTION_CAMERA_BUTTON)

        registerReceiver(airplaneReceiver, filter)
        registerReceiver(batteryReceiver, batteryFilter)
        registerReceiver(cameraButtonReceiver, cameraButtonFilter)

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(airplaneReceiver)
        unregisterReceiver(batteryReceiver)
        unregisterReceiver(cameraButtonReceiver)
    }

    override fun onResume() {
        super.onResume()
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

}