package com.echo.spider;


import org.apache.log4j.Logger;

import com.echo.util.DownEcho;
import com.echo.util.HttpUtil;
import com.echo.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 抓取echo音乐
 * @author Yuanjp
 *
 */
@SuppressWarnings("deprecation")
public class EchoSpider {
	
	public static Logger loger=Logger.getLogger(EchoSpider.class);
	/**
	 * 很久之前 用 .m3u8格式保存
	 * 后面用.mp3
	 * 现在又变了
	 * @param id
	 * @return
	 */
	public static boolean  getPageByUrl(String id){
  		   String url="http://www.app-echo.com/sound/api-infos?ids="+id;
// 		   String url="http://www.app-echo.com/api/sound/info?id="+id;//该url也可
		   String musicName=null; //歌名
		   JSONArray jsonAry=null;
		   String musicUrl=null;
		   String picUrl=null; 
		   boolean flag=true;
		   
		   try{
			   String resultJson=HttpUtil.get(url);
			
//			   loger.debug("resultJson="+resultJson.toString());
			   if(StringUtil.isEmpty(resultJson)){
				  return false;
			   }
			
			   JSONObject soundObj=JSONObject.fromObject(resultJson);
			   if(soundObj.isNullObject()){
				   loger.debug("soundObj为空");
				   return false;
			   }
			   jsonAry=soundObj.getJSONArray("desc");
			   if(jsonAry.isEmpty()){
				   return false;
			   }
//			   System.out.println("jsonAry="+jsonAry.toString());
			   soundObj=jsonAry.getJSONObject(0);
//			   System.out.println("soundObject="+soundObj.toString());
			   
			   musicName=soundObj.getString("name");
			   musicUrl=soundObj.getString("source");
			   picUrl=soundObj.getString("pic");
			   
			   loger.debug("musicName="+musicName);
			 /*  loger.debug("musicUrl="+musicUrl);
			   loger.debug("picUrl="+picUrl);*/
			   if(musicName!=null) musicName=musicName.trim();
			   musicName=StringUtil.filterStr(musicName); 
			   flag=DownEcho.download(musicUrl, musicName+".mp3", "c:/echo/music/");//main\resources  WebContent/Music2/
			  if(!flag){
				  return false;
			  }
			   flag=DownEcho.download(picUrl, musicName+".png", "c:/echo/pic/");//main\resources  WebContent/Music2/
			   if(!flag){
					  return false;
				  }
		   }catch(Exception e){
			    e.printStackTrace();
			    return false;
		   }finally{}
		   return true;
	   }
	
	public static void main(String[] args) throws Exception {
		String id="1348680";
		EchoSpider.getPageByUrl(id);
	}
}
