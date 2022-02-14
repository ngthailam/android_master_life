package vn.thailam.android.masterlife.app.page.note.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.japanwritinghelper.app.base.BaseViewBindingFragment
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.app.utils.OpStatus
import vn.thailam.android.masterlife.app.utils.toast
import vn.thailam.android.masterlife.databinding.FragmentNoteCreateBinding

class NoteDetailFragment : BaseViewBindingFragment<FragmentNoteCreateBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNoteCreateBinding
        get() = FragmentNoteCreateBinding::inflate

    private val viewModel by viewModel<NoteDetailViewModel>()

    private val noteId: Int by lazy {
        arguments?.getInt(ARG_NOTE_ID, 0) ?: 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setupUI() {
        super.setupUI()
        setupToolbar()
        setupTextChangeListeners()
    }

    override fun setupClickListeners() {
        super.setupClickListeners()
    }

    private fun setupToolbar() {
        binding.tbCreateNote.run {
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            inflateMenu(R.menu.menu_note_detail)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_note_detail_pin, R.id.menu_note_detail_unpin -> {
                        viewModel.pinOrUnpinNote()
                        true
                    }
                    R.id.menu_note_detail_delete -> {
                        viewModel.deleteNote()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupTextChangeListeners() {
        binding.run {
            etTitle.doAfterTextChanged { viewModel.onTitleChanged(it.toString()) }
            etDesc.doAfterTextChanged { viewModel.onDescChanged(it.toString()) }
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            mode.observe(viewLifecycleOwner, ::onModeChanged)
            deleteOpStatus.observe(viewLifecycleOwner, ::onDeleteOpStatusChanged)
            pinOpStatus.observe(viewLifecycleOwner, ::onPinChanged)

            initialize(noteId)
        }
    }

    private fun onPinChanged(opStatus: OpStatus) {
        updatePinMenu()
    }

    private fun updatePinMenu() {
        val isPin = viewModel.isPin
        binding.tbCreateNote.menu.findItem(R.id.menu_note_detail_pin).isVisible = !isPin
        binding.tbCreateNote.menu.findItem(R.id.menu_note_detail_unpin).isVisible = isPin
    }

    private fun onDeleteOpStatusChanged(opStatus: OpStatus) {
        when (opStatus) {
            is OpStatus.Success -> {
                requireContext().toast(message = "Delete note successfully")
                activity?.onBackPressed()
            }
            is OpStatus.Error -> {
                requireContext().toast(message = "Delete note error ${opStatus.e.message}")
            }
            else -> Unit
        }
    }

    private fun onModeChanged(mode: NoteDetailMode) {
        when (mode) {
            is NoteDetailMode.Edit -> {
                binding.etTitle.setText(mode.note.title ?: "")
                binding.etDesc.setText(mode.note.desc ?: "")
            }
            else -> Unit
        }
        updatePinMenu()
    }

    companion object {
        const val ARG_NOTE_ID = "ARG_NOTE_ID"

        fun newInstance(noteId: Int? = null) = NoteDetailFragment().apply {
            arguments = bundleOf(
                ARG_NOTE_ID to noteId
            )
        }
    }
}