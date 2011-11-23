package com.vvs.webserver.helperObjects;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Test;

public class HttpResponseTest {

	@Test
	public void testHeader(){
		HttpResponse hr = new HttpResponse(10);

		String headers = "HTTP/1.1 200 Ok\n"+
				"Server: VVS_webserver\n"+
				"Content-Type: text/html\n"+
				"Content-Length: 10\n\n";
		
		assertEquals(headers, hr.getHeader());
	}
	
	@Test
	public void testMethods() {
		HttpResponse hr = new HttpResponse(10);
		
		hr.setContentType("image");
		hr.setResponseCode(404);
		hr.setResponseStatus("Not Found");
		
		//System.out.println(hr.getHeader());
		
		String headers = "HTTP/1.1 404 Not Found\n" +
						"Server: VVS_webserver\n" + 
						"Content-Type: image\n"+
						"Content-Length: 10\n\n";
		
		assertEquals(headers, hr.getHeader());
	}
	
	@Test (expected = InvalidParameterException.class)
	public void testInvalidLength() {
		@SuppressWarnings("unused")
		HttpResponse hr = new HttpResponse(-10);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void testInvalidResponseCode() {
		HttpResponse hr = new HttpResponse(1);
		hr.setResponseCode(999);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void testInvalidResponseStatus() {
		HttpResponse hr = new HttpResponse(1);
		hr.setResponseStatus(null);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void testInvalidContentType() {
		HttpResponse hr = new HttpResponse(1);
		hr.setContentType(null);
	}
}
