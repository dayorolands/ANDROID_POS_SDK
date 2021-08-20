package com.interswitchng.smartpos.usb.services

import com.interswitchng.smartpos.shared.utilities.Logger
import com.interswitchng.smartpos.usb.interfaces.UsbConnector
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.concurrent.Executors

typealias ResponseListener = (String?) -> Unit

class UsbConnectionManager: UsbConnector {

    private val logger by lazy { Logger.with("UsbConnector") }

    private val serverSocketFactory = { ServerSocket(LISTENING_PORT) }
    private var serverSocket = serverSocketFactory()
    // executor for async message send/receive
    private val executor = Executors.newCachedThreadPool()
    // connected client socket
    private var mClientSocket: Socket? = null

    companion object { const val LISTENING_PORT = 39300 }

    // read data from socket's inputstream
    override fun receive(): String {
        return try {
            val inputStream = mClientSocket?.getInputStream() ?: return ""
            val reader = BufferedReader(InputStreamReader(inputStream))

            return reader.readLine() ?: ""
        } catch (e: SocketException) {
            logger.log("Socket Exception when receiving message: ${e.message ?: e.localizedMessage}")
            "" // return empty string as response
        }
    }

    // send message, then wait for response
    @Throws(IOException::class)
    override fun sendReceive(message: String): String {
        send(message)
        return receive()
    }

    // function to check if connection is open
    override fun isOpen() = !serverSocket.isClosed

    // create server and listen for client(USB) connection
    override fun open(): Boolean {
        return try {
            // create new socket if current is closed
            if (serverSocket.isClosed) serverSocket = serverSocketFactory()

            mClientSocket = serverSocket.accept()
            isOpen()
        } catch (e: SocketException) {
            logger.log("Socket Exception when opening connection: ${e.message ?: e.localizedMessage}")
            false
        }
    }

    // close connection
    override fun close(): Boolean {
        if (!serverSocket.isClosed) serverSocket.close()
        mClientSocket?.close()
        mClientSocket = null
        return serverSocket.isClosed
    }

    // send message to the connected USB client
    override fun send(message: String): Boolean {

        val outputStream = mClientSocket?.getOutputStream() ?: return false
        val writer = PrintWriter(outputStream, true)
        writer.println(message + "\n")
        return true
    }

    // send message asynchronously to the connected USB client
    override fun sendAsync(message: String) {
        executor.execute {
            send(message)
        }
    }

    // receive message asynchronously
    override fun receiveAsync(listener: ResponseListener) {

        executor.execute {
            val data = receive()
            listener(data)
        }
    }
}