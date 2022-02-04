package vn.thailam.android.masterlife.data.dao

import androidx.room.Dao
import vn.thailam.android.masterlife.data.entity.PassEntity

@Dao
interface PassDao {
    fun insert(passEntity: PassEntity)

    fun getAll(): List<PassEntity>
}
