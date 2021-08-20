package com.interswitchng.smartpos.shared.interfaces.library

/**
 * This interface is responsible for providing a secure communication between
 * the POS device and the EPMS server, publishing messages and reading reponses
 * from the server
 *
 */
internal interface IsoSocket {


    /**
     * Sets the connection ip address and port
     */
    fun setIpAndPort(ip: String, port: Int)

    /**
     * Sets the connection timeout in milliseconds
     */
    fun setTimeout(timeout: Int)

    /**
     * Opens a secure communication with the EPMS server, and returns a
     * boolean to indicates success or failure in establishing the connection
     *
     * @return  boolean that indicates the success or failure of opening connection with the server
     */
    fun open(): Boolean

    /**
     * Closes an open connection with the EPMS server
     */
    fun close()

    /**
     * Sends provided binary to EPMS server, and returns boolean indicating success or failure
     *
     * @param bytes  the binary message to be sent to the server
     * @return  boolean that indicates the success or failure of sending the provided message
     */
    fun send(bytes: ByteArray): Boolean


    /**
     * Receives the returned response by the EPMS server
     *
     * @return  binary response from the server
     */
    fun receive(): ByteArray?


    /**
     * Combines the actions of sending and receiving a message from the EPMS server
     *
     * @param bytes the binary message to be sent to the server
     * @return  binary response from the server
     */
    fun sendReceive(bytes: ByteArray): ByteArray?
}