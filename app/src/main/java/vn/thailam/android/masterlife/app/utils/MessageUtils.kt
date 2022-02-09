package vn.thailam.android.masterlife.app.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String, len: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, len).show()
}