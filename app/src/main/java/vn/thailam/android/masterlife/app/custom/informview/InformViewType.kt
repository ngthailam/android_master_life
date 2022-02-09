package vn.thailam.android.masterlife.app.custom.informview

import androidx.annotation.IntDef
import vn.thailam.android.masterlife.app.custom.informview.InformViewType.Companion.ERROR
import vn.thailam.android.masterlife.app.custom.informview.InformViewType.Companion.INFO
import vn.thailam.android.masterlife.app.custom.informview.InformViewType.Companion.SUCCESS
import vn.thailam.android.masterlife.app.custom.informview.InformViewType.Companion.WARNING

@IntDef(INFO, SUCCESS, WARNING, ERROR)
@Retention(AnnotationRetention.SOURCE)
annotation class InformViewType {
    companion object {
        const val INFO = 0
        const val SUCCESS = 1
        const val WARNING = 2
        const val ERROR = 3
    }
}
