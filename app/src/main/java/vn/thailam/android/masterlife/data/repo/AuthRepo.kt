package vn.thailam.android.masterlife.data.repo

import vn.thailam.android.masterlife.data.dao.AuthDao
import vn.thailam.android.masterlife.data.entity.UserEntity

interface AuthRepo {
    fun isLoggedIn(): Boolean

    fun getUser(): UserEntity?

    fun saveUser(name: String)
}

class AuthRepoImpl(
    private val authDao: AuthDao
) : AuthRepo {
    override fun isLoggedIn(): Boolean {
        return authDao.isLoggedIn()
    }

    override fun getUser(): UserEntity? {
        return authDao.getUser()
    }

    override fun saveUser(name: String) {
        authDao.saveUser(name)
    }
}