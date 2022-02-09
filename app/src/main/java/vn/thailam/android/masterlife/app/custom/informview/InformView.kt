package vn.thailam.android.masterlife.app.custom.informview

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.databinding.InformViewBinding

/**
 * - This custom view includes collapse animation when pressing on dismiss icon.
 * - For smooth animation effect, please add [View] or [Space] to add spacing top to
 * this view if needed, instead of using marginTop
 */
class InformView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes),
    ViewTreeObserver.OnPreDrawListener {

    private var flagPreDraw: Boolean = false

    /**
     * message text to be set for [tvMessage].
     * this is required field. If it is not set in xml, the system will throw [IllegalArgumentException]
     */
    private var _message: CharSequence? = null
    var message: CharSequence?
        get() = _message
        set(value) {
            _message = value
            binding.tvMessage.text = _message
            flagPreDraw = true
            this.binding.tvMessage.viewTreeObserver.addOnPreDrawListener(this)
        }

    override fun onPreDraw(): Boolean {
        if (flagPreDraw) {
            flagPreDraw = false
            this.binding.tvMessage.viewTreeObserver.removeOnPreDrawListener(this)
            setLayoutPadding()
        }
        return true
    }

    /**
     * style of inform view
     * there are 4 styles of inform view:
     *  - [InformViewType.INFO]
     *  - [InformViewType.SUCCESS]
     *  - [InformViewType.WARNING]
     *  - [InformViewType.ERROR]
     * by default, the style will be [InformViewType.INFO]
     * this is required field. If it is not set in xml, the system will throw [IllegalArgumentException]
     */
    @InformViewType
    private var _style: Int = InformViewType.INFO

    @InformViewType
    var style: Int
        get() = _style
        set(value) {
            _style = value
            setInformViewStyle()
            setupIconInfo()
        }

    /**
     * action icon resource id to set for [btnAction]
     * if resource cannot be found, then action icon's visibility of [btnAction] is gone
     */
    private var _isIconHidden: Boolean = false
    var isIconHidden: Boolean
        get() = _isIconHidden
        set(value) {
            _isIconHidden = value
            setupIconInfo()
        }

    /**
     * text alignment of [message]
     * there are 2 type of text alignment:
     *  - [InformViewTextAlignmentType.LEFT]
     *  - [InformViewTextAlignmentType.CENTER]
     * by default, the text alignment will be [InformViewTextAlignmentType.LEFT]
     * this is required field. If it is not set in xml, the system will throw [IllegalArgumentException]
     */
    @InformViewTextAlignmentType
    private var _informTextAlignment: Int = InformViewTextAlignmentType.LEFT

    @InformViewTextAlignmentType
    var informTextAlignment: Int
        get() = _informTextAlignment
        set(value) {
            _informTextAlignment = value
            binding.tvMessage.gravity = when (_informTextAlignment) {
                InformViewTextAlignmentType.CENTER -> Gravity.CENTER
                else -> Gravity.START
            }
        }

    /**
     * action icon resource id to set for [btnAction]
     * if resource cannot be found, then action icon's visibility of [btnAction] is gone
     */
    private var _canDismiss: Boolean = false
    var canDismiss: Boolean
        get() = _canDismiss
        set(value) {
            _canDismiss = value
            binding.ivDismiss.visibility = when (_canDismiss) {
                true -> View.VISIBLE
                false -> View.GONE
            }
            flagPreDraw = true
            this.binding.tvMessage.viewTreeObserver.addOnPreDrawListener(this)
        }

    /**
     * icon info resource id to set for [ivInfo]
     * if resource cannot be found, then icon info's visibility of [ivInfo] is gone
     */
    @DrawableRes
    private var _infoIcon: Int? = null
    var infoIcon: Int?
        @DrawableRes
        get() = _infoIcon
        set(@DrawableRes value) {
            _infoIcon = value
            setupIconInfo()
        }

    var dsInformViewDismissListener: InformViewDismissListener? = null

    var binding: InformViewBinding = InformViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        val typeArr = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.InformView,
            defStyleAttr,
            defStyleRes
        )

        try {
            message = typeArr.getString(R.styleable.InformView_iv_message)

            style = typeArr.getInteger(R.styleable.InformView_iv_style, InformViewType.INFO)

            isIconHidden = typeArr.getBoolean(R.styleable.InformView_iv_isIconHidden, false)

            informTextAlignment = typeArr.getInteger(
                R.styleable.InformView_iv_textAlignment,
                InformViewTextAlignmentType.LEFT
            )

            canDismiss = typeArr.getBoolean(R.styleable.InformView_iv_canDismiss, false)
        } finally {
            typeArr.recycle()
        }

        initDefault()
    }

    private fun initDefault() {
        this.minHeight = resources.getDimensionPixelOffset(R.dimen.inform_view_min_height)
        binding.ivDismiss.setOnClickListener {
            val flag = dsInformViewDismissListener?.onViewDismissListener(this) ?: false
            if (!flag) {
                this.collapse()
            }
        }
        setInformViewStyle()

        if (isInEditMode) {
            setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.dp_12))
            return
        }
    }

    private fun setupIconInfo() {
        if (_isIconHidden) {
            binding.ivInfo.visibility = View.GONE
            return
        }
        binding.ivInfo.visibility = View.VISIBLE
        setupImageResourceIconInfo()
        tintIconInfoColor()
    }

    private fun setupImageResourceIconInfo() {
        if (_infoIcon != null) {
            binding.ivInfo.setImageResource(requireNotNull(_infoIcon))
        } else {
            binding.ivInfo.setImageResource(
                when (_style) {
                    InformViewType.SUCCESS -> R.drawable.ic_16inline_check_circle
                    else -> R.drawable.ic_16inline_alert_circle
                }
            )
        }
    }

    private fun tintIconInfoColor() {
        binding.ivInfo.imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                when (_style) {
                    InformViewType.SUCCESS -> R.color.green500
                    InformViewType.WARNING -> R.color.orange500
                    InformViewType.ERROR -> R.color.red500
                    else -> R.color.blue500
                }
            )
        )
    }

    private fun setInformViewStyle() {
        when (_style) {
            InformViewType.SUCCESS -> setSuccessStyle()
            InformViewType.WARNING -> setWarningStyle()
            InformViewType.ERROR -> setErrorStyle()
            else -> setInfoStyle()
        }
    }

    private fun setInfoStyle() {
        background = ContextCompat.getDrawable(context, R.drawable.bg_inform_container_info)
    }

    private fun setErrorStyle() {
        background = ContextCompat.getDrawable(context, R.drawable.bg_inform_container_error)
    }

    private fun setWarningStyle() {
        background = ContextCompat.getDrawable(context, R.drawable.bg_inform_container_warning)
    }

    private fun setSuccessStyle() {
        background = ContextCompat.getDrawable(context, R.drawable.bg_inform_container_success)
    }

    private fun setLayoutPadding() {
        setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.dp_12))
    }
}

private fun View.collapse() {
    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) visibility = View.GONE
            layoutParams.height = measuredHeight - (measuredHeight * interpolatedTime).toInt()
            requestLayout()
        }

        override fun willChangeBounds(): Boolean = true
    }
    animation.duration = 250L
    startAnimation(animation)
}