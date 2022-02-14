package vn.thailam.android.masterlife.data.dao

import vn.thailam.android.masterlife.data.entity.UserEntity

interface AuthDao {
    suspend fun isLoggedIn(): Boolean

    suspend fun getUser(): UserEntity?

    suspend fun saveUser(name: String)
}

class AuthDaoImpl : AuthDao {
    override suspend fun isLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserEntity {
        return UserEntity(name = "Lam")
    }

    override suspend fun saveUser(name: String) {
        TODO("Not yet implemented")
    }
}