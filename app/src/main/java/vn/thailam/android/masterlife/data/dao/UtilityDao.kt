package vn.thailam.android.masterlife.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import vn.thailam.android.masterlife.data.entity.UTILITY_TBL_NAME
import vn.thailam.android.masterlife.data.entity.UtilityEntity

@Dao
interface UtilityDao {
    @Insert
    fun insertAll(utilities: List<UtilityEntity>)

    @Query("SELECT * FROM $UTILITY_TBL_NAME")
    fun getAll(): List<UtilityEntity>
}