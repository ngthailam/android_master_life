package vn.thailam.android.masterlife.data.entity

import androidx.annotation.StringDef
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import vn.thailam.android.masterlife.data.entity.InventoryCondition.Companion.MISSING
import vn.thailam.android.masterlife.data.entity.InventoryCondition.Companion.NORMAL

const val INVEN_TBL_NAME = "inventory"
const val INVEN_PRIMARY_KEY = "id"
const val INVEN_COL_NAME = "name"
const val INVEN_COL_ROOM = "room"
const val INVEN_COL_LOC_DESC = "location_desc"
const val INVEN_COL_COUNT = "count"
const val INVEN_COL_CONDITION = "condition"

@Entity(tableName = INVEN_TBL_NAME)
data class InventoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = INVEN_PRIMARY_KEY)
    val id: Int? = null,
    @ColumnInfo(name = INVEN_COL_NAME)
    val name: String? = null,
    @ColumnInfo(name = INVEN_COL_ROOM)
    val roomTypeId: Int? = null,
    @ColumnInfo(name = INVEN_COL_LOC_DESC)
    val locationDesc: String? = null,
    @ColumnInfo(name = INVEN_COL_COUNT)
    val count: Int = 1,
    @ColumnInfo(name = INVEN_COL_CONDITION)
    val condition: String = NORMAL
) {

    @Ignore
    var roomEntity: RoomTypeEntity? = null
}

@Retention(AnnotationRetention.SOURCE)
@StringDef(value = [NORMAL, MISSING])
annotation class InventoryCondition {
    companion object {
        const val NORMAL = "NORMAL"
        const val MISSING = "MISSING"
    }
}