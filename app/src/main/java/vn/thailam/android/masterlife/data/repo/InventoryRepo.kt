package vn.thailam.android.masterlife.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import vn.thailam.android.masterlife.data.dao.InventoryDao
import vn.thailam.android.masterlife.data.entity.InventoryEntity
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity

interface InventoryRepo {
    suspend fun insert(inventory: InventoryEntity): Long

    fun getAllFlow(): Flow<List<InventoryEntity>>

    fun getByIdFlow(id: Int): Flow<InventoryEntity>

    suspend fun getById(inventoryId: Int): InventoryEntity

    fun deleteById(id: Int)
}

class InventoryRepoImpl(
    private val inventoryDao: InventoryDao,
    private val roomTypeRepo: RoomTypeRepo,
) : InventoryRepo {
    override suspend fun insert(inventory: InventoryEntity): Long {
        return inventoryDao.insert(inventory)
    }

    override fun getAllFlow(): Flow<List<InventoryEntity>> {
        return combine(
            inventoryDao.getAllFlow(),
            roomTypeRepo.getAllFlow()
        ) { inventories: List<InventoryEntity>, rooms: List<RoomTypeEntity> ->

            inventories.map { inventory ->
                inventory.apply {
                    roomEntity = rooms.first { room -> room.id == roomTypeId }
                }
            }
        }
    }

    override suspend fun getById(inventoryId: Int): InventoryEntity {
        val inventory = inventoryDao.getById(inventoryId)
        val room = roomTypeRepo.getById(inventory.roomTypeId!!)
        return inventory.apply {
            roomEntity = room
        }
    }

    override fun getByIdFlow(id: Int): Flow<InventoryEntity> {
        return inventoryDao.getByIdFlow(id)
            .flatMapConcat { inventoryEntity ->
                roomTypeRepo.getByIdFlow(inventoryEntity.roomTypeId!!)
                    .map { roomTypeEntity ->
                        inventoryEntity.apply { roomEntity = roomTypeEntity }
                    }
            }
    }

    override fun deleteById(id: Int) {
        inventoryDao.deleteById(id)
    }
}