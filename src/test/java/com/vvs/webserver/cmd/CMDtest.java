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
		cmd = mock(CMD.class);
		
		try {
			when( cmd.readFromKeyboard("Change port number to: ")).thenReturn("2000");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cmd.changePort();
		
		ConnectionManager server = cmd.getServer();
		
		assertEquals(2000, server.getPort());
	}
}
