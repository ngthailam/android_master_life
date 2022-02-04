package vn.thailam.android.masterlife.data.repo

import vn.thailam.android.masterlife.data.dao.UtilityDao
import vn.thailam.android.masterlife.data.entity.UtilityEntity

interface UtilityRepo {
    fun insertAll(utilities: List<UtilityEntity>)

    fun getAll(): List<UtilityEntity>
}

class UtilityRepoImpl(
    private val utilityDao: UtilityDao
) : UtilityRepo {
    override fun insertAll(utilities: List<UtilityEntity>) {
        utilityDao.insertAll(utilities)
    }

    override fun getAll(): List<UtilityEntity> {
        return utilityDao.getAll()
    }

}