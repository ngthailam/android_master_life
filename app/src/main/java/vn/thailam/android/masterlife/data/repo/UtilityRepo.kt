package vn.thailam.android.masterlife.data.repo

import vn.thailam.android.masterlife.data.dao.UtilityDao
import vn.thailam.android.masterlife.data.entity.UtilityEntity

interface UtilityRepo {
    suspend fun insertAll(utilities: List<UtilityEntity>)

    suspend fun getAll(): List<UtilityEntity>
}

class UtilityRepoImpl(
    private val utilityDao: UtilityDao
) : UtilityRepo {
    override suspend fun insertAll(utilities: List<UtilityEntity>) {
        utilityDao.insertAll(utilities)
    }

    override suspend fun getAll(): List<UtilityEntity> {
        return utilityDao.getAll()
    }

}