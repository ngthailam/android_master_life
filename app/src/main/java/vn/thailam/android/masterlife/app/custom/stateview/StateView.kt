package vn.thailam.android.masterlife.app.custom.stateview

import android.content.Context
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.databinding.StateViewBinding

class StateView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private var binding: StateViewBinding = StateViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        setupDefault()

        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.StateView,
            defStyleAttr,
            defStyleRes
        )
        try {
            val iconSrc = typedArray.getResourceId(R.styleable.StateView_sv_iconSrc, 0)
            if (iconSrc == 0) {
                binding.imgIcon.visibility = View.GONE
            } else {
                binding.imgIcon.setImageResource(iconSrc)
            }
            startAnimation()
            setTitle(typedArray.getString(R.styleable.StateView_sv_title))
            setSubtitle(typedArray.getString(R.styleable.StateView_sv_subtitle))
            setActionText(typedArray.getString(R.styleable.StateView_sv_actionText))
            setBackgroundColor(
                typedArray.getColor(
                    R.styleable.StateView_sv_backgroundColor,
                    ContextCompat.getColor(context, R.color.white500)
                )
            )
        } finally {
            // recycle arrays
            typedArray.recycle()
        }
    }

    private fun setupDefault() {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        isClickable = true

        val padding = context.resources.getDimensionPixelOffset(R.dimen.dp_16)
        setPadding(padding, padding, padding, padding)
    }

    private fun startAnimation() {
        val avd = binding.imgIcon.drawable as AnimatedVectorDrawable
        avd.registerAnimationCallback(object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable) {
                avd.start()
            }
        })
        avd.start()
        (binding.imgIcon.drawable as? AnimationDrawable)?.start()
    }

    /**
     * Set Action Listener For Clicking Action Button
     * @param callback callback action
     */
    fun setActionListener(callback: () -> Unit) {
        binding.btnAction.setOnClickListener { view ->
            view.isClickable = false
            callback()
            view.postDelayed(
                {
                    view.isClickable = true
                },
                300L
            )
        }
    }

    /**
     * Set title
     * @param text of title
     */
    fun setTitle(text: String?) {
        binding.tvTitle.text = text
        binding.tvTitle.visibility = if (text.isNullOrBlank().not()) View.VISIBLE else View.GONE
    }

    /**
     * Set sub title
     * @param text of sub title
     */
    fun setSubtitle(text: String?) {
        binding.tvSubTitle.text = text
        binding.tvSubTitle.visibility = if (text.isNullOrBlank().not()) View.VISIBLE else View.GONE
    }

    /**
     * Set Action Text
     * @param text of action button
     */
    fun setActionText(text: String?) {
        binding.btnAction.setText((text ?: "") as CharSequence)
        binding.btnAction.visibility = if (text.isNullOrBlank().not()) View.VISIBLE else View.GONE
    }

    /**
     * Set Action Button Visible Status
     * @param isVisible of action button
     */
    fun setActionVisible(isVisible: Boolean) {
        binding.btnAction.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    /**
     * Set main image visible status
     * @param isVisible of main image
     */
    fun setImageVisible(isVisible: Boolean) {
        binding.imgIcon.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    /**
     * Set background color
     * @param color of background
     */
    fun setStateBackgroundColor(@ColorInt color: Int?) {
        color?.let { setBackgroundColor(it) }
    }

    /**
     * Set resource for main image
     * @param imageRes resource of main image
     */
    fun setImage(@DrawableRes imageRes: Int) {
        binding.imgIcon.setImageResource(imageRes)
        binding.imgIcon.visibility = View.VISIBLE
        startAnimation()
    }

    /**
     * Get image view
     * @return get image view
     */
    fun getImageView(): ImageView = binding.imgIcon

    /**
     * Set Loading Image Url for main image
     * @param url of main image
     * @param placeHolderLoading resource for waiting loading
     * @param failureImageRes resource for failure loading
     */
    fun setLoadingImgUrl(
        url: String,
        @DrawableRes placeHolderLoading: Int?,
        @DrawableRes failureImageRes: Int?
    ) {
        binding.imgIcon.visibility = View.VISIBLE
        val requestHolder = Glide.with(context)
            .load(url)
        placeHolderLoading?.let {
            requestHolder.placeholder(it)
        }
        failureImageRes?.let {
            requestHolder.error(it)
        }
        requestHolder.into(binding.imgIcon)
    }
}
