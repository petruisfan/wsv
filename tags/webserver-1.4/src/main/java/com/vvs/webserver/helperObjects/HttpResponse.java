package com.vvs.webserver.helperObjects;

import java.security.InvalidParameterException;

/**
 * Used mainly for the http response headers.
 * @author petre
 *
 */
public class HttpResponse {
	private String protocol = "HTTP/1.1";
	private int responseCode = 200;
	private String responseStatus = "Ok";
	private String server = "VVS_webserver";
	private String contentType = "text/html";
	private int length = 0;
	

	public HttpResponse(int len) {
		if (len < 0) {
			throw new InvalidParameterException();
		}
		this.length = len;
	}

	public void setContentType(String type ) {
		if (type  == null) {
			throw new InvalidParameterException();
		}
		this.contentType = type;
	}

	public String getHeader () {
		String result = "";

		result += protocol + " " + responseCode + " " + responseStatus + "\n";
		result += "Server: " + server + "\n";
		result += "Content-Type: " + contentType + "\n";
		result += "Content-Length: " + length + "\n";
		
		return result + "\n";
	}
	
	public void setResponseCode(int responseCode) {
		if (responseCode >= 100 && responseCode <600) {
			this.responseCode = responseCode;
		} else
			throw new InvalidParameterException();
	}
	
	public void setResponseStatus(String responseStatus) {
		if (responseStatus == null) {
			throw new InvalidParameterException();
		}
		this.responseStatus = responseStatus;
	}
}
