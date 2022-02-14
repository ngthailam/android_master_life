package vn.thailam.android.masterlife.app.page.note.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.NoteEntity
import vn.thailam.android.masterlife.data.repo.NoteRepo

class NoteViewModel(
    private val noteRepo: NoteRepo
) : BaseViewModel() {

    var notes: List<NoteEntity> = listOf()
    val displayedNotes = MutableLiveData<List<NoteEntity>>()

    fun getNotes() = viewModelScope.launch(Dispatchers.Main) {
        noteRepo.getAllFlow()
            .flowOn(Dispatchers.IO)
            .collect {
                notes = it
                displayedNotes.value = it
            }
    }

    fun onSearch(searchText: String?) {
        if (searchText == null || searchText.isEmpty() || searchText.isBlank()) {
            displayedNotes.value = notes
        }

        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                noteRepo.search(searchText!!)
            }

            displayedNotes.value = result
        }
    }
}