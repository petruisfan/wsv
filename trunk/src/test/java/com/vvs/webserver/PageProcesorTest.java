package com.vvs.webserver;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class PageProcesorTest {
	private PageProcesor pp = null;
	private BufferedOutputStream out;
	private ByteArrayOutputStream byteStream;
	
	@Before
	public void setUp() {
		byteStream = new ByteArrayOutputStream();
		
		out = new BufferedOutputStream(byteStream);
		
		pp = new PageProcesor("/var/www/", out);
	}
	
	// constructor tests
	@Test (expected=IllegalArgumentException.class)
	public void nullConstructor() {
		pp = new PageProcesor(null, null);
		
	}
	
	// processPage() tests
	@Test (expected=IllegalArgumentException.class)
	public void nullPage() throws FileNotFoundException {
	
		pp.processPage(null);
	}
	
	@Test 
	public void invalidPage() {
		boolean b = pp.processPage("this page does not exist");
		assertFalse(b);
	}
	
	@Test
	public void testOK() {
		// TODO make this work, see white byteStream is not writen.
		
		boolean rez = pp.processPage("index.html");
		
		//System.out.println(byteStream);
		
		assertTrue(rez);
	}
	
	// maintenance tests
	@Test (expected=IllegalArgumentException.class)
	public void nullOut() throws IOException {
		pp = new PageProcesor("/var/www/", null);
		
		pp.maintenance();
	}
}
