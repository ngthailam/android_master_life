package vn.thailam.android.masterlife.app.utils

import android.view.animation.Animation

class MyAnimationListener(
    val onAnimStart: ((anim: Animation?) -> Unit)? = null,
    val onAnimEnd: ((anim: Animation?) -> Unit)? = null,
    val onAnimRepeat: ((anim: Animation?) -> Unit)? = null,
) : Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {
        onAnimStart?.invoke(p0)
    }

    override fun onAnimationEnd(p0: Animation?) {
        onAnimEnd?.invoke(p0)
    }

    override fun onAnimationRepeat(p0: Animation?) {
        onAnimRepeat?.invoke(p0)
    }
}