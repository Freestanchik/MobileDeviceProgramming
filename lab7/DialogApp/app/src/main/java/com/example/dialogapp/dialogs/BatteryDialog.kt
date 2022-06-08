package com.example.dialogapp.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.dialogapp.MainActivity

class BatteryDialog() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle("Battery info")
            .setMessage("Low battery!")
            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                    _, _ -> goBack()
            })
            .create()
    }
    private fun goBack() {
        val i = Intent(context, MainActivity::class.java)
        startActivity(i)
    }
}