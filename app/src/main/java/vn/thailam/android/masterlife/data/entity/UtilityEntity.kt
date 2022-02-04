package vn.thailam.android.masterlife.data.entity

import android.os.Parcelable
import androidx.annotation.StringDef
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import vn.thailam.android.masterlife.data.entity.UtilityCode.Companion.PASS_SAVER

const val UTILITY_TBL_NAME = "utility"
const val UTILITY_PRIMARY_KEY = "id"
const val UTILITY_COL_NAME = "name"
const val UTILITY_COL_CODE = "code"
const val UTILITY_COL_DL = "deeplink"

@Parcelize
@Entity(tableName = UTILITY_TBL_NAME)
data class UtilityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UTILITY_PRIMARY_KEY)
    val id: Int? = null,
    @ColumnInfo(name = UTILITY_COL_NAME)
    val name: String? = null,
    @UtilityCode
    @ColumnInfo(name = UTILITY_COL_CODE)
    val code: String? = null,
    @ColumnInfo(name = UTILITY_COL_DL)
    val deeplink: String? = null,
) : Parcelable

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    value = [PASS_SAVER]
)
annotation class UtilityCode {
    companion object {
        const val PASS_SAVER = "PASS_SAVER"
    }
}