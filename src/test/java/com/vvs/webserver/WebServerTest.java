package com.vvs.webserver;

import java.io.PrintWriter;
import java.net.Socket;

import static org.mockito.Mockito.*;
import org.junit.Before;
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
	public void testHandleRequest() {
		web = mock(WebServer.class);
		PrintWriter out = new PrintWriter(System.out);
		
		String line = "GET /aaa HTTP/1.1";
		
		web.handleRequest(line, out);
		verify(web).processHttpGet(line, out);
	}
}
