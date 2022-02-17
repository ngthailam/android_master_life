package vn.thailam.android.masterlife.app.custom.bottomsheet

import androidx.annotation.IntDef
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface BottomSheetListener {
    companion object {
        const val DISMISS_EVENT_SWIPE = -4

        const val DISMISS_EVENT_MANUAL = -5

        const val DISMISS_EVENT_ITEM_SELECTED = -6

        const val DISMISS_EVENT_IN_CONTENT = -7
    }

    @IntDef(DISMISS_EVENT_MANUAL, DISMISS_EVENT_SWIPE, DISMISS_EVENT_IN_CONTENT)
    annotation class DismissEvent

    fun onSheetShown(bottomSheet: BottomSheetDialogFragment, item: Any?)
    fun onSheetDismissed(item: Any?, @DismissEvent dismissEvent: Int) = Unit
    fun onSheetEvent(item: Any?) = Unit
    fun onSheetItemSelected(item: Any?) = Unit
    fun onNavigationBarLeftClick() = Unit
    fun onNavigationBarRightClick() = Unit
}

interface BottomSheetOnClickListener {
    fun onLeftIconClick()
}
