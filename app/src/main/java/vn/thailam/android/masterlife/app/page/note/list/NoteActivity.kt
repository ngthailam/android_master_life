package vn.thailam.android.masterlife.app.page.note.list

import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
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
import vn.thailam.android.masterlife.app.utils.MyAnimationListener
import vn.thailam.android.masterlife.app.utils.SpacingItemDecoration
import vn.thailam.android.masterlife.app.utils.applySlideInUp
import vn.thailam.android.masterlife.app.utils.debounce
import vn.thailam.android.masterlife.app.utils.visibility
import vn.thailam.android.masterlife.databinding.ActivityNoteBinding

class NoteActivity : BaseVBActivity<ActivityNoteBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityNoteBinding
        get() = ActivityNoteBinding::inflate

    private val viewModel by viewModel<NoteViewModel>()
    private val listItemInteraction: NoteItemInteraction by lazy(LazyThreadSafetyMode.NONE) {
        object : NoteItemInteraction {
            // TODO: consider moving some logic to view model
            override fun onClick(note: NoteUiModel) {
                if (viewModel.isMultiSelectMode()) {
                    viewModel.onSelectNote(note.entity.id!!)
                } else {
                    goToCreateNotePage(note.entity.id!!)
                }
            }

            override fun onLongPressed(note: NoteUiModel): Boolean {
                if (viewModel.isNormalMode()) {
                    viewModel.startMultiSelectMode()
                    return true
                }
                return false
            }
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
            noteListMode.observe(this@NoteActivity, ::onModeChanged)

            getNotes()
        }
    }

    private fun onModeChanged(noteListMode: NoteListMode) {
        when (noteListMode) {
            NoteListMode.MULTI_SELECT -> {
                animateFab(false)
                animateOptionsBar(true)
                binding.ivCheck.visibility = View.VISIBLE
            }
            NoteListMode.NORMAL -> {
                animateFab(true)
                animateOptionsBar(false)
                binding.ivCheck.visibility = View.GONE
            }
        }
    }

    private fun animateFab(show: Boolean) {
        val scaleInitial = if (show) 0f else 1f
        val scaleAnimation = ScaleAnimation(
            scaleInitial,
            1f - scaleInitial,
            scaleInitial,
            1f - scaleInitial,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 200L
            interpolator = if (show) DecelerateInterpolator() else AccelerateInterpolator()

            setAnimationListener(MyAnimationListener(
                onAnimEnd = {
                    binding.fabCreateNote.visibility(show)
                }
            ))
        }
        binding.fabCreateNote.startAnimation(scaleAnimation)
    }

    private fun animateOptionsBar(show: Boolean) {
        binding.clOptionBar.visibility(show)
    }

    override fun setupClickListeners() {
        binding.run {
            fabCreateNote.setOnClickListener { goToCreateNotePage() }
            tbNotes.setNavigationOnClickListener { finish() }
            ivCheck.setOnClickListener { viewModel.startNormalMode() }

            // Move when custom view ready
            ivTrash.setOnClickListener { viewModel.deleteSelected() }
            ivPin.setOnClickListener { viewModel.pinSelected() }
        }
    }

    private fun goToCreateNotePage(noteId: Int? = null) {
        supportFragmentManager.beginTransaction()
            .applySlideInUp()
            .addToBackStack(null)
            .add(R.id.clNoteContainer, NoteDetailFragment.newInstance(noteId), null)
            .commit()
    }

    private fun onNotesChanged(notes: List<NoteUiModel>) {
        noteAdapter.submitList(notes)
    }

    private fun setupSearchView() {
        binding.svNote.run {
            val onSearchTextChanged: (String?) -> Unit = debounce(
                300L,
                lifecycleScope,
                viewModel::onSearch
            )

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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