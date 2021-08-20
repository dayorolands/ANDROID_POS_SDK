/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interswitchng.smartpos.shared.services.nibss.tcp;


import android.util.Log;

import androidx.annotation.NonNull;

import com.interswitchng.smartpos.shared.interfaces.library.IsoSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 *
 * @author tosin.eniolorunda
 */

public class IsoSocketImpl implements IsoSocket {

    private SSLSocket socket;
    private SocketAddress socketAddress;
    private String serverIp;
    private int serverPort;
    private int timeout;
    private static final int SOCKET_SIZE_INCREAMENT = 100 * 1024;
    private SSLSocketFactory factory;

    public IsoSocketImpl(SocketAddress socketAddress, int timeout) {
        this.socketAddress = socketAddress;
        this.timeout = timeout;
    }

    public IsoSocketImpl(String serverIp, int serverPort, int timeout) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.timeout = timeout;
        configureSSLContext();
    }

    private void configureSSLContext() {
        SavingTrustManager tm = new SavingTrustManager();
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{tm}, new java.security.SecureRandom());
            factory = sc.getSocketFactory();
        } catch (KeyManagementException |NoSuchAlgorithmException e) {
            logEx(e);
        }
    }

    private void logEx(Exception e) {
        Log.d("Test", "Error", e);
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void setIpAndPort(String ip, int port) {
        this.serverIp = ip;
        this.serverPort = port;
    }

    private byte[] concat(byte[] A, byte[] B) {
        int aLen = A.length;
        int bLen = B.length;
        byte[] C = new byte[aLen + bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    @Override
    public boolean open() {
        try {
            socket = (SSLSocket) factory.createSocket(serverIp, serverPort);
            socket.setSoTimeout(timeout);
            return true;
        } catch (IOException ex) {
            logEx(ex);
            return false;
        }
    }

    public void close() {
        try {
            if (socket.isConnected()) {
                socket.close();
            }
        } catch (IOException ex) {
            logEx(ex);
        }
    }

    @Override
    public boolean send(@NonNull byte[] data) {
        int length = (int) data.length;
        byte[] headerbytes = new byte[2];

        headerbytes[0] = (byte) (length >> 8);
        headerbytes[1] = (byte) length;

        byte[] dataToSend = concat(headerbytes, data);

        if (this.socket.isConnected()) {
            try {
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                os.write(dataToSend);
                os.flush();
                return true;
            } catch (IOException ex) {
                logEx(ex);
            }
        }
        return false;
    }

    private byte[] resize(byte[] data) {
        if (data == null || data.length == 0) {
            return new byte[SOCKET_SIZE_INCREAMENT];
        }
        byte[] newData = new byte[data.length + SOCKET_SIZE_INCREAMENT];
        System.arraycopy(data, 0, newData, 0, data.length);
        return newData;
    }

    public final int readFully(InputStream in, byte b[], int off, int len) throws IOException {
        if (len < 0)
            throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = in.read(b, off + n, len - n);
            if (count < 0)
                break;
            n += count;
        }
        return n;
    }

    /*data comes over the stream untill a timeout or nibss closes the socket*/
    @Override
    public byte[] receive() {
        byte[] lenData = new byte[2];
        byte[] receivedData = null;
        int dataLen;
        try {

            DataInputStream is = new DataInputStream(this.socket.getInputStream());
            readFully(is, lenData, 0, 2);
            //is.readFully(lenData, 0, 2);
            dataLen = ((int)(0xFF & lenData[0])) * 256 + (int)(0xFF & lenData[1]);
            receivedData = new byte[dataLen];
            readFully(is, receivedData, 0, dataLen);

        } catch (IOException ex) {
            logEx(ex);
        }
        return receivedData;
    }

    @Override
    public byte[] sendReceive(@NonNull byte[] data) {
        send(data);
        return receive();
    }
}
