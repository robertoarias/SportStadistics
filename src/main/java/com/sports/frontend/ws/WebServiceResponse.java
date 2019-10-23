package com.sports.frontend.ws;

public class WebServiceResponse {
	private Integer responseCode;
	private String responseString;
	
	public WebServiceResponse(Integer responseCode, String responseString) {
		super();
		this.responseCode = responseCode;
		this.responseString = responseString;
	}
	public Integer getResponseCode() {
		return responseCode;
	}
	public String getResponseString() {
		return responseString;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	
	
	
}
