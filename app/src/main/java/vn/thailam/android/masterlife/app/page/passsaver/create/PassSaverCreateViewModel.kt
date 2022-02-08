package vn.thailam.android.masterlife.app.page.passsaver.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.app.utils.OpStatus
import vn.thailam.android.masterlife.data.repo.PassRepo

class PassSaverCreateViewModel(
    private val passRepo: PassRepo
) : BaseViewModel() {

    val createOpStatus = MutableLiveData<OpStatus>()

    private var name: String = ""
    private var accName: String = ""
    private var password: String = ""

    fun onNameChanged(text: String) {
        val trimmed = text.trim()
        name = trimmed
    }

    fun onAccNameChanged(text: String) {
        val trimmed = text.trim()
        accName = trimmed
    }

    fun onPasswordChanged(text: String) {
        val trimmed = text.trim()
        password = trimmed
    }

    fun createPass() {
        if (validateInputs()) {
            viewModelScope.launch(Dispatchers.IO) {
                createOpStatus.postValue(OpStatus.Loading)
                try {
                    passRepo.savePassword(name, accName, password)
                    createOpStatus.postValue(OpStatus.Success)
                } catch (t: Throwable) {
                    createOpStatus.postValue(OpStatus.Error(t))
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val isNameValid = name.isNotEmpty()
        val isAccNameValid = accName.isNotEmpty()
        val isPasswordValid = password.isNotEmpty()

        return isNameValid && isAccNameValid && isPasswordValid
    }
}