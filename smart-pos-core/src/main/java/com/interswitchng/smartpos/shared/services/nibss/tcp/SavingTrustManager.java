package com.interswitchng.smartpos.shared.services.nibss.tcp;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Created by joel.dada on 11/1/2018.
 */

// technical dept... please remove this and install certificate
public class SavingTrustManager implements X509TrustManager {

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

}
