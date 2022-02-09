package vn.thailam.android.masterlife.data.repo

import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


interface KeyRepo {
    fun getSecretKey(alias: String): SecretKey
}

class KeyRepoImpl(
) : KeyRepo {
    companion object {
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }

    override fun getSecretKey(alias: String): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
            load(null)
        }
        val secretKey = keyStore.getKey(alias, null) as? SecretKey

        if (secretKey == null) {
            val newSecretKey = createSecretKey()
            saveSecretKey(alias, newSecretKey)
            return newSecretKey
        }

        return secretKey
    }

    private fun saveSecretKey(alias: String, secretKey: SecretKey) {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
            load(null)
        }
        val purposes = KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        val keyProtection = KeyProtection.Builder(purposes)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyStore.load(null)
        keyStore.setEntry(
            alias,
            KeyStore.SecretKeyEntry(secretKey),
            keyProtection
        )
    }

    private fun createSecretKey(): SecretKey {
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(128)
        return keygen.generateKey()
    }
}