package com.echo.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *  echo请求工具类
 * @author Yuanjp
 * @date 2017年6月20日 下午4:33:19
 *
 */
public class ClientGet {
	private RequestConfig requestConfig = RequestConfig.custom()
	        .setSocketTimeout(30000)  //单位：毫秒
	        .setConnectTimeout(30000)
	        .setConnectionRequestTimeout(30000)
	        .build();  
	
	private static ClientGet instance=null;
	
	private ClientGet(){}
	
	public static ClientGet getInstance(){
		if(instance==null){
			instance=new ClientGet();
		}
		return instance;
	}
	 /**  
     * 发送Get请求  
     * @param httpGet
     * @return  
     */    
    public String sendHttpGet(HttpGet httpGet) {    
        CloseableHttpClient httpClient = null;    
        CloseableHttpResponse response = null;    
        HttpEntity entity = null;    
        String responseContent = null;    
       
        // 执行请求    
        try {
        	  httpClient=HttpClients.createDefault();
              httpGet.setConfig(requestConfig);   
			  response = httpClient.execute(httpGet);
			  int statuCode = response.getStatusLine().getStatusCode();
			  if(statuCode==200){
				  entity = response.getEntity();    
			      responseContent = EntityUtils.toString(entity, "UTF-8"); 
			  }else{
				  responseContent="资源不存在";
			  }
			    
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			 try {    
	                // 关闭连接,释放资源    
	                if (response != null) {    
	                    response.close();    
	                }    
	                if (httpClient != null) {    
	                    httpClient.close();    
	                }    
	            } catch (IOException e) {    
	                e.printStackTrace();    
	            }finally { }    
		}    
       
        return responseContent;
    }
	
	
	 /**  
     * 发送 get请求  
     * @param httpUrl  
     */    
    public String sendHttpGet(String httpUrl) {    
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求    
        return sendHttpGet(httpGet);    
    }    
}
