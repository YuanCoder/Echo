package com.echo.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 证书信任管理器（用于https请求）  20170613将JDK 为1.8    之前为1.6  去掉@Override注解
 * 这个证书管理器的作用就是让它信任我们指定的证书，下面的代码意味着信任所有证书，不管是否权威机构颁发。
 *
 */
public class MyX509TrustManager implements  X509TrustManager, TrustManager{

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		
		
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		
		
	}

	public X509Certificate[] getAcceptedIssuers() {

		return null;
	}

}
