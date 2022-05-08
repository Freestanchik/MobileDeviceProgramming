package com.example.circleslist

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CircleDialog : DialogFragment() {

    private var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        number = arguments?.getInt("number") as Int
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle("Your number is:")
            .setMessage(number.toString())
            .setPositiveButton("Ok", null)
            .create()
    }

    companion object {
        fun newInstance(number: Int): CircleDialog {
            val args = Bundle()
            args.putInt("number", number)
            val fragment = CircleDialog()
            fragment.arguments = args
            return fragment
        }
    }
}