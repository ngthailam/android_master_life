package vn.thailam.android.masterlife.data.sources.local

import android.util.Base64
import vn.thailam.android.masterlife.data.repo.KeyRepo
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec

interface PassSecurityStore {
    fun encrypt(data: String): String

    fun decrypt(base64String: String): String
}

class PassSecurityStoreImpl(
    private val keyRepo: KeyRepo
) : PassSecurityStore {

    companion object {
        private const val KEY_ALIAS_PASS_SAVER = "ALIAS_PASS_SAVER_2"
        private const val CIPHER_TRANSFORMATION_AES_GCM_NOPADDING = "AES/GCM/NoPadding"
        private const val IV_SEPARATOR = "]"
    }

    var passSaverSecretKey: SecretKey? = null

    override fun encrypt(data: String): String {
        setSecretKey()
        //
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_AES_GCM_NOPADDING)
        cipher.init(Cipher.ENCRYPT_MODE, passSaverSecretKey)

        val iv = cipher.iv
        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
        val encryptedBytes = cipher.doFinal(data.toByteArray())

        return StringBuilder()
            .append(ivString + IV_SEPARATOR)
            .append(Base64.encodeToString(encryptedBytes, Base64.DEFAULT))
            .toString()
    }

    override fun decrypt(base64String: String): String {
        setSecretKey()
        //
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_AES_GCM_NOPADDING)

        var encodedString: String

        val split = base64String.split(IV_SEPARATOR.toRegex())
        if (split.size != 2) throw IllegalArgumentException("Passed data is incorrect. There was no IV specified with it.")

        val ivString = split[0] as String
        encodedString = split[1]
        val iv =  IvParameterSpec(Base64.decode(ivString, Base64.DEFAULT)).iv
        val spec = GCMParameterSpec(128, iv)

        cipher.init(Cipher.DECRYPT_MODE, passSaverSecretKey, spec)

        val encryptedData = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData)
    }

    private fun setSecretKey() {
        if (passSaverSecretKey == null) {
            passSaverSecretKey = keyRepo.getSecretKey(KEY_ALIAS_PASS_SAVER)
        }
    }
}