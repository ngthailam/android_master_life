package vn.thailam.android.masterlife.app.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import java.util.*

fun Activity.hideKeyboard() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(window?.decorView?.windowToken, 0)
}

fun View.hideKeyboard() {
    (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(windowToken, 0)
}

fun getRoundShapeDrawable(cornerRadius: Float, @ColorInt color: Int): Drawable {
    val outerRadii = FloatArray(8)
    Arrays.fill(outerRadii, cornerRadius)

    val r = RoundRectShape(outerRadii, null, null)
    val shapeDrawable = ShapeDrawable(r)
    shapeDrawable.paint.color = color
    return shapeDrawable
}
