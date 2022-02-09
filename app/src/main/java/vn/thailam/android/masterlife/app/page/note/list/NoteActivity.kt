package vn.thailam.android.masterlife.app.page.note.list

import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.app.base.BaseVBActivity
import vn.thailam.android.masterlife.app.page.note.detail.NoteDetailFragment
import vn.thailam.android.masterlife.app.page.note.list.adapter.NoteAdapter
import vn.thailam.android.masterlife.app.page.note.list.adapter.NoteItemInteraction
import vn.thailam.android.masterlife.app.utils.SpacingItemDecoration
import vn.thailam.android.masterlife.app.utils.applySlideInUp
import vn.thailam.android.masterlife.app.utils.debounce
import vn.thailam.android.masterlife.data.entity.NoteEntity
import vn.thailam.android.masterlife.databinding.ActivityNoteBinding

class NoteActivity : BaseVBActivity<ActivityNoteBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityNoteBinding
        get() = ActivityNoteBinding::inflate

    private val viewModel by viewModel<NoteViewModel>()
    private val listItemInteraction: NoteItemInteraction by lazy(LazyThreadSafetyMode.NONE) {
        object : NoteItemInteraction {
            override fun onClick(note: NoteEntity) = goToCreateNotePage(note.id)
        }
    }

    private val noteAdapter = NoteAdapter(listItemInteraction)

    override fun setupUI() {
        super.setupUI()
        setupSearchView()
        setupNoteRecycler()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            displayedNotes.observe(this@NoteActivity, ::onNotesChanged)

            getNotes()
        }
    }

    override fun setupClickListeners() {
        binding.run {
            fabCreateNote.setOnClickListener {
                goToCreateNotePage()
            }
        }
    }

    private fun goToCreateNotePage(noteId: Int? = null) {
        supportFragmentManager.beginTransaction()
            .applySlideInUp()
            .addToBackStack(null)
            .add(R.id.clNoteContainer, NoteDetailFragment.newInstance(noteId), null)
            .commit()
    }

    private fun onNotesChanged(notes: List<NoteEntity>) {
        noteAdapter.submitList(notes)
    }

    private fun setupSearchView() {
        binding.svNote.run {
            val onSearchTextChanged: (String?) -> Unit = debounce(
                300L,
                lifecycleScope,
                viewModel::onSearch
            )

            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    onSearchTextChanged(newText)
                    return false
                }
            })
        }
    }

    private fun setupNoteRecycler() {
        binding.rvNotes.run {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
            val spacing = resources.getDimensionPixelSize(R.dimen.dp_4)
            addItemDecoration(SpacingItemDecoration(spacing, spacing, spacing, spacing))
        }
    }
}