package vn.thailam.android.masterlife.data.repo

import kotlinx.coroutines.flow.Flow
import vn.thailam.android.masterlife.data.dao.ScheduleDao
import vn.thailam.android.masterlife.data.entity.ScheduleEntity
import java.util.Date

interface ScheduleRepo {
    fun insert(title: String = "", desc: String, time: Date? = null)

    fun getAllFlow(): Flow<List<ScheduleEntity>>

    fun getByIdFlow(id: Int): Flow<ScheduleEntity>
}

class ScheduleRepoImpl(
    private val ScheduleDao: ScheduleDao
) : ScheduleRepo {
    override fun insert(title: String, desc: String, time: Date?) {
        ScheduleDao.insert(
            ScheduleEntity(title = title, desc = desc, time = time)
        )
    }

    override fun getAllFlow(): Flow<List<ScheduleEntity>> {
        return ScheduleDao.getAllFlow()
    }

    override fun getByIdFlow(id: Int): Flow<ScheduleEntity> {
        return ScheduleDao.getByIdFlow(id)
    }
}