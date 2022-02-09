package vn.thailam.android.masterlife.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.entity.PASS_TBL_NAME
import vn.thailam.android.masterlife.data.entity.PassEntity

@Dao
interface PassDao {
    @Insert
    fun insert(passEntity: PassEntity)

    @Query("SELECT * FROM $PASS_TBL_NAME")
    fun getAll(): List<PassEntity>

    @Query("SELECT * FROM $PASS_TBL_NAME")
    fun getAllFlow(): Flow<List<PassEntity>>
}
