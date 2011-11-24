package com.vvs.webserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WebServerTest {
	private WebServer web = null;
	private Socket clientSoc ;
	
	
	@Before
	public void setUp() {
		clientSoc = new Socket();
		web = new WebServer(clientSoc, false, "www");
		
		BufferedOutputStream out = new BufferedOutputStream ( System.out);
		web.setPP(out );
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void nullSocket() {
		web = new WebServer(null, true, "www");
	}
	
	@Test
	public void processHttpGetBadTest() throws IOException {
		assertFalse(web.processHttpGet("GET /aaa HTTP/1.1"));
	}
	
	@Test
	public void processHttpGetGoodTest() throws IOException {
		assertTrue(web.processHttpGet("GET / HTTP/1.1"));
	}
	
	@Test
	public void nullParameter () throws IOException {
		assertFalse(web.processHttpGet(null));
	}
	
	@Test
	public void twoParamHttpGet() throws IOException {
		assertFalse(web.handleRequest("GET HTTP/1.1"));
	}
}
