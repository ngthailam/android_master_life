package vn.thailam.android.masterlife.data.repo

import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.dao.NoteDao
import vn.thailam.android.masterlife.data.entity.NoteEntity

interface NoteRepo {
    fun insert(title: String = "", desc: String)

    fun getAllFlow(): Flow<List<NoteEntity>>

    fun getByIdFlow(id: Int): Flow<NoteEntity>

    fun getById(id: Int): NoteEntity

    fun search(searchText: String): List<NoteEntity>

    fun save(id: Int? = null, title: String, desc: String): Int
}

class NoteRepoImpl(
    private val noteDao: NoteDao
) : NoteRepo {
    override fun insert(title: String, desc: String) {
        noteDao.insert(NoteEntity(title = title, desc = desc))
    }

    override fun getAllFlow(): Flow<List<NoteEntity>> {
        return noteDao.getAllFlow()
    }

    override fun getByIdFlow(id: Int): Flow<NoteEntity> {
        return noteDao.getByIdFlow(id)
    }

    override fun getById(id: Int): NoteEntity {
        return noteDao.getById(id)
    }

    override fun search(searchText: String): List<NoteEntity> {
        return noteDao.searchTitle(searchText)
    }

    override fun save(id: Int?, title: String, desc: String): Int {
        return noteDao.save(NoteEntity(id = id, title = title, desc = desc)).toInt()
    }
}