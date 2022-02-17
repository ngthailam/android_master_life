package vn.thailam.android.masterlife.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.entity.ROOM_PRIMARY_KEY
import vn.thailam.android.masterlife.data.entity.ROOM_TBL_NAME
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity

@Dao
interface RoomTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventory: RoomTypeEntity): Long

    @Update
    suspend fun update(inventory: RoomTypeEntity)

    @Query("SELECT * FROM $ROOM_TBL_NAME")
    fun getAllFlow(): Flow<List<RoomTypeEntity>>

    @Query("SELECT * FROM $ROOM_TBL_NAME")
    suspend fun getAll(): List<RoomTypeEntity>

    @Query("SELECT * FROM $ROOM_TBL_NAME WHERE $ROOM_PRIMARY_KEY = :id")
    fun getByIdFlow(id: Int): Flow<RoomTypeEntity>

    @Query("SELECT * FROM $ROOM_TBL_NAME WHERE $ROOM_PRIMARY_KEY = :id")
    suspend fun getById(id: Int): RoomTypeEntity

    @Query("DELETE FROM $ROOM_TBL_NAME WHERE $ROOM_PRIMARY_KEY = :id")
    fun deleteById(id: Int)
}
