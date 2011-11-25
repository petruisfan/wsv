package com.vvs.webserver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConnectionManagerTest {
	private ConnectionManager conn;
	
	@Before
	public void setUp () {
		conn = new ConnectionManager();
	}
	
	@Test
	public void testStopped() {
		assertEquals("stopped", conn.getServerState());
	}

	public void testToggleMaintenance() {
		conn.toggleMintenance();
		
		assertTrue(conn.maintenance());
	}
	
	@Test 
	public void testReset() {
		conn.setMaintenace(true);
		
		conn.reset();
		
		assertFalse(conn.maintenance());
	}
	
	@Test
	public void testPort(){
		conn.setMaintenace(true);
		
		conn.setPort(2000);
		
		assertEquals(2000, conn.getPort());
	}
	
	@Test
	public void testRoot() {
		conn.setMaintenace(true);
		
		conn.setWebRoot("www222");
		
		assertEquals("www222/", conn.getWwwRoot());
	}
}
