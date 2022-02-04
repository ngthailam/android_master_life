package vn.thailam.android.masterlife.app.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class SpacingItemDecoration(
    private val start: Int = 0,
    private val top: Int = 0,
    private val end: Int = 0,
    private val bottom: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(
            start,
            top,
            end,
            bottom
        )
    }
}
