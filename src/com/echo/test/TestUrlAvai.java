package com.echo.test;

import com.echo.util.URLAvailability;

public class TestUrlAvai
{

	 public static void main(String[] args)
	{    
		 String urlStr="http://www.app-echo.com/sound/1341667";
		 URLAvailability UrlAvai=new URLAvailability();
		 System.out.println(UrlAvai.isConnect(urlStr));
	}

}
