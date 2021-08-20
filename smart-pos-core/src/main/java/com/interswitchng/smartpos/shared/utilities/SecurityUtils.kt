package com.interswitchng.smartpos.shared.utilities

import android.annotation.TargetApi
import android.app.Application
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.math.BigInteger
import java.security.*
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal


object SecurityUtils {
    private const val ENCRYPTION_ALIAS = "MASTER_KEY"
    private const val TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding"


    private val keyStore by lazy {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        return@lazy keyStore
    }

    /**
     * This function encrypts provided data using provided encryption key
     *
     * @param data the value to be encrypted
     * @param key the encryption key to be used
     * @return  a base64 encoded string containing the encrypted value
     */
    fun encrypt(data: ByteArray, key: Key): ByteArray {
        // initialize cipher
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION_ASYMMETRIC)
        cipher.init(Cipher.ENCRYPT_MODE, key)

        // encrypt data
        return cipher.doFinal(data)
    }


    /**
     * This function decrypts provided data with the provided decryption key
     *
     * @param data the value to be decrypted
     * @param key the decryption key to be used
     * @return the string containing the decrypted value
     */
    fun decrypt(data: ByteArray, key: Key): ByteArray {
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION_ASYMMETRIC)
        cipher.init(Cipher.DECRYPT_MODE, key)
        return cipher.doFinal(data)
    }

    /**
     * This function returns an RSA [KeyPair] that stored within the AndroidKeyStore, if it exists
     *
     * @return  the keypair containing the RSA keypair values or null if none exits within the store
     * @see [KeyPair]
     */
    fun getSavedKeyPair(): KeyPair? {
        // extract key pair
        val privateKey = keyStore.getKey(ENCRYPTION_ALIAS, null) as PrivateKey?
        val publicKey = keyStore.getCertificate(ENCRYPTION_ALIAS)?.publicKey

        // validate not null and return keyPair
        val eitherIsNull = privateKey == null || publicKey == null
        return if (eitherIsNull) null
        else KeyPair(publicKey, privateKey)

    }


    /**
     * This function creates an RSA [KeyPair] saves and returns it
     *
     * @param app the current android Application object
     * @return the created KeyPair
     */
    fun generateKeyPair(app: Application): KeyPair {
        val generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")

        val isMarshMallow = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        if (isMarshMallow) {
            initGeneratorWithKeyGenParameterSpec(generator)
        } else {
            initGeneratorWithKeyPairGeneratorSpec(generator, app)
        }

        // Generates Key with given spec and saves it to the KeyStore
        return generator.generateKeyPair()
    }


    /**
     * This function initializes a KeyPairGenerator for android devices on and above MarshMallow
     *
     * @param generator the [KeyPairGenerator] to be initialized
     */
    @TargetApi(Build.VERSION_CODES.M)
    private fun initGeneratorWithKeyGenParameterSpec(generator: KeyPairGenerator) {
        val builder = KeyGenParameterSpec.Builder(ENCRYPTION_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)

        generator.initialize(builder.build())
    }

    /**
     * This function initializes a KeyPairGenerator for android devices below MarshMallow
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initGeneratorWithKeyPairGeneratorSpec(generator: KeyPairGenerator, app: Application) {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.YEAR, 20)

        val builder = KeyPairGeneratorSpec.Builder(app.applicationContext)
                .setAlias(ENCRYPTION_ALIAS)
                .setSerialNumber(BigInteger.ONE)
                .setSubject(X500Principal("CN=$ENCRYPTION_ALIAS CA Certificate"))
                .setStartDate(startDate.time)
                .setEndDate(endDate.time)

        generator.initialize(builder.build())
    }


    /**
     * This function is reponsible for hashing string text using the MD5 hashing algorithm
     *
     * @param passwordToHash the password string to be hashed
     * @return the hashed password
     */
    fun getSecurePassword(passwordToHash: String): String {
        var generatedPassword = ""
        try {
            // create MessageDigest instance for MD5
            val md = MessageDigest.getInstance("MD5")

            // get the hashed bytes
            val bytes = md.digest(passwordToHash.toByteArray())

            // convert the bytes to hexadecimal format from decimal format;
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(Integer.toString((bytes[i].toInt() and 0xff) + 0x100, 16).substring(1))
            }
            // get complete hashed password in hex format
            generatedPassword = sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return generatedPassword
    }

    fun getHash(passwordToHash: String, hashType: String = "SHA-256"): String {
        try {
            val digest = MessageDigest.getInstance(hashType)
            digest.update(passwordToHash.toByteArray())
            val messageDigest = digest.digest()

            //create Hex String
            val hexString = StringBuilder()
            for (i in messageDigest.indices)
                hexString.append(String.format("%02x", 0xFF and messageDigest[i].toInt()))

            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}