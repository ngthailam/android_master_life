package vn.thailam.android.masterlife.app.page.passsaver

import vn.thailam.android.japanwritinghelper.app.base.BaseViewModel
import vn.thailam.android.masterlife.data.entity.PassEntity
import vn.thailam.android.masterlife.data.repo.PassRepo

data class PassUiModel(
    val entity: PassEntity,
    val rawPassword: String,
    val showPassword: Boolean = false
) {

    companion object {
        fun fromEntity(passEntity: PassEntity, decryptedPass: String): PassUiModel {
            return PassUiModel(
                entity = passEntity,
                rawPassword = decryptedPass,
            )
        }
    }
}

class PassSaverViewModel(
    private val passRepo: PassRepo
) : BaseViewModel() {

    fun getPasswords() {

    }
}