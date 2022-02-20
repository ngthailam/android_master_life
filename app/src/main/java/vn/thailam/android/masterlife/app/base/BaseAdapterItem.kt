package vn.thailam.android.masterlife.app.base

interface AdapterItem {
    fun getViewType(): Int
    fun areItemTheSame(other: Any?): Boolean
    fun areContentsTheSame(other: Any?): Boolean
}