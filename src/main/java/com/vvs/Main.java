package com.vvs;

import java.io.File;

import com.vvs.webserver.ConnectionManager;
import com.vvs.webserver.gui.GUI;

public class Main {

	public static void main(String[] args) {
		
		File f = new File(".");
		System.out.println(f.getAbsoluteFile());
		
		if (args.length == 0 || args[0].equals("-help")) {
			printHelpMessage();
		} else if (args[0].equals("-c")) {
			startCmd();
		} else if (args[0].equals("-g")) {
			startGUI();
		}
	}
	
	private static void printHelpMessage() {
		System.out.println(" Usage: \n" +
				"-c\tStart terminal mode.\n" +
				"-g\tStart graphical mode.\n" +
				"-help\tPrint this help message.\n");
	}

	private static void startCmd() {
		ConnectionManager server = new ConnectionManager();
		Thread thread = new Thread(server);
		
		thread.start();
		
		System.out.println("running");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//server.setMaintenace(true);
	}
	
	private static void startGUI () {
		GUI gui = new GUI();
		gui.setVisible(true);
	}
}
