package com.echo.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * ֤�����ι�����������https����
 * ���֤������������þ���������������ָ����֤�飬����Ĵ�����ζ����������֤�飬�����Ƿ�Ȩ�������䷢��
 * @author Yuan
 *
 */
public class MyX509TrustManager implements  X509TrustManager, TrustManager{

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {

		return null;
	}

}