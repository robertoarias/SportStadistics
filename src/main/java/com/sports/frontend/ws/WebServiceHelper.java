package com.sports.frontend.ws;

import java.io.IOException;
import java.net.Proxy;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebServiceHelper {
	
	private String endpoint;	
	private String encodedAuth;
	private Proxy proxy;
	
	private static OkHttpClient client =null;
	
	public WebServiceHelper(String endpoint, AuthInfo basicAuth,Proxy proxy) {
		this.endpoint = endpoint;
		this.proxy=proxy;
		if (basicAuth!=null)
		{
			this.encodedAuth=base64Encode(basicAuth.getUser(), basicAuth.getPassword());
		}else{
			encodedAuth=null;	
		}		
	}

	public WebServiceResponse sendPostToWS(String jsonRequestString)
	{		
	
		//JSONObject jsonRequest = JSONObject.fromObject(jsonRequestString);
		
		OkHttpClient client =getClient();
		
		Request request = new Request.Builder()
				.url(endpoint)
				.post(RequestBody.create(MediaType.parse("application/json"), jsonRequestString) )
				.addHeader("authorization", "Basic "+this.encodedAuth!=null?this.encodedAuth:"")
				.addHeader("content-type", "application/json")
				.build();
		try {
			Response response = client.newCall(request).execute();
			
			return  new WebServiceResponse(response.code(), response.body().string());
		} catch (IOException e) {
			return null;
			
		}
		
	}
	
	public WebServiceResponse sendPostToWS(JSONObject request)
	{
		return sendPostToWS(request.toString());
	}
	
	public WebServiceResponse sendPostToWS(Map<String,String> request)
	{
		return this.sendPostToWS(JSONObject.fromObject(request).toString());
	}
	
	public WebServiceResponse sendGetToWS()
	{
						
		OkHttpClient client =getClient();
		
		Request request = new Request.Builder()
				.url(endpoint)
				.get()
				.addHeader("authorization", "Basic "+this.encodedAuth!=null?this.encodedAuth:"")
				.build();

		try {
			Response response = client.newCall(request).execute();
			
			return  new WebServiceResponse(response.code(), response.body().string());

		} catch (IOException e) {
			return null;
			
		}
		
	}
	
	private String base64Encode(String user, String password) 
	{
		String plainCreds = user + ":" + password;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		return "Basic "+base64Creds;
		
	}	
	
	private OkHttpClient getClient()
	{
		
		if (client==null)
		{
			if (this.proxy!=null)
			{
				 client = new OkHttpClient().newBuilder().proxy(proxy).connectTimeout(5000, TimeUnit.MILLISECONDS).readTimeout(5000, TimeUnit.MILLISECONDS).build();	
			}else{
				 client = new OkHttpClient().newBuilder().connectTimeout(5000, TimeUnit.MILLISECONDS).readTimeout(5000, TimeUnit.MILLISECONDS).build();
			}	
		}
		return client;
	}
}
