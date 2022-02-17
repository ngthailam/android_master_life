package vn.thailam.android.masterlife.app.custom.inputspinner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.databinding.ViewInputSpinnerBinding

class InputSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var _minValue: Int = 0
    var minValue: Int
        get() = _minValue
        set(value) {
            _minValue = value
            checkValue(_minValue, _maxValue)
        }

    private var _maxValue: Int = MAX_VALUE_DEFAULT
    var maxValue: Int
        get() = _maxValue
        set(value) {
            _maxValue = if (value > MAX_INPUT_VALUE) MAX_INPUT_VALUE else value
            checkValue(_minValue, _maxValue)
        }
    var isIncreaseDisableOnMax: Boolean = true
        set(value) {
            if (!isEnabled) return
            field = value
            updateButtonView()
        }
    var isDecreaseDisableOnMin: Boolean = true
        set(value) {
            if (!isEnabled) return
            field = value
            updateButtonView()
        }

    var inputSpinnerStyle: InputSpinnerStyle = InputSpinnerStyle.SMALL
        set(value) {
            if (!isEnabled) return
            field = value
            setupInputSpinnerStyle(value)
            updateButtonView()
        }

    var isAutoUpdateValue: Boolean = true
    private var valueDidChange: ((newValue: Int, delta: Int) -> Unit)? = null
    private var didTapChangeButton: ((newValue: Int, delta: Int) -> Unit)? = null
    var value: Int = 0
        set(value) {
            if (!isEnabled) return
            val oldValue = field
            val validValue =
                when {
                    value >= maxValue -> maxValue
                    value <= minValue -> minValue
                    else -> value
                }
            field = validValue
            valueDidChange?.invoke(validValue, validValue - oldValue)
            updateButtonView()
        }

    private var ink1Color: Int = 0
    private var ink2Color: Int = 0
    private var ink3Color: Int = 0
    private var ink4Color: Int = 0
    private var blue500: Int = 0
    private var blue200: Int = 0

    var binding: ViewInputSpinnerBinding =
        ViewInputSpinnerBinding.inflate(LayoutInflater.from(context), this)

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.InputSpinner, 0, 0)
        minValue = typeArray.getInt(R.styleable.InputSpinner_ips_min, 0)
        maxValue = typeArray.getInt(R.styleable.InputSpinner_ips_max, MAX_VALUE_DEFAULT)

        isIncreaseDisableOnMax =
            typeArray.getBoolean(R.styleable.InputSpinner_ips_is_increase_disable_on_max, true)
        isDecreaseDisableOnMin =
            typeArray.getBoolean(R.styleable.InputSpinner_ips_is_decrease_disable_on_min, true)
        value = typeArray.getInt(R.styleable.InputSpinner_ips_value, 0)
        isAutoUpdateValue =
            typeArray.getBoolean(R.styleable.InputSpinner_ips_is_auto_update_value, true)
        inputSpinnerStyle =
            InputSpinnerStyle.values()[typeArray.getInt(R.styleable.InputSpinner_ips_style, 0)]
        setupInputSpinnerStyle(inputSpinnerStyle)
        typeArray.recycle()
        binding.ivDecrease.setOnClickListener {
            didTapChangeButton?.invoke(value - 1, -1)
            decreaseValue()
        }
        binding.ivIncrease.setOnClickListener {
            didTapChangeButton?.invoke(value + 1, 1)
            increaseValue()
        }
        ink1Color = ContextCompat.getColor(context, R.color.ink500)
        ink2Color = ContextCompat.getColor(context, R.color.ink400)
        ink3Color = ContextCompat.getColor(context, R.color.ink300)
        ink4Color = ContextCompat.getColor(context, R.color.ink200)
        blue500 = ContextCompat.getColor(context, R.color.blue500)
        blue200 = ContextCompat.getColor(context, R.color.blue200)
        updateButtonView()
    }

    private fun checkValue(min: Int, max: Int) {
        if (value in (min + 1) until max) {
            return
        } else {
            if (value <= min) {
                value = min
            }

            if (value >= max) {
                value = max
            }
        }
    }

    fun setValueDidChangeListener(listener: ((newValue: Int, delta: Int) -> Unit)? = null) {
        this.valueDidChange = listener
    }

    fun setDidTapChangeButtonListener(listener: ((newValue: Int, delta: Int) -> Unit)? = null) {
        this.didTapChangeButton = listener
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        resetButtonColor()
        if (!isEnabled) {
            binding.ivDecrease.background.setTint(ink4Color)
            setImageSrcTint(binding.ivDecrease, ink4Color)
            binding.ivIncrease.background.setTint(ink4Color)
            setImageSrcTint(binding.ivIncrease, ink4Color)
            binding.tvValue.background.setTint(ink4Color)
            binding.tvValue.setTextColor(ink2Color)
        }
    }

    /**
     * Update style [small, medium, large] for input spinner
     */
    private fun setupInputSpinnerStyle(style: InputSpinnerStyle) {
        val actionSize = context.resources.getDimensionPixelSize(
            when (style) {
                InputSpinnerStyle.SMALL -> R.dimen.ips_small_action_size
                InputSpinnerStyle.MEDIUM -> R.dimen.ips_medium_action_size
                else -> R.dimen.ips_large_action_size
            }
        )
        binding.ivDecrease.layoutParams.apply {
            height = actionSize
            width = actionSize
            binding.ivDecrease.layoutParams = this
        }
        binding.ivIncrease.layoutParams.apply {
            height = actionSize
            width = actionSize
            binding.ivIncrease.layoutParams = this
        }
        val centerContentMargin = context.resources.getDimensionPixelSize(
            when (style) {
                InputSpinnerStyle.MEDIUM -> R.dimen.dp_4
                else -> R.dimen.dp_8
            }
        )
    }

    /**
     * Handle increasing button
     */
    private fun increaseValue() {
        if (!isEnabled) return
        if (value >= maxValue) {
            if (!isIncreaseDisableOnMax) {
                valueDidChange?.invoke(value, 0)
            }
            return
        }
        val newValue = value + 1
        valueDidChange?.invoke(newValue, 1)
        if (isAutoUpdateValue) {
            value++
            updateButtonView()
        }
    }

    /**
     * Handle decreasing button
     */
    private fun decreaseValue() {
        if (!isEnabled) return
        if (value <= minValue) {
            if (!isDecreaseDisableOnMin) {
                valueDidChange?.invoke(value, 0)
            }
            return
        }
        val newValue = value - 1
        valueDidChange?.invoke(newValue, -1)
        if (isAutoUpdateValue) {
            value--
            updateButtonView()
        }
    }

    /**
     * Refresh button when increasing or decreasing
     */
    private fun updateButtonView() {
        binding.tvValue.text = value.toString()
        resetButtonColor()
        if (isEnabled && value <= minValue && isDecreaseDisableOnMin) {
            binding.ivDecrease.background.setTint(ink4Color)
            setImageSrcTint(binding.ivDecrease, ink4Color)
        }
        if (isEnabled && value >= maxValue && isIncreaseDisableOnMax) {
            binding.ivIncrease.background.setTint(ink4Color)
            setImageSrcTint(binding.ivIncrease, ink4Color)
        }
    }

    private fun resetButtonColor() {
        binding.ivDecrease.background.setTint(blue200)
        setImageSrcTint(binding.ivDecrease, blue500)
        binding.ivIncrease.background.setTint(blue200)
        setImageSrcTint(binding.ivIncrease, blue500)
        binding.tvValue.setTextColor(ink1Color)
    }

    private fun setImageSrcTint(imageView: ImageView, color: Int) {
        imageView.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
    }

    companion object {
        private const val MAX_VALUE_DEFAULT = 99
        private const val MAX_INPUT_VALUE = 999 // limit maximum input is 3 number
    }
}

enum class InputSpinnerStyle { SMALL, MEDIUM, LARGE }
