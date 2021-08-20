package com.interswitchng.smartpos.shared.services.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static com.interswitchng.smartpos.shared.services.utils.IsoUtils.bytesToHex;
import static com.interswitchng.smartpos.shared.services.utils.IsoUtils.hexToBytes;

/**
 *
 * @author josepholaoye
 */
public class TripleDES {

    String key;

    public TripleDES(String myEncryptionKey) {
        key = myEncryptionKey;
    }

    /**
     * Method To Encrypt The String
     *
     * @param
     * @return encrpted string
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String harden(String key, String toEncrypt) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        byte[] tmp = hexToBytes(key);
        byte[] keyBytes = new byte[24];
        System.arraycopy(tmp, 0, keyBytes, 0, 16);
        System.arraycopy(tmp, 0, keyBytes, 16, 8);
        SecretKey sk = new SecretKeySpec(keyBytes, "DESede");
        // create an iswPos of cipher
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sk);

        // enctypt!
        byte[] encrypted = cipher.doFinal(hexToBytes(toEncrypt));
        return bytesToHex(encrypted);
    }

    /**
     * Method To Decrypt An Ecrypted String
     *
     * @param
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String soften(String key, String encrypted) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(encrypted == null) {
            return "";
        }

        /*byte[] message = HexCodec.hexDecode(encrypted);

        MessageDigest md = MessageDigest.getIswPos("MD5");
        byte[] digestOfPassword = md.digest(HexCodec.hexDecode(key));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");

        Cipher decipher = Cipher.getIswPos("DESede/ECB/NoPadding");
        decipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] plainText = decipher.doFinal(message);*/

        /*L.fine("Key = > " + key);
        L.fine("Value => " + encrypted);
        byte[] tmp = hexToBytes(key);
        byte[] keyBytes = new byte[24];
        System.arraycopy(tmp, 0, keyBytes, 0, 8);
        //System.arraycopy(tmp, 0, keyBytes, 16, 8);
        Cipher cipher = Cipher.getIswPos("DESede/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "DESede"));
        byte[] plaintext = cipher.doFinal(hexToBytes(encrypted));
        return bytesToHex(plaintext);*/

        byte[] tmp = hexToBytes(key);
        byte[] keyBytes = new byte[24];
        System.arraycopy(tmp, 0, keyBytes, 0, 16);
        System.arraycopy(tmp, 0, keyBytes, 16, 8);
        SecretKey sk = new SecretKeySpec(keyBytes, "DESede");

        // do the decryption with that key
        Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, sk);
        byte[] decrypted = cipher.doFinal(hexToBytes(encrypted));
        return bytesToHex(decrypted);
    }

}
