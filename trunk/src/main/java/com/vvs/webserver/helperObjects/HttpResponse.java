package com.vvs.webserver.helperObjects;

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
		this.length = len;
	}

	public void setContentType(String type ) {
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
}
