package com.echo.test;

import java.io.IOException;

import org.junit.Test;

import com.echo.util.HttpUtil;

public class testHttpUtil
{

	@Test
	public void  testHttp() throws IOException{
		
	 	String url="http://www.app-echo.com/sound/757934";//http://www.app-echo.com/#/
//		String url="http://www.app-echo.com/#/";
		String html=HttpUtil.get(url);
		System.out.println("html="+html);
	}

}
