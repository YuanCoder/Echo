package com.echo.test;

import org.junit.Test;

import com.echo.util.DownEcho;

public class testHttpsDown
{

	 @Test
	 public void testDown() throws Exception{
		 String urlString="https://ws-qn-echo-cp-cdn.app-echo.com/c2_96k/a1c57363deeffde83c0e8d2fadbd50a51d5819fd27a0fed28da9aec75eb069ca13c1e00a.mp3?1482054983";
		 String filename="¡¸ÐÐÊ¬×ßÈâ¡¹ÈÃåó¸ç±»Å°Çú°ïÄãÏ´ÄÔ .mp3";
		 String savePath="c:\\echo\\music2";
		 DownEcho downEcho=new DownEcho();
		 downEcho.download(urlString, filename, savePath);
	 }

}
