package com.example.dialogapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.dialogapp.DialogActivity

class CameraButtonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_CAMERA_BUTTON) {
            val i = Intent(context, DialogActivity::class.java)
            i.putExtra("dialog_id", 3)
            context?.startActivity(i)
        }
    }
}