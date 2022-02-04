package vn.thailam.android.masterlife.app.page.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.UtilityEntity
import vn.thailam.android.masterlife.data.repo.UtilityRepo

class HomeViewModel(
    private val utilityRepo: UtilityRepo
) : BaseViewModel() {

    val utilityEntities = MutableLiveData<List<UtilityEntity>>()

    fun getUtilities() {
        viewModelScope.launch(Dispatchers.Main) {
            val entities = withContext(Dispatchers.IO) {
                utilityRepo.getAll()
            }
            utilityEntities.value = entities
        }
    }
}