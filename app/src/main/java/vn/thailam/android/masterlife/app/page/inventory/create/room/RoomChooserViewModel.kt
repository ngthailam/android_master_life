package vn.thailam.android.masterlife.app.page.inventory.create.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity
import vn.thailam.android.masterlife.data.repo.RoomTypeRepo

class RoomChooserViewModel(
    private val roomTypeRepo: RoomTypeRepo
) : BaseViewModel() {

    var preChosenId: Int = 0
    var handledPreChosen: Boolean = false
    val rooms = MutableLiveData<List<RoomChooserUiModel>>()
    val chosenRoom = MutableLiveData<RoomTypeEntity>()

    fun initialize(preChosenId: Int) {
        this.preChosenId = preChosenId
        getAll()
    }

    fun getAll() = viewModelScope.launch {
        roomTypeRepo.getAllFlow()
            .map { list ->
                list.map {
                    val isChosen = if (handledPreChosen) false else (it.id == preChosenId)
                    RoomChooserUiModel(data = it, isChosen = isChosen)
                }
            }
            .flowOn(Dispatchers.IO)
            .collect { rooms.value = it }
    }

    fun onChosen(room: RoomTypeEntity) {
        if (chosenRoom.value != null) return
        chosenRoom.value = room
    }
}