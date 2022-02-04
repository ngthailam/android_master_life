package vn.thailam.android.masterlife.data.dao

import vn.thailam.android.masterlife.data.entity.UserEntity

interface AuthDao {
    fun isLoggedIn(): Boolean

    fun getUser(): UserEntity?

    fun saveUser(name: String)
}

class AuthDaoImpl : AuthDao {
    override fun isLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUser(): UserEntity {
        return UserEntity(name = "Lam")
    }

    override fun saveUser(name: String) {
        TODO("Not yet implemented")
    }
}