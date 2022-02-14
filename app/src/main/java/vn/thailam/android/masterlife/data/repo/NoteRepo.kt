package vn.thailam.android.masterlife.data.repo

import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.dao.NoteDao
import vn.thailam.android.masterlife.data.entity.NoteEntity
import java.util.*

interface NoteRepo {
    suspend fun insert(title: String = "", desc: String)

    fun getAllFlow(): Flow<List<NoteEntity>>

    fun getByIdFlow(id: Int): Flow<NoteEntity>

    suspend fun getById(id: Int): NoteEntity

    suspend fun search(searchText: String): List<NoteEntity>

    suspend fun save(id: Int? = null, title: String, desc: String): Int

    suspend fun delete(id: Int)

    suspend fun delete(ids: List<Int>)

    suspend fun pinOrUnpin(id: Int)

    suspend fun pin(ids: List<Int>)
}

class NoteRepoImpl(
    private val noteDao: NoteDao
) : NoteRepo {
    override suspend fun insert(title: String, desc: String) {
        noteDao.insert(NoteEntity(title = title, desc = desc))
    }

    override fun getAllFlow(): Flow<List<NoteEntity>> {
        return noteDao.getAllFlow()
    }

    override fun getByIdFlow(id: Int): Flow<NoteEntity> {
        return noteDao.getByIdFlow(id)
    }

    override suspend fun getById(id: Int): NoteEntity {
        return noteDao.getById(id)
    }

    override suspend fun search(searchText: String): List<NoteEntity> {
        return noteDao.searchTitle(searchText)
    }

    override suspend fun save(id: Int?, title: String, desc: String): Int {
        return noteDao.save(NoteEntity(id = id, title = title, desc = desc)).toInt()
    }

    override suspend fun delete(id: Int) {
        noteDao.delete(id)
    }

    override suspend fun delete(ids: List<Int>) {
        noteDao.deleteByIds(ids)
    }

    override suspend fun pinOrUnpin(id: Int) {
        val note = noteDao.getById(id)
        noteDao.save(
            note.copy(
                pin = if (note.pin == null) Date(System.currentTimeMillis()) else null
            )
        )
    }

    override suspend fun pin(ids: List<Int>) {
        ids.forEach { id ->
            val note = noteDao.getById(id)
            noteDao.save(
                note.copy(pin = Date(System.currentTimeMillis()))
            )
        }
    }
}