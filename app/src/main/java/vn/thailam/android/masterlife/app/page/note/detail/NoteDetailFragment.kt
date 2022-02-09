package vn.thailam.android.masterlife.app.page.note.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.japanwritinghelper.app.base.BaseViewBindingFragment
import vn.thailam.android.masterlife.app.utils.OpStatus
import vn.thailam.android.masterlife.databinding.FragmentNoteCreateBinding

class NoteDetailFragment : BaseViewBindingFragment<FragmentNoteCreateBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNoteCreateBinding
        get() = FragmentNoteCreateBinding::inflate

    private val viewModel by viewModel<NoteDetailViewModel>()

    private val noteId: Int by lazy {
        arguments?.getInt(ARG_NOTE_ID, 0) ?: 0
    }

    override fun setupUI() {
        super.setupUI()
        setupTextChangeListeners()
    }

    override fun setupClickListeners() {
        super.setupClickListeners()
        binding.run {
            tbCreateNote.setNavigationOnClickListener {
                activity?.onBackPressed()
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

            initialize(noteId)
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