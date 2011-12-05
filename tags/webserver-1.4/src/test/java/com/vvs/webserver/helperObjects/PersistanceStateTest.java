package com.vvs.webserver.helperObjects;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class PersistanceStateTest {
	private PersistanceState state;
	private String stateFile;


	@SuppressWarnings("static-access")
	@Before
	public void setUp() {
		state = new PersistanceState();
		stateFile = state.getStatefile();
	}

	@Test
	public void constructorTest() {
		state = new PersistanceState();
		this.updateTest();
	}

	@Test
	public void portTest() {
		state.setPort(200);

		assertEquals(200, state.getPort());
		assertEquals("200", this.getLine(1));
	}
	
	@Test
	public void rootTest() {
		state.setWWWroot("www22");

		assertEquals("www22", state.getWWWroot());
		assertEquals("www22", this.getLine(2));
	}
	
	@Test
	public void portTest2() {
		int port = state.getPort();
		state.setPort(port);

		assertEquals(port, state.getPort());
		assertEquals(Integer.toString(port), this.getLine(1));
	}
	
	@Test
	public void rootTest2() {
		String root = state.getWWWroot();
		state.setWWWroot(root);

		assertEquals(root, state.getWWWroot());
		assertEquals(root, this.getLine(2));
	}

	@Test
	public void initializeTest() {
		File f = new File(stateFile);
		
		boolean result = state.initialize(f);
		assertTrue(result);
		
		int filePort = Integer.valueOf(this.getLine(1));
		
		assertEquals(state.getPort(), filePort);
		assertTrue(state.getWWWroot().equals( this.getLine(2) ));
	}
	
	@Test
	public void updateTest() {
		boolean result = state.update();
		assertTrue(result);
		
		int filePort = Integer.valueOf(this.getLine(1));
		
		assertEquals(state.getPort(), filePort);
		assertTrue(state.getWWWroot().equals( this.getLine(2) ));
	}
	
	@Test
	public void updateTest2() {
		File f = new File(stateFile);
		if (f.exists()) {
			f.delete();
		}
		
		boolean result = state.update();
		assertTrue(result);
		
		int filePort = Integer.valueOf(this.getLine(1));
		
		assertEquals(state.getPort(), filePort);
		assertTrue(state.getWWWroot().equals( this.getLine(2) ));
	}
	
	@Test
	public void updateTest3() throws IOException {
		File f = new File(stateFile);
		if (f.exists()) {
			FileWriter out = new FileWriter(f);
			out.write("aaa\n");
		}
		
		boolean result = state.update();
		assertFalse(result);
		
		int filePort = Integer.valueOf(this.getLine(1));
		
		assertEquals(state.getPort(), filePort);
		assertTrue(state.getWWWroot().equals( this.getLine(2) ));
	}
	
	private String getLine(int nr) {
		BufferedReader in = null;
		File f = new File(stateFile);
		String result = null;
		
		try {
			in = new BufferedReader(new FileReader(f));

			//
			// Some trick for reading the line:
			//
			while (nr != 0 ) {
				result = in.readLine();
				nr--;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		
		return result;
	}
}
