package com.vvs.webserver.helperObjects;

import static org.junit.Assert.*;

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
}
