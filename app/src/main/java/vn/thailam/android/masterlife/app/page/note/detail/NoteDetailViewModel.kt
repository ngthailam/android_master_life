package vn.thailam.android.masterlife.app.page.note.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.app.utils.OpStatus
import vn.thailam.android.masterlife.data.entity.NoteEntity
import vn.thailam.android.masterlife.data.repo.NoteRepo

class NoteDetailViewModel(
    private val noteRepo: NoteRepo
) : BaseViewModel() {
    var saveNoteDebounceJob: Job? = null
    val mode = MutableLiveData<NoteDetailMode>(NoteDetailMode.Unknown)

    val deleteOpStatus = MutableLiveData<OpStatus>()
    val pinOpStatus = MutableLiveData<OpStatus>()

    var id: Int? = null
    var title: String = ""
    var desc: String = ""
    var isPin: Boolean = false

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
                isPin = noteEntity.pin != null

                mode.value = NoteDetailMode.Edit(note = noteEntity)
            }
        }
    }

    fun onTitleChanged(text: String) {
        if (text != title) {
            title = text
            onEditData()
        }
    }

    fun onDescChanged(text: String) {
        if (text != desc) {
            desc = text
            onEditData()
        }
    }

    fun onEditData() {
        saveNoteDebounceJob?.cancel()
        saveNoteDebounceJob = viewModelScope.launch {
            delay(350L)
            if (title.isEmpty() && desc.isEmpty()) {
                deleteNote()
            } else {
                saveNote()
            }
        }
    }

    fun saveNote() = viewModelScope.launch(Dispatchers.IO) {
        val noteId = noteRepo.save(
            id = id,
            title = title,
            desc = desc
        )
        id = noteId
    }

    fun deleteNote() = viewModelScope.launch(Dispatchers.IO) {
        if (id == null) {
            return@launch
        }

        deleteOpStatus.postValue(OpStatus.Loading)
        try {
            noteRepo.delete(id!!)
            deleteOpStatus.postValue(OpStatus.Success)
        } catch (t: Throwable) {
            deleteOpStatus.postValue(OpStatus.Error(t))
        }
    }

    fun pinOrUnpinNote() = viewModelScope.launch(Dispatchers.IO) {
        if (id == null) {
            return@launch
        }

        pinOpStatus.postValue(OpStatus.Loading)
        try {
            noteRepo.pinOrUnpin(id!!)
            isPin = !isPin
            pinOpStatus.postValue(OpStatus.Success)
        } catch (t: Throwable) {
            pinOpStatus.postValue(OpStatus.Error(t))
        }
    }
}

sealed class NoteDetailMode {
    object Unknown : NoteDetailMode()
    object Create : NoteDetailMode()
    data class Edit(val note: NoteEntity) : NoteDetailMode()
}