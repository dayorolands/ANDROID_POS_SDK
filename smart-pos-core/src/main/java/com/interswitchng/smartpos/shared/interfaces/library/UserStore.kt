package com.interswitchng.smartpos.shared.interfaces.library


/**
 * This interface is responsible for performing user related actions
 */
internal interface UserStore {

    /**
     * Passes the user's retrieved access token to the provided callback
     *
     * @param callback a function that takes the user's access token to perform its operation
     */
    fun <T> getToken(callback: (String) -> T): T
}