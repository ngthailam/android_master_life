package vn.thailam.android.masterlife.app.page.inventory

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.InventoryEntity
import vn.thailam.android.masterlife.data.repo.InventoryRepo

class InventoryViewModel(
    private val inventoryRepo: InventoryRepo
) : BaseViewModel() {

    val inventories = MutableLiveData<List<InventoryEntity>>()
    var searchFilter = MutableLiveData<((InventoryEntity) -> Boolean)> { true }

    val displayedInventories = MediatorLiveData<List<InventoryEntity>>().apply {
        fun updateList() {
            value = inventories.value?.filter(searchFilter.value!!) ?: listOf()
        }

        addSource(inventories) { updateList() }
        addSource(searchFilter) { updateList() }
    }

    fun initialize() {
        getAll()
    }

    fun getAll() = viewModelScope.launch {
        inventoryRepo.getAllFlow()
            .flowOn(Dispatchers.IO)
            .collect { inventories.value = it }
    }

    fun onSearch(text: String?) {
        searchFilter.value = {
            if (text?.isNotEmpty() == true) {
                it.name!!.contains(text) || it.locationDesc?.contains(text) == true
            } else {
                true
            }
        }
    }
}