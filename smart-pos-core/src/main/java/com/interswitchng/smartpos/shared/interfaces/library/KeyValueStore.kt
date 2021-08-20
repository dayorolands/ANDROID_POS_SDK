package com.interswitchng.smartpos.shared.interfaces.library

/**
 * This interface is responsible for providing quick storage
 * options, enabling quick storage of only primitive types.
 */
internal interface KeyValueStore {


    /**
     * saves a string value using provided key
     *
     * @param key  a string value representing the access key for the stored value
     * @param value  a string containing the value to be stored
     */
    fun saveString(key: String, value: String)


    /**
     * retrieves a string value using the provided key,
     * and if no value is found, it returs the default value
     *
     * @param key a string value representing the access key for the stored value
     * @param default  a string that will be used as default value if no value is found using the access key
     */
    fun getString(key: String, default: String): String


    /**
     * saves a numeric value using the provided key
     *
     * @param key a string value representing the access key for the stored value
     * @param value the numeric value to be stored
     */
    fun saveNumber(key: String, value: Long)


    /**
     * retrieves the numeric value using the provided key
     *
     * @param key a string representing the access key for the stored value
     * @param default a numeric value that will be returned if no value is found using the access key
     */
    fun getNumber(key: String, default: Long): Long

    fun saveBoolean(key: String, value: Boolean)

    fun getBoolean(key: String): Boolean
}