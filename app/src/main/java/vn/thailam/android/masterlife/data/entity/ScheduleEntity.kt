package vn.thailam.android.masterlife.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize
import java.util.*

const val SCHE_TBL_NAME = "schedule"
const val SCHE_PRIMARY_KEY = "id"
const val SCHE_COL_TITLE = "title"
const val SCHE_COL_DESC = "desc"
const val SCHE_COL_TIME = "time"

@Parcelize
@Entity(tableName = SCHE_TBL_NAME)
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SCHE_PRIMARY_KEY)
    val id: Int? = null,
    @ColumnInfo(name = SCHE_COL_TITLE)
    val title: String? = null,
    @ColumnInfo(name = SCHE_COL_DESC)
    val desc: String? = null,
    @ColumnInfo(name = SCHE_COL_TIME)
    val time: Date? = null
// TODO: add repeat
) : Parcelable

