package vn.thailam.android.masterlife.app.page.note.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.NoteEntity
import vn.thailam.android.masterlife.data.repo.NoteRepo

class NoteDetailViewModel(
    private val noteRepo: NoteRepo
) : BaseViewModel() {
    var saveNoteDebounceJob: Job? = null
    val mode = MutableLiveData<NoteDetailMode>(NoteDetailMode.Unknown)

    var id: Int? = null
    var title: String = ""
    var desc: String = ""

    fun initialize(noteId: Int) {
        if (noteId == 0) {
            mode.value = NoteDetailMode.Create
            return
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                val noteEntity = withContext(Dispatchers.IO) {
                    noteRepo.getById(noteId)
                }

                id = noteEntity.id
                title = noteEntity.title ?: ""
                desc = noteEntity.desc ?: ""

                mode.value = NoteDetailMode.Edit(note = noteEntity)
            }
        }
    }

    fun onTitleChanged(text: String) {
        if (text != title) {
            title = text
            saveNote()
        }
    }

    fun onDescChanged(text: String) {
        if (text != desc) {
            desc = text
            saveNote()
        }
    }

    fun saveNote() {
        saveNoteDebounceJob?.cancel()
        saveNoteDebounceJob = viewModelScope.launch {
            delay(350L)
            val noteId = withContext(Dispatchers.IO) {
                noteRepo.save(
                    id = id,
                    title = title,
                    desc = desc
                )
            }

            id = noteId
        }
    }
}

sealed class NoteDetailMode {
    object Unknown : NoteDetailMode()
    object Create : NoteDetailMode()
    data class Edit(val note: NoteEntity) : NoteDetailMode()
}