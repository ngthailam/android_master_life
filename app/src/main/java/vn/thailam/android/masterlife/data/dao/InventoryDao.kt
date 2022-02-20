package vn.thailam.android.masterlife.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.entity.INVEN_PRIMARY_KEY
import vn.thailam.android.masterlife.data.entity.INVEN_TBL_NAME
import vn.thailam.android.masterlife.data.entity.InventoryEntity

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventory: InventoryEntity): Long

    @Query("SELECT * FROM $INVEN_TBL_NAME")
    fun getAllFlow(): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM $INVEN_TBL_NAME WHERE $INVEN_PRIMARY_KEY = :id")
    fun getByIdFlow(id: Int): Flow<InventoryEntity>

    @Query("SELECT * FROM $INVEN_TBL_NAME WHERE $INVEN_PRIMARY_KEY = :id")
    suspend fun getById(id: Int): InventoryEntity

    @Query("DELETE FROM $INVEN_TBL_NAME WHERE $INVEN_PRIMARY_KEY = :id")
    fun deleteById(id: Int)
}