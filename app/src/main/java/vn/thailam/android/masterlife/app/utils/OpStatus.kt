package vn.thailam.android.masterlife.app.utils

sealed class OpStatus {
    object Loading: OpStatus()
    object Success : OpStatus()
    data class Error(val e: Throwable) : OpStatus()
}