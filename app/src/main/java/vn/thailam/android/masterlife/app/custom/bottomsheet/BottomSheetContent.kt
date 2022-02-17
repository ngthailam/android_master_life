package vn.thailam.android.masterlife.app.custom.bottomsheet

import android.app.Dialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface BottomSheetContent {
    fun onRightIconClick() = Unit
    fun onRightTextClick() = Unit
    fun parentBottomSheet(callBackContent: BottomSheetCallback?) = Unit
    fun onDismissed(bottomSheet: BottomSheetDialogFragment?) = Unit
    fun setBottomSheetDialog(dialog: Dialog?) = Unit
}

interface BottomSheetLeftIconContent {
    fun leftIconContent(callBackContent: BottomSheetLeftIconCallBack?) = Unit
}
