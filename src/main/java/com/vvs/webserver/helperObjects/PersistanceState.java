package com.vvs.webserver.helperObjects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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

	public PersistanceState() {
		this.update();
	}

	/**
	 * Read what is in the file and update the values.
	 * @return
	 */
	boolean update() {
		boolean result = false;

		//
		// If file does not exist, create it.
		//
		if (! f.exists()) {
			Main.logger.info("Will now initialize state file.");
			result = this.initialize(f);
			return result;
		}

		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(f));

			this.latestPort = Integer.valueOf(in.readLine());
			this.latestWWWroot = in.readLine();

			result = true;
		} catch (FileNotFoundException e) {
			Main.logger.error("The file must have been not created properly.\n" + e);
			
		} catch (NumberFormatException e) {
			Main.logger.error("The first line in the file was not a number !?" + e);
			
		} catch (IOException e) {
			Main.logger.error("Some problems with IO."+e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {}
		}

		return result;
	}

	/**
	 * Create the file from scratch. Delete it, if it exists. 
	 * Write the latest values in it.
	 * @param f
	 * @return
	 */
	boolean initialize(File f) {
		boolean ret = true;
		try {
			if (f.exists()) {
				ret = f.delete();
			}

			if (! ret ) throw new IOException(stateFile + " could not be deleted.");

			ret = f.createNewFile();			

			if (! ret ) throw new IOException(stateFile + " could not be created.");

			BufferedWriter out = new BufferedWriter ( new FileWriter(f));

			out.write(latestPort + "\n");
			out.write(latestWWWroot);

			out.close();

			Main.logger.info("New data was writen to persistance file.");
		} catch (IOException e) {
			Main.logger.error("Persistence state file initialization error: " + e);
		}
		return ret;
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


	public String getWWWroot() {
		this.update();
		return latestWWWroot;
	}
	
	
	// used for tests
	
	public static String getStatefile() {
		return stateFile;
	}
}
