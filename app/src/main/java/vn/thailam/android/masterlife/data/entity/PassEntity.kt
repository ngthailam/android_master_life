package vn.thailam.android.masterlife.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val PASS_TBL_NAME = "pass"
const val PASS_PRIMARY_KEY = "id"
const val PASS_COL_NAME = "name"
const val PASS_COL_ACC_NAME = "acc_name"
const val PASS_COL_EN_PASS = "encrypted_pass"

@Parcelize
@Entity(tableName = PASS_TBL_NAME)
data class PassEntity @Ignore constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PASS_PRIMARY_KEY)
    val id: Int? = null,
    @ColumnInfo(name = PASS_COL_NAME)
    val name: String? = null,
    @ColumnInfo(name = PASS_COL_ACC_NAME)
    val accName: String? = null,
    @ColumnInfo(name = PASS_COL_EN_PASS)
    val encryptedPassword: String? = null,
    @Ignore
    val decryptedPassword: String? = null
) : Parcelable {
    // Use constructor to avoid @Ignore issues
    constructor(id: Int?, name: String?, accName: String?, encryptedPassword: String?) : this(
        id,
        name,
        accName,
        encryptedPassword,
        null,
    )
}