package vn.thailam.android.masterlife.app.page.note.list.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.databinding.NoteViewBinding

interface NoteViewInterface {
    fun setNoteId(id: Int): NoteViewInterface
    fun setTitle(text: String): NoteViewInterface
    fun setDesc(text: String): NoteViewInterface
    fun setCreatedAt(text: String): NoteViewInterface
    fun setIsChecked(isChecked: Boolean): NoteViewInterface
    fun enableCheck(enable: Boolean): NoteViewInterface
    fun isPinned(pin: Boolean): NoteViewInterface
}

class NoteView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes), NoteViewInterface {
    private var binding =
        NoteViewBinding.inflate(LayoutInflater.from(context), this)

    private var _noteId: Int? = null

    private var _checkable: Boolean = false
    val checkable: Boolean
        get() = _checkable

    private var _isChecked: Boolean = false
    val isChecked: Boolean
        get() = _isChecked

    private var _isPinned: Boolean = false
    val isPinned: Boolean
        get() = _isPinned

    init {
        initDefault()
    }

    private fun initDefault() {
        binding.run {
            background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_8)
            val padding = resources.getDimensionPixelSize(R.dimen.dp_16)
            setPadding(padding, padding, padding, padding)
        }
    }

    override fun setNoteId(id: Int): NoteViewInterface {
        this._noteId = id
        return this
    }

    override fun setTitle(text: String): NoteViewInterface {
        binding.tvNoteTitle.text = text
        return this
    }

    override fun setDesc(text: String): NoteViewInterface {
        binding.tvNoteDesc.text = text
        return this
    }

    override fun setCreatedAt(text: String): NoteViewInterface {
        binding.tvCreatedAt.text = text
        return this
    }

    override fun setIsChecked(isChecked: Boolean): NoteViewInterface {
        if (!_checkable && _isChecked == isChecked) return this
        this._isChecked = isChecked
        binding.ivCheck.setImageResource(
            if (isChecked) R.drawable.ic_24baseline_radio_button_checked else R.drawable.ic_24baseline_radio_button_unchecked
        )
        return this
    }

    override fun isPinned(pin: Boolean): NoteViewInterface {
        this._isPinned = pin
        binding.ivPin.visibility = if (pin) View.VISIBLE else View.GONE
        return this
    }

    override fun enableCheck(enable: Boolean): NoteViewInterface {
        if (enable == _checkable) return this
        this._isChecked = false
        this._checkable = enable
        binding.ivCheck.visibility = if (enable) View.VISIBLE else View.GONE
        return this
    }
}