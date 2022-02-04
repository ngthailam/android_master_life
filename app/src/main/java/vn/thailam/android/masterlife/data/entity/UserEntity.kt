package vn.thailam.android.masterlife.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val name: String? = null,
) : Parcelable

enum class AuthStatus {
    UNREGISTERED,
    REGISTERED
}