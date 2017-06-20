package com.echo.spider;


import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import com.echo.constant.ConstantParams;
import com.echo.util.ClientGet;
import com.echo.util.DownEcho;
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
  		   String url=ConstantParams.URL+id;
		   String musicName=null; //歌名
		   JSONArray jsonAry=null;
		   String musicUrl=null;
		   String picUrl=null; 
		   boolean flag=true;
		   HttpGet httpGet = new HttpGet(url);// 创建get请求    
	        httpGet.setHeader("Host", ConstantParams.HOST);
			httpGet.setHeader("Accept-Language", ConstantParams.ACCEPT_LANGUAGE);
			httpGet.setHeader("Accept-Charset", ConstantParams.ACCEPT_CHARSET);
			httpGet.setHeader("Accept", ConstantParams.ACCEPT);
			httpGet.setHeader("Accept-Encoding", ConstantParams.Accept_Encoding); //Accept-Encoding 是浏览器发给服务器,声明浏览器支持的编码类型
			httpGet.setHeader("User-Agent", ConstantParams.User_Agent);
			httpGet.setHeader("Referer", ConstantParams.REFERER);  //告诉服务器是从哪个页面链接过来的，
			httpGet.setHeader("X-Requested-With", ConstantParams.X_REQUESTED_WITH); 
			httpGet.setHeader("Cookie", ConstantParams.COOKIE);
			httpGet.setHeader("Connection", ConstantParams.CONNECTION);
		   
		   try{
			   String resultJson=ClientGet.getInstance().sendHttpGet(httpGet);
			
			   loger.debug("resultJson="+resultJson.toString());
			   if(StringUtil.isEmpty(resultJson)){
				  return false;
			   }
			
			   JSONObject soundObj=JSONObject.fromObject(resultJson);
			   if(soundObj.isNullObject()){
				   loger.debug("soundObj为空");
				   return false;
			   }
			   jsonAry=soundObj.getJSONArray(ConstantParams.DESC);
			   if(jsonAry.isEmpty()){
				   return false;
			   }
			   loger.debug("jsonAry="+jsonAry.toString());
			   soundObj=jsonAry.getJSONObject(0);
			   
			   musicName=soundObj.getString(ConstantParams.NAME);
			   musicUrl=soundObj.getString(ConstantParams.SOURCE);
			   picUrl=soundObj.getString(ConstantParams.PIC);
			
			   if(musicName!=null) musicName=musicName.trim();
			   musicName=StringUtil.filterStr(musicName); 
			   flag=DownEcho.download(musicUrl, musicName+".mp3", ConstantParams.SAVE_MUSIC_PATH);//main\resources  WebContent/Music2/
			  if(!flag){
				  return false;
			  }
			   flag=DownEcho.download(picUrl, musicName+".png", ConstantParams.SAVE_PIC_PATH);//main\resources  WebContent/Music2/
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
