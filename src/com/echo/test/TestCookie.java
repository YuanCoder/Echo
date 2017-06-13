package com.echo.test;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.echo.util.HttpUserAgent;

public class TestCookie
{

      public static void main(String[] args){
    	  
    	  CloseableHttpClient client=null;
    	  CloseableHttpResponse httpResponse=null;
    	  try{
    		  CookieStore cookieStore=new BasicCookieStore();//保存cookie
    		  client=HttpClients.custom().setDefaultCookieStore(cookieStore).build();//创建httpclient;
    		  HttpGet get=new HttpGet("http://www.app-echo.com/v/dist-prod/style.css?v=99c5906");
    		
    		  get.setHeader("Cookie", "PHPSESSID=admunbjjjp2l6v5nfq7uckb1v1; echo_language=0fa769e85f49c8f39f1a51b419d5ec98c7821fcdb7666236b7c498a20cee27fea%3A2%3A%7Bi%3A0%3Bs%3A13%3A%22echo_language%22%3Bi%3A1%3Bs%3A2%3A%22cn%22%3B%7D; _csrf=03c9b2ae0599c72ef55836e471fb43ce4c9c83aedf7f743087d804b0fb928610a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%227TJFB4HfrYks-6EHqvuA9s5iCdJXa5k6%22%3B%7D");
    		  httpResponse=client.execute(get);
    		  
    		  System.out.println("返回："+httpResponse.getEntity().toString());
    		  List<Cookie> cookies=cookieStore.getCookies();//获取cookies
    		  for(int i=0;i<cookies.size();i++){
    			  System.out.println("获取到的cookies:"+cookies.get(i));
    		  }
    	  }catch(Exception e){
    		  e.printStackTrace();
    	  }finally{
    		  try
			{
				httpResponse.close();
				 client.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 
    	  }
      }

}
