package com.vvs.webserver.cmd;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.vvs.webserver.ConnectionManager;


public class CMDtest {
	private CMD cmd;
	private ConnectionManager server;
	
	@Before 
	public void setUp() {
		cmd = new CMD();
		server = cmd.getServer();
	}
	
	@Test
	public void changeWebRootTest() {
		cmd = spy ( new CMD());
		
		try {
			//when( cmd.readFromKeyboard("Enter the new web root: ")).thenReturn("www2");
			//
			// The rule above will still be called once. The one below is better.
			//			
			doReturn("www2").when(cmd).readFromKeyboard("Enter the new web root: ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cmd.toggleMaintenance();
		cmd.changeWebRoot();
		
		ConnectionManager server = cmd.getServer();
		
		assertEquals("www2/", server.getWwwRoot());
	}
	
	@Test
	public void changePortTest() {
		cmd = new CMD() {
			@Override
			String readFromKeyboard(String message) throws IOException {
				return "2000";
			}
		};
		
		cmd.toggleMaintenance();
		cmd.changePort();
		
		ConnectionManager server = cmd.getServer();
		
		assertEquals(2000, server.getPort());
	}
	
	@Test
	public void helpMessageTest() {
		String output =  cmd.helpMessage();
		
		String result = "";
		
		result += " s - start the webserver.\n";
		result += " m - toggle maintenance mode on/off.\n";
		result += " k - stop (kill) the webserver.\n";
		result += " c - change port.\n";
		result += " w - change web root.\n";
		result += " p - print current state.\n";
		result += " h - print this help menu.\n";
		result += " q - quit the application.\n";

		assertTrue(result.equals(output));
	}
	
	@Test  
	public void testStartStop () throws InterruptedException {
		assertTrue(cmd.getServer().getServerState().equalsIgnoreCase("stopped"));
		
		cmd.startServer();
		server.run();
		assertTrue(cmd.getServer().getServerState().equalsIgnoreCase("running"));
		
		cmd.toggleMaintenance();
		assertTrue(cmd.getServer().getServerState().equalsIgnoreCase("maintenance"));
		
		cmd.toggleMaintenance();
		assertTrue(cmd.getServer().getServerState().equalsIgnoreCase("running"));
		
		cmd.killServer();
		assertTrue(cmd.getServer().getServerState().equalsIgnoreCase("stopped"));
	}
}
