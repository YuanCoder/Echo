package com.echo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * @author Yuanjp
 * @date 2016/12/10
 * @deprecated echo 请求工具类
 */
public class HttpUtil {
		
		// 创建CookieStore实例  
	    public static Logger loger=Logger.getLogger(HttpUtil.class);
	    static CookieStore cookieStore = null;  
	    static HttpClientContext context = null;  
	    
		public static String get( String url) throws IOException{
			   
			String content=null;
			HttpEntity entity=null;
 			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet=new HttpGet(url);
			httpGet.setHeader("Host", "www.app-echo.com");
 			httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
 			httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
 			httpGet.setHeader("Accept", "application/json,text/javascript,*/*;q=0.01");
 			httpGet.setHeader("Accept-Encoding", "gzip, deflate"); //Accept-Encoding 是浏览器发给服务器,声明浏览器支持的编码类型
 			httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
 			httpGet.setHeader("Referer", "http://www.app-echo.com/");  //告诉服务器是从哪个页面链接过来的，
 			httpGet.setHeader("X-Requested-With", "XMLHttpRequest"); 
 			httpGet.setHeader("Cookie", "PHPSESSID=admunbjjjp2l6v5nfq7uckb1v1; echo_language=0fa769e85f49c8f39f1a51b419d5ec98c7821fcdb7666236b7c498a20cee27fea%3A2%3A%7Bi%3A0%3Bs%3A13%3A%22echo_language%22%3Bi%3A1%3Bs%3A2%3A%22cn%22%3B%7D; _csrf=03c9b2ae0599c72ef55836e471fb43ce4c9c83aedf7f743087d804b0fb928610a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%227TJFB4HfrYks-6EHqvuA9s5iCdJXa5k6%22%3B%7D; MP_LIST=; Hm_lvt_46b3b8e7eb78200527b089c276c81a7e=1481631600; Hm_lpvt_46b3b8e7eb78200527b089c276c81a7e=1481637261");
 			httpGet.setHeader("Connection", "keep-alive");
		try {
			InputStream ins=null; 
			HttpResponse response = client.execute(httpGet);
			String hs=response.getAllHeaders().toString();
			
			int statuCode = response.getStatusLine().getStatusCode();
			if(statuCode==200){
				entity=response.getEntity();  
				Header contentEncoding = response.getFirstHeader("Content-Encoding");
				if(entity!=null){
	 			    	ins=entity.getContent();
				 }
				if(contentEncoding == null){  //服务器返回未压缩的正文
					 content = convertStreamToString(ins,"utf-8");
				}else{    //服务器返回GZIP压缩的正文
					 content = uncompress(ins,"utf-8");
				}
				
			}else{
				loger.debug("资源不存在 statuCode="+statuCode);
				return "资源不存在";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(entity!=null)
				try {
					EntityUtils.consume(entity);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(client!=null)
 					  client.close();
					
		}
			return content;
		}
		
		/**
		 *  防止响应返回乱码
		 * @param is
		 * @param charset
		 * @return
		 */
		public static String convertStreamToString(InputStream is, String charset) {      
	        StringBuilder sb1 = new StringBuilder();      
	        byte[] bytes = new byte[4096];    
	        int size = 0;    
	          
	        try {      
	            while ((size = is.read(bytes)) > 0) {    
	                String str = new String(bytes, 0, size, charset);    
	                sb1.append(str);    
	            }    
	        } catch (IOException e) {      
	            e.printStackTrace();      
	        } finally {      
	            try {      
	                is.close();      
	            } catch (IOException e) {      
	               e.printStackTrace();      
	            }      
	        }      
	        return sb1.toString();      
	    }  
		
		/**
		 * GZIP解压
		 * @param in
		 * @param charset 字符编码格式
		 * @return
		 */
		private static String uncompress(InputStream in, String charset) {
		    StringBuilder sb1 = new StringBuilder();   
		    byte[] bytes = new byte[256];
			int size=0;
		    String str=null;
		    GZIPInputStream gunzip=null;
			try {
				gunzip = new GZIPInputStream(in);
				
				while((size = gunzip.read(bytes)) >=0 ){
			    	str= new String(bytes, 0, size, charset);  
			    	sb1.append(str);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}finally {      
	            try {      
	            	if(gunzip!=null){
	            		gunzip.close();      
	            	}
	            } catch (IOException e) {      
	               e.printStackTrace();      
	            }      
	        }   
		   return sb1.toString();      
		}
		 
}