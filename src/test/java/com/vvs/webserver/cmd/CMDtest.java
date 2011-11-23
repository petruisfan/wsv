package com.vvs.webserver.cmd;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.vvs.webserver.ConnectionManager;


public class CMDtest {
	private CMD cmd;

	
	@Before 
	public void setUp() {

	}
	
	@Test
	public void test() {
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
}
