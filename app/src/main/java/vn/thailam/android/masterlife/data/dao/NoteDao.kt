package vn.thailam.android.masterlife.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.entity.NOTE_COL_TITLE
import vn.thailam.android.masterlife.data.entity.NOTE_PRIMARY_KEY
import vn.thailam.android.masterlife.data.entity.NOTE_TBL_NAME
import vn.thailam.android.masterlife.data.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: NoteEntity)

    @Query("SELECT * FROM $NOTE_TBL_NAME")
    fun getAllFlow(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TBL_NAME WHERE $NOTE_PRIMARY_KEY=:id")
    fun getByIdFlow(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM $NOTE_TBL_NAME WHERE $NOTE_PRIMARY_KEY=:id")
    suspend fun getById(id: Int): NoteEntity

    @Query("SELECT * FROM $NOTE_TBL_NAME WHERE $NOTE_COL_TITLE LIKE '%' || :searchText || '%'")
    suspend fun searchTitle(searchText: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(note: NoteEntity): Long

    @Query("DELETE FROM $NOTE_TBL_NAME WHERE $NOTE_PRIMARY_KEY=:id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM $NOTE_TBL_NAME WHERE $NOTE_PRIMARY_KEY in (:ids)")
    suspend fun deleteByIds(ids: List<Int>)
}