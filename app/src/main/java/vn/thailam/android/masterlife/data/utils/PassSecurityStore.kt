package vn.thailam.android.masterlife.data.utils

interface PassSecurityStore {
    fun encrypt(str: String): String

    fun decrypt(data: Any): String
}

class PassSecurityStoreImpl : PassSecurityStore {
    override fun encrypt(str: String): String {
        TODO("Not yet implemented")

    }

    override fun decrypt(data: Any): String {
        TODO("Not yet implemented")
    }
}