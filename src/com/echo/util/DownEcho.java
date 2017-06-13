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
 * 下载类
 * @author Yuanjp
 * @date 2016年12月13日 上午11:04:06
 *
 */
public class DownEcho {
	
	public static Logger loger=Logger.getLogger(DownEcho.class);
	/**
	 * 
	 * @param urlString 地址 
	 * @param filename  歌名
	 * @param savePath  保存路径
	 * @throws Exception
	 */
	public static boolean download(String urlString, String filename,String savePath){  
        // 构造URL  
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
	        // 从上述SSLContext对象中得到SSLSocketFactory对象  
	        SSLSocketFactory ssf = sslContext.getSocketFactory();
			url = new URL(urlString);
			// 打开连接  
	        con = (HttpsURLConnection) url.openConnection();  
	        // 设置域名校验
	        con.setHostnameVerifier(new DownEcho().new TrustAnyHostnameVerifier());
	        //设置请求超时为10s  
	        con.setConnectTimeout(10*1000);  
	        //
	        con.setSSLSocketFactory(ssf);
	        // 输入流  
	        is= con.getInputStream();  
	      
	        // 1K的数据缓冲  
	        bs= new byte[1024];  
	        // 读取到的数据长度  
	        int len;  
	        // 输出的文件流  
	       File sf=new File(savePath);  
	       System.out.println(sf.getAbsolutePath());
	       if(!sf.exists()){  
	           sf.mkdirs();  
	       }  
	       os= new FileOutputStream(sf.getPath()+"\\"+filename,true);  
	        // 开始读取  
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
			// 完毕，关闭所有链接  
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
     * https 域名校验
     * @author biezhi
     * @since 1.0
     */
    public class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;// 直接返回true
        }
    }
}
