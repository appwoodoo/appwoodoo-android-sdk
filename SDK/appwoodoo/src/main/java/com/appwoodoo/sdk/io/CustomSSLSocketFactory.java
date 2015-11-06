package com.appwoodoo.sdk.io;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CustomSSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory {

	private SSLSocketFactory FACTORY = HttpsURLConnection.getDefaultSSLSocketFactory();

	public CustomSSLSocketFactory(KeyStore keyStore) throws GeneralSecurityException {
		super(keyStore);

    	SSLContext context = SSLContext.getInstance ("TLS");
        TrustManager[] tm = new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
        }};
        context.init(null, tm, new SecureRandom());

        FACTORY = context.getSocketFactory();
	}

	@Override
	public Socket createSocket() throws IOException {
	    return FACTORY.createSocket();
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
		return FACTORY.createSocket(socket, host, port, autoClose);
	}

}
