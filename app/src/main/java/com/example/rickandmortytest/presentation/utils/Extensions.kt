package com.example.rickandmortytest.presentation.utils

import android.app.Dialog
import android.content.Context
import android.view.View

fun View.initDialog(context: Context): Dialog {
    val dialog = Dialog(context)
    dialog.setContentView(this)
    dialog.setCancelable(true)
    return dialog
}