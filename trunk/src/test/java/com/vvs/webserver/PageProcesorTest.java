package com.vvs.webserver;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

public class PageProcesorTest {
	private PageProcesor pp = null;
	private PrintWriter out = null;
	
	
	@Before
	public void setUp() {
		pp = new PageProcesor("/var/www/");
		out= new PrintWriter(System.out);
	}
	
	// constructor tests
	@Test (expected=IllegalArgumentException.class)
	public void nullConstructor() {
		pp = new PageProcesor(null);
		
	}
	
	// processPage() tests
	@Test (expected=IllegalArgumentException.class)
	public void nullPage() throws FileNotFoundException {
	
		pp.processPage(null, out);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void nullPrintWriter() {
		pp.processPage("string", null);
	}
	
	@Test 
	public void invalidPage() {
		boolean b = pp.processPage("this page does not exist", out);
		assertFalse(b);
	}
	
	@Test
	public void testOK() {
		
	}
	
	// maintenance tests
	@Test (expected=IllegalArgumentException.class)
	public void nullOut() {
		pp.maintenance(null);
	}
}
