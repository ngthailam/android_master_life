package vn.thailam.android.masterlife.app.page.inventory.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.app.utils.OpStatus
import vn.thailam.android.masterlife.data.entity.InventoryEntity
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity
import vn.thailam.android.masterlife.data.repo.InventoryRepo

class InventoryCreateViewModel(
    private val inventoryRepo: InventoryRepo,
) : BaseViewModel() {

    val createOpStatus = MutableLiveData<OpStatus>()

    var inventory = InventoryEntity()

    fun initialize(inventoryId: Int) {
        if (inventoryId != 0) {
            getInventory(inventoryId)
        }
    }

    fun getInventory(inventoryId: Int) = viewModelScope.launch {
        val inventoryEntity = withContext(Dispatchers.IO) { inventoryRepo.getById(inventoryId) }
        inventory = inventoryEntity
    }

    fun createInventory() = viewModelScope.launch(Dispatchers.Main) {
        createOpStatus.value = OpStatus.Loading
        try {
            withContext(Dispatchers.IO) { inventoryRepo.insert(inventory) }
            createOpStatus.value = OpStatus.Success
        } catch (t: Throwable) {
            createOpStatus.value = OpStatus.Error(t)
        }
    }


    fun onCountChanged(newValue: Int) {
        inventory = inventory.copy(count = newValue)
    }

    fun onLocDescChanged(text: String) {
        inventory = inventory.copy(locationDesc = text)
    }

    fun onNameChanged(text: String) {
        inventory = inventory.copy(name = text)
    }

    fun onRoomChanged(room: RoomTypeEntity) {
        inventory = inventory.copy(roomTypeId = room.id!!)
    }
}