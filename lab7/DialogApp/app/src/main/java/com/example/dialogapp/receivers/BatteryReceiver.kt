package com.example.dialogapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.dialogapp.DialogActivity

class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_LOW) {
            val i = Intent(context, DialogActivity::class.java)
            i.putExtra("dialog_id", 1)
            context?.startActivity(i)
        }
    }

}