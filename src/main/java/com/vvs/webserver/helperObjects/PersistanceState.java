package com.vvs.webserver.helperObjects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.vvs.Main;

public class PersistanceState {
	private static final String stateFile = "target/data.cfg";
	private File f = new File(stateFile);
	
	private static final int defaultPort = 10008;
	private static final String defaultWWWroot = "www/";
	
	private int latestPort = defaultPort;
	private String latestWWWroot = defaultWWWroot;
	
	
	private boolean update() {
		boolean result = false;
		
		if (! f.exists()) {
			Main.logger.error("Will now initialize state file.");
			this.initialize(f);
			return true;
		}
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			
			this.latestPort = Integer.valueOf(in.readLine());
			this.latestWWWroot = in.readLine();
			result = true;
			
		} catch (Exception e) {
			Main.logger.error("Problem reading state file: will now reset it.");
			this.initialize(f);
		}
		
		return result;
	}


	private void initialize(File f) {
		try {
			if (f.exists()) {
				f.delete();
			}

			f.createNewFile();			
			
			BufferedWriter out = new BufferedWriter ( new FileWriter(f));
			
			out.write(latestPort + "\n");
			out.write(latestWWWroot+ " \n");
			
			Main.logger.info("New data was writen to persistance file.");
		} catch (IOException e) {
			Main.logger.error("Persistence state file initialization error: " + e);
		}
	}
	

	public void setPort(int port){
		if (this.latestPort != port) {
			this.latestPort = port;
			this.initialize(f);
		}
	}
	
	public void setWWWroot(String root) {
		if (!this.latestWWWroot.equals(root)) {
			this.latestWWWroot = root;
			this.initialize(f);
		}
	}


	public int getPort() {
		this.update();
		return this.latestPort;
	}
}
