package vn.thailam.android.masterlife.app.page.passsaver.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.PassEntity
import vn.thailam.android.masterlife.data.repo.PassRepo

data class PassUiModel(
    val entity: PassEntity,
    val showPassword: Boolean = false
) {

    companion object {
        fun fromEntity(passEntity: PassEntity): PassUiModel {
            return PassUiModel(entity = passEntity)
        }
    }
}

class PassSaverViewModel(
    private val passRepo: PassRepo
) : BaseViewModel() {

    val displayedPasswords = MutableLiveData<List<PassUiModel>>()

    fun getPasswords() = viewModelScope.launch(Dispatchers.Main) {
        passRepo.observeAll()
            .flowOn(Dispatchers.IO)
            .collect { list ->
                displayedPasswords.value = list.map {
                    PassUiModel.fromEntity(it)
                }
            }
    }

    fun onPasswordClick(password: PassUiModel) {
        // Move to dispatchers IO if needed, for now, no need since performance improve is
        // minimal
        val passwords = displayedPasswords.value!!.map {
            if (it.entity.id == password.entity.id) {
                it.copy(showPassword = !it.showPassword)
            } else {
                it
            }
        }

        displayedPasswords.value = passwords
    }
}