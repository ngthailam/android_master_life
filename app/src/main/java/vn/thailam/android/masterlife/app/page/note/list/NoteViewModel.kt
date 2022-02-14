package vn.thailam.android.masterlife.app.page.note.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.NoteEntity
import vn.thailam.android.masterlife.data.repo.NoteRepo
import java.util.*

data class NoteUiModel(
    val entity: NoteEntity,
    val isSelected: Boolean = false,
    val showSelectBox: Boolean = false,
) {
    companion object {
        fun fromEntity(entity: NoteEntity) = NoteUiModel(
            entity = entity
        )
    }
}

class NoteViewModel(
    private val noteRepo: NoteRepo
) : BaseViewModel() {

    // TODO: issue ordering when search -> empty -> gives layout error
    var notes: List<NoteUiModel> = listOf()
    val displayedNotes = MutableLiveData<List<NoteUiModel>>()
    val noteListMode = MutableLiveData<NoteListMode>()
    val selectedNoteIds = mutableListOf<Int>()

    fun getNotes() = viewModelScope.launch(Dispatchers.Main) {
        noteRepo.getAllFlow()
            .map { it.sortNote() }
            .map { it.map { entity -> NoteUiModel.fromEntity(entity) } }
            .flowOn(Dispatchers.IO)
            .collect {
                notes = it
                displayedNotes.value = notes
            }
    }

    fun onSearch(searchText: String?) {
        if (searchText == null || searchText.isEmpty() || searchText.isBlank()) {
            displayedNotes.value = notes
        }

        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                noteRepo.search(searchText!!)
                    .sortNote()
                    .map { entity -> NoteUiModel.fromEntity(entity) }
            }

            displayedNotes.value = result
        }
    }

    fun startNormalMode() {
        if (noteListMode.value != null && noteListMode.value != NoteListMode.NORMAL) {
            noteListMode.value = NoteListMode.NORMAL
            selectedNoteIds.clear()
            displayedNotes.value = displayedNotes.value?.map {
                it.copy(
                    showSelectBox = false,
                    isSelected = false,
                )
            }
        }
    }

    fun startMultiSelectMode() {
        if (noteListMode.value != NoteListMode.MULTI_SELECT) {
            noteListMode.value = NoteListMode.MULTI_SELECT
            displayedNotes.value = displayedNotes.value?.map {
                it.copy(
                    showSelectBox = true,
                    isSelected = false,
                )
            }
        }
    }

    fun isNormalMode() = noteListMode.value == null || noteListMode.value == NoteListMode.NORMAL
    fun isMultiSelectMode() = noteListMode.value == NoteListMode.MULTI_SELECT

    fun onSelectNote(id: Int) {
        var selected = false
        if (selectedNoteIds.contains(id)) {
            selectedNoteIds.removeIf { it == id }
        } else {
            selectedNoteIds.add(id)
            selected = true
        }

        // TODO: improve perforamnce
        displayedNotes.value = displayedNotes.value?.map {
            if (it.entity.id == id) it.copy(isSelected = selected) else it
        }
    }

    private fun List<NoteEntity>.sortNote(): List<NoteEntity> {
        return this.sortedByDescending { item -> item.pin ?: Date(950516618) }
    }

    fun deleteSelected() = viewModelScope.launch{
        noteRepo.delete(selectedNoteIds)
        startNormalMode() // TODO: needs optimize
    }

    fun pinSelected() = viewModelScope.launch {
        noteRepo.pin(selectedNoteIds)
        startNormalMode() // TODO: needs optimize
    }
}

enum class NoteListMode {
    NORMAL,
    MULTI_SELECT
}