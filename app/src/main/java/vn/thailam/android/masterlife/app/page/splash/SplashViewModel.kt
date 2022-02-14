package vn.thailam.android.masterlife.app.page.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.AuthStatus
import vn.thailam.android.masterlife.data.entity.UtilityCode
import vn.thailam.android.masterlife.data.entity.UtilityEntity
import vn.thailam.android.masterlife.data.repo.AuthRepo
import vn.thailam.android.masterlife.data.repo.UtilityRepo

class SplashViewModel(
    private val authRepo: AuthRepo,
    private val utilityRepo: UtilityRepo,
) : BaseViewModel() {

    val userStatus = MutableLiveData<AuthStatus>()

    fun initialize() {
        viewModelScope.launch(Dispatchers.Main) {
            prepopulateUtilities()
            userStatus.value = checkUserStatus()
        }
    }

    private suspend fun checkUserStatus(): AuthStatus = withContext(Dispatchers.IO) {
        val user = authRepo.getUser()
        if (user == null) {
            AuthStatus.UNREGISTERED
        } else {
            AuthStatus.REGISTERED
        }
    }

    private suspend fun prepopulateUtilities() = withContext(Dispatchers.IO) {
        val exist = utilityRepo.getAll().isNotEmpty()
        if (!exist) {
            val data = listOf(
                UtilityEntity(name = "Password Saver", code = UtilityCode.PASS_SAVER),
                UtilityEntity(name = "Notes", code = UtilityCode.NOTES)
            )
            utilityRepo.insertAll(data)
        }
    }
}