package vn.thailam.android.masterlife.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ROOM_TBL_NAME = "room"
const val ROOM_PRIMARY_KEY = "id"
const val ROOM_COL_NAME = "name"

@Entity(tableName = ROOM_TBL_NAME)
data class RoomTypeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ROOM_PRIMARY_KEY)
    val id: Int? = null,
    @ColumnInfo(name = ROOM_COL_NAME)
    val name: String? = null,
)

