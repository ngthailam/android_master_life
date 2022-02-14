package vn.thailam.android.masterlife.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.entity.NOTE_PRIMARY_KEY
import vn.thailam.android.masterlife.data.entity.NOTE_TBL_NAME
import vn.thailam.android.masterlife.data.entity.NoteEntity
import vn.thailam.android.masterlife.data.entity.SCHE_PRIMARY_KEY
import vn.thailam.android.masterlife.data.entity.SCHE_TBL_NAME
import vn.thailam.android.masterlife.data.entity.ScheduleEntity

@Dao
interface ScheduleDao {
    @Insert
    fun insert(note: ScheduleEntity)

    @Query("SELECT * FROM $SCHE_TBL_NAME")
    fun getAllFlow(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM $SCHE_TBL_NAME WHERE $SCHE_PRIMARY_KEY=:id")
    fun getByIdFlow(id: Int): Flow<ScheduleEntity>
}