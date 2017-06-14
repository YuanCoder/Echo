package com.echo.util;

/**
 *  字符串工具类
 * @author Yuanjp
 * @date 2016年12月13日 上午11:04:59
 */
public class StringUtil {

	/**
	 * 判断是否是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 过滤掉/ \ 等字符
	 * @param str
	 * @return
	 */
	public static String filterStr(String str){
		String newStr=null;
		newStr=str.replaceAll("/", "").replaceAll("\\\\",  "");
		
		return newStr;
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtil.filterStr("电音迷不能错过的现场 Around the World / Harder\\ Better Faster Stronger (Live)"));
	}
	
}
