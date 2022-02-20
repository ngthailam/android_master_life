package vn.thailam.android.masterlife.app.page.inventory.create.room

import vn.thailam.android.masterlife.app.base.AdapterItem
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity

data class RoomChooserUiModel(
    val data: RoomTypeEntity,
    val isChosen: Boolean = false
)


