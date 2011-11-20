package com.vvs.webserver;

import java.io.IOException;

import static org.mockito.Mockito.*;
import org.junit.Test;

public class WebServerTest {
	private WebServer web = null;
	
	
//	@Before
//	public void setUp() {
//		Socket clientSoc = new Socket();
//		web = new WebServer(clientSoc, false);
//	}
			
	@Test (expected=IllegalArgumentException.class)
	public void nullSocket() {
		web = new WebServer(null, true);
	}
	
	@Test
	public void testHandleRequest() throws IOException {
		web = mock(WebServer.class);
		
		String line = "GET /aaa HTTP/1.1";
		
		web.handleRequest(line, null);
		verify(web).processHttpGet(line, null);
	}
}
