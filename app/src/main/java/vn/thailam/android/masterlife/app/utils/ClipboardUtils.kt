package vn.thailam.android.masterlife.app.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


fun Context.copyToClipboard(text: String) {
    val clipboard =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText("", text)
    clipboard?.setPrimaryClip(clip)
}