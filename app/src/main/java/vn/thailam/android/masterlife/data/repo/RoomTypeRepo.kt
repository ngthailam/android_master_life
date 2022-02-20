package vn.thailam.android.masterlife.data.repo

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.dao.RoomTypeDao
import vn.thailam.android.masterlife.data.entity.ROOM_PRIMARY_KEY
import vn.thailam.android.masterlife.data.entity.ROOM_TBL_NAME
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity

interface RoomTypeRepo {
    suspend fun insert(inventory: RoomTypeEntity): Long

    suspend fun update(inventory: RoomTypeEntity)

    fun getAllFlow(): Flow<List<RoomTypeEntity>>

    fun getByIdFlow(id: Int): Flow<RoomTypeEntity>

    suspend fun getAll(): List<RoomTypeEntity>

    suspend fun getById(roomTypeId: Int): RoomTypeEntity

    fun deleteById(id: Int)
}

class RoomTypeRepoImpl(
    private val roomTypeDao: RoomTypeDao
): RoomTypeRepo {
    override suspend fun insert(inventory: RoomTypeEntity): Long {
        return roomTypeDao.insert(inventory)
    }

    override suspend fun update(inventory: RoomTypeEntity) {
        roomTypeDao.insert(inventory)
    }

    override fun getAllFlow(): Flow<List<RoomTypeEntity>> {
        return roomTypeDao.getAllFlow()
    }

    override suspend fun getAll(): List<RoomTypeEntity> {
        return roomTypeDao.getAll()
    }

    override fun getByIdFlow(id: Int): Flow<RoomTypeEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(roomTypeId: Int): RoomTypeEntity {
        return roomTypeDao.getById(roomTypeId)
    }

    override fun deleteById(id: Int) {
        return roomTypeDao.deleteById(id)
    }

}