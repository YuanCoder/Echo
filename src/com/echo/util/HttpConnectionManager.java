package com.echo.util;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

@SuppressWarnings("deprecation")
public class HttpConnectionManager {

	private static HttpParams httpParams;
	private static ClientConnectionManager connectionManager;
	
	//���������
	public final static int MAX_TOTAL_CONNECTIONS = 800;
	
	//��ȡ���ӵ����ȴ�ʱ��
	public final static int WAIT_TIMEOUT = 60000;
	
	//ÿ��·�����������
	public final static int MAX_ROUTE_CONNECTIONS = 400;
	
	//���ӳ�ʱʱ��
	public final static int CONNECT_TIMEOUT = 60000;
	
	//��ȡ��ʱʱ��
	public final static int READ_TIMEOUT = 60000;
	
	static {
		httpParams = new BasicHttpParams();
		// �������������
		ConnManagerParams.setMaxTotalConnections(httpParams, MAX_TOTAL_CONNECTIONS);
		// ���û�ȡ���ӵ����ȴ�ʱ��
		ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
		// ����ÿ��·�����������
		ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams,connPerRoute);
		
		// �������ӳ�ʱʱ��
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
		// ���ö�ȡ��ʱʱ��
		HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
	
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		connectionManager = new ThreadSafeClientConnManager(httpParams, registry);
	}
	
	
	public static HttpClient getHttpClient(String proxyIp, Integer proxyPort){
		DefaultHttpClient client = new DefaultHttpClient(connectionManager, httpParams);
		
		if(proxyIp !=null && proxyPort !=null){
			HttpHost proxy = new HttpHost(proxyIp, proxyPort);
			client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		}
		return client;
	}
	
	
	
	
	
	
	
}