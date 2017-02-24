package com.echo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配歌曲json对象
 * @author Yuan
 * @date 2016/12/10
 */
public class RegexUtil {
	 
	/**
	 * 提取歌曲json格式对象
	 * @param script 
	 * @return
	 */
	 public static String getMusicUrl(String script){
		String urlreg="\\{.name.*\\}";
		Pattern p=Pattern.compile(urlreg); 
        Matcher m=p.matcher(script); 
        while(m.find())
        { 
         return m.group(); 
        } 
		 
		 return null;
	 }
	 
	 public static String ModifyMusicUrl(String url){
			String str=null;
			 if(url!=null){
				 str=url.replaceAll("\\/", "");
				 return str;
			 }
			 return str;
		 }
}
