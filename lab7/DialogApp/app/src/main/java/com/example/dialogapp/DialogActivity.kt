package com.example.dialogapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.dialogapp.dialogs.AirplaneModeDialog
import com.example.dialogapp.dialogs.BatteryDialog
import com.example.dialogapp.dialogs.CameraButtonDialog

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secont_activity)
        val i = intent
        val id = i.getIntExtra("dialog_id", 0)
        val dialog: DialogFragment
        when (id) {
            1 -> {
                dialog = BatteryDialog()
                dialog.show(supportFragmentManager, "batteryDlg")
            }
            2 -> {
                dialog = AirplaneModeDialog()
                dialog.show(supportFragmentManager, "AirplaneDlg")
            }
            3 -> {
                dialog = CameraButtonDialog()
                dialog.show(supportFragmentManager, "CameraDlg")
            }
        }
    }

}

