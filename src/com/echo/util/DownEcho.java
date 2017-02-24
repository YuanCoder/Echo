package com.echo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;



/**
 * ������
 * @author Yuan
 *
 */
public class DownEcho {
	
	public static Logger loger=Logger.getLogger(DownEcho.class);
	/**
	 * 
	 * @param urlString ��ַ 
	 * @param filename  ����
	 * @param savePath  ����·��
	 * @throws Exception
	 */
	public static boolean download(String urlString, String filename,String savePath){  
        // ����URL  
        URL url=null;
        HttpsURLConnection con=null;
        InputStream is =null;
        byte[] bs =null;
        OutputStream os =null;
		try
		{
			TrustManager[] tm = { new MyX509TrustManager() };
	        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	        sslContext.init(null, tm, new java.security.SecureRandom());
	        // ������SSLContext�����еõ�SSLSocketFactory����  
	        SSLSocketFactory ssf = sslContext.getSocketFactory();
			url = new URL(urlString);
			// ������  
	        con = (HttpsURLConnection) url.openConnection();  
	        // ��������У��
	        con.setHostnameVerifier(new DownEcho().new TrustAnyHostnameVerifier());
	        //��������ʱΪ10s  
	        con.setConnectTimeout(10*1000);  
	        //
	        con.setSSLSocketFactory(ssf);
	        // ������  
	        is= con.getInputStream();  
	      
	        // 1K�����ݻ���  
	        bs= new byte[1024];  
	        // ��ȡ�������ݳ���  
	        int len;  
	        // ������ļ���  
	       File sf=new File(savePath);  
	       System.out.println(sf.getAbsolutePath());
	       if(!sf.exists()){  
	           sf.mkdirs();  
	       }  
	       os= new FileOutputStream(sf.getPath()+"\\"+filename,true);  
	        // ��ʼ��ȡ  
	        while ((len = is.read(bs)) != -1) {  
	          os.write(bs, 0, len);  
	        }  
	        return true;
		} catch (MalformedURLException e)
		{
			loger.debug(e);
			return false;
		} catch (IOException e)
		{
			loger.debug(e);
			return false;
		} catch (NoSuchAlgorithmException e)
		{
			loger.debug(e);
			return false;
		} catch (NoSuchProviderException e)
		{
			loger.debug(e);
			return false;
		} catch (KeyManagementException e)
		{
			loger.debug(e);
			return false;
		}finally{
			// ��ϣ��ر���������  
			try
			{
				 if(os!=null){os.close();}
				 if(os!=null){is.close();}
			} catch (IOException e)
			{
				loger.debug(e);
				return false;
			}  
	       
		}
        
        
    }   
	
	 /**
     * https ����У��
     * @author biezhi
     * @since 1.0
     */
    public class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;// ֱ�ӷ���true
        }
    }
}
