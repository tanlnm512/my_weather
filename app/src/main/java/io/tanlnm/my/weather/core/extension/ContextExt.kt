@file:JvmMultifileClass
@file:JvmName("ContextExt")

package io.tanlnm.my.weather.core.extension

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createLoadingDialog(@LayoutRes layout: Int, cancellable: Boolean = false): AlertDialog {
    val dialog = MaterialAlertDialogBuilder(this).apply {
        setCancelable(cancellable)
        setView(layout)
    }.create()

    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}

fun Context.showToast(message: String?) {
    Toast.makeText(this, message ?: "Error!", Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String?) {
    Toast.makeText(this, message ?: "Error!", Toast.LENGTH_LONG).show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}