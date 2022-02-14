package vn.thailam.android.masterlife.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.thailam.android.masterlife.data.dao.PassDao
import vn.thailam.android.masterlife.data.entity.PassEntity
import vn.thailam.android.masterlife.data.sources.local.PassSecurityStore

interface PassRepo {
    suspend fun getAll(): List<PassEntity>

    fun observeAll(): Flow<List<PassEntity>>

    suspend fun getDecryptedPassword(passEntity: PassEntity): String

    suspend fun savePassword(name: String, accName: String, password: String)
}

class PassRepoImpl(
    private val passDao: PassDao,
    private val passSecurityStore: PassSecurityStore,
) : PassRepo {
    override suspend fun savePassword(name: String, accName: String, password: String) {
        val passEntity = PassEntity(
            name = name,
            accName = accName,
            encryptedPassword = passSecurityStore.encrypt(password)
        )
        passDao.insert(passEntity)
    }

    override suspend fun getAll(): List<PassEntity> {
        return passDao.getAll().map {
            it.copy(decryptedPassword = passSecurityStore.decrypt(it.encryptedPassword!!))
        }
    }

    override fun observeAll(): Flow<List<PassEntity>> {
        return passDao.getAllFlow().map { list ->
            list.map {
                it.copy(decryptedPassword = passSecurityStore.decrypt(it.encryptedPassword!!))
            }
        }
    }

    override suspend fun getDecryptedPassword(passEntity: PassEntity): String {
        return passSecurityStore.decrypt(passEntity.encryptedPassword!!)
    }
}