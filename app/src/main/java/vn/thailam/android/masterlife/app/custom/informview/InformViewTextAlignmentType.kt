package vn.thailam.android.masterlife.app.custom.informview

import androidx.annotation.IntDef
import vn.thailam.android.masterlife.app.custom.informview.InformViewTextAlignmentType.Companion.CENTER
import vn.thailam.android.masterlife.app.custom.informview.InformViewTextAlignmentType.Companion.LEFT

@IntDef(LEFT, CENTER)
@Retention(AnnotationRetention.SOURCE)
annotation class InformViewTextAlignmentType {
    companion object {
        const val LEFT = 0
        const val CENTER = 1
    }
}
