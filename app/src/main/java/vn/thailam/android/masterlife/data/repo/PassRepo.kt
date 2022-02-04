package vn.thailam.android.masterlife.data.repo

import vn.thailam.android.masterlife.data.dao.PassDao
import vn.thailam.android.masterlife.data.entity.PassEntity
import vn.thailam.android.masterlife.data.utils.PassSecurityStore

interface PassRepo {
    fun insert(passEntity: PassEntity)

    fun getAll(): List<PassEntity>

    fun getDecryptedPassword(passEntity: PassEntity): String
}

class PassRepoImpl(
    private val passDao: PassDao,
    private val passSecurityStore: PassSecurityStore,
) : PassRepo {
    override fun insert(passEntity: PassEntity) {
        passDao.insert(passEntity)
    }

    override fun getAll(): List<PassEntity> {
        return passDao.getAll()
    }

    override fun getDecryptedPassword(passEntity: PassEntity): String {
        return passSecurityStore.decrypt(passEntity.encryptedPassword!!)
    }
}