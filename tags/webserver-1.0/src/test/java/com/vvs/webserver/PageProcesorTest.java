package com.vvs.webserver;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class PageProcesorTest {
	private PageProcesor pp = null;
	
	
	@Before
	public void setUp() {
		pp = new PageProcesor("/var/www/", null);
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
		
	}
	
	// maintenance tests
	@Test (expected=IllegalArgumentException.class)
	public void nullOut() throws IOException {
		pp.maintenance();
	}
}