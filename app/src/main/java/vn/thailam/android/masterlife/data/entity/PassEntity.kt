package vn.thailam.android.masterlife.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassEntity(
    val id: Int?,
    val name: String?,
    val desc: String?,
    val encryptedPassword: String?
) : Parcelable