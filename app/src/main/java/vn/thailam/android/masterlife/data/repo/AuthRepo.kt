package vn.thailam.android.masterlife.data.repo

import vn.thailam.android.masterlife.data.dao.AuthDao
import vn.thailam.android.masterlife.data.entity.UserEntity

interface AuthRepo {
    suspend fun isLoggedIn(): Boolean

    suspend fun getUser(): UserEntity?

    suspend fun saveUser(name: String)
}

class AuthRepoImpl(
    private val authDao: AuthDao
) : AuthRepo {
    override suspend fun isLoggedIn(): Boolean {
        return authDao.isLoggedIn()
    }

    override suspend fun getUser(): UserEntity? {
        return authDao.getUser()
    }

    override suspend fun saveUser(name: String) {
        authDao.saveUser(name)
    }
}