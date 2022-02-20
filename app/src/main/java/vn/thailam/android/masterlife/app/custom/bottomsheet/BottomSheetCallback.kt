package vn.thailam.android.masterlife.app.custom.bottomsheet

import androidx.annotation.DrawableRes

interface BottomSheetCallback {
    fun onSheetEvent(item: Any?) = Unit
    fun setDividerVisible(isVisible: Boolean? = null) = Unit
    fun setNavigationStatus(title: String? = null, description: String? = null) = Unit
    fun setNavigationIcon(@DrawableRes icon: Int?) = Unit
    fun setButtonRightStatus(isVisible: Boolean?, title: String? = null) = Unit
    fun onDismiss() = Unit
}

interface BottomSheetLeftIconCallBack {
    fun setLeftIcon(@DrawableRes leftIcon: Int)
}
