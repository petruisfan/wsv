package com.vvs;

import java.io.File;

import org.apache.log4j.Logger;

import com.vvs.webserver.cmd.CMD;
import com.vvs.webserver.gui.GUI;

public class Main {
	public static final Logger logger = Logger.getLogger("");
	
	public static void main(String[] args) {

		if (args.length == 0 || args[0].equals("-help")) {
			printHelpMessage();
			logger.info("Printed help message.");
			
		} else if (args[0].equals("-c")) {
			startCmd();
			logger.info("Started command line.");
			
		} else if (args[0].equals("-g")) {
			startGUI();
			logger.info("Started GUI mode.");
			
		}
		System.exit(0);
	}
	
	private static void printHelpMessage() {
		System.out.println(" Usage: \n" +
				"-c\tStart terminal mode.\n" +
				"-g\tStart graphical mode.\n" +
				"-help\tPrint this help message.\n");
	}

	private static void startCmd() {
		CMD cmd = new CMD();
		try {
			cmd.run();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private static void startGUI () {
		GUI gui = new GUI();
		gui.setVisible(true);
	}
}
