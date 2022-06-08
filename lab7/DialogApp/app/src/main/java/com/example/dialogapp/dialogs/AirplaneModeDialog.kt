package com.example.dialogapp.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.dialogapp.MainActivity

class AirplaneModeDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if(Settings.System.getInt(context?.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0){
            AlertDialog.Builder(requireActivity())
                .setTitle("Airplane mode info")
                .setMessage("Airplane mode active")
                .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ -> goBack()
                })
                .create()
        }else{
            AlertDialog.Builder(requireActivity())
                .setTitle("Airplane mode info")
                .setMessage("Airplane Mode inactive")
                .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ -> goBack()
                })
                .create()
        }
    }
    private fun goBack() {
        val i = Intent(context, MainActivity::class.java)
        startActivity(i)
    }
}