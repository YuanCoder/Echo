package com.echo.util;

import java.net.HttpURLConnection;
import java.net.URL;

/** 
* �ļ�����Ϊ��URLAvailability.java 
* �ļ����ܼ����� ����һ��URL��ַ�Ƿ���Ч 
* @author Jason 
* @time   2010-9-14  
*  
*/  
public class URLAvailability {  
	
	private static URL url;  
	private static HttpURLConnection con;  
	private static int state = -1;  
  
	/** 
	   * ���ܣ���⵱ǰURL�Ƿ�����ӻ��Ƿ���Ч, 
	   * ����������������� 5 ��, ��� 5 �ζ����ɹ�����Ϊ�õ�ַ������ 
	   * @param urlStr ָ��URL�����ַ 
	   * @return URL 
	   */  
	public synchronized boolean isConnect(String urlStr) {  
	   int counts = 0;  
	   boolean flag=false;
	   if (urlStr == null || urlStr.length() <= 0) {                         
	    return flag;                   
	   }  
	   while (counts < 3) {  
	    try {  
	     url = new URL(urlStr);  
	     con = (HttpURLConnection) url.openConnection();  
	     state = con.getResponseCode();  
	     System.out.println((++counts) +"��httpcode= "+state);  
	     if (state == 200) {  
	      System.out.println("URL���ã�");  
	      flag=true;
	      break;  
	     }  
	   
	    }catch (Exception ex) {  
	     counts++;   
	     System.out.println("URL�����ã����ӵ� "+counts+" ��");  
	     urlStr = null;  
	     continue;  
	    }  
	   }  
	   return flag;  
	}  
}  