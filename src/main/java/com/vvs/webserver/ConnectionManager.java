package com.vvs.webserver;

import java.io.IOException;
import java.net.ServerSocket;

import com.vvs.Main;
import com.vvs.webserver.WebServer;

public class ConnectionManager implements Runnable {
	private boolean maintenance = false;
	private boolean running = false;
	
	private int port = 10008;
	private String ROOT = "www/";
	
	private ServerSocket serverSocket = null;
	
	
	
	public ConnectionManager() {
	}

	public ConnectionManager(int p) {
		this();
		this.port = p;
	}
	
	void startServer() {
		try {
			serverSocket = new ServerSocket(port);

			running = true;

			Main.logger.info("Connection Socket Created");

			try {
				while (!Thread.currentThread().isInterrupted()) {
					Main.logger.info("Waiting for Connection");

					new WebServer(serverSocket.accept(), maintenance, ROOT);
					
				}
			} catch (IOException e) {
				Main.logger.fatal("Accept failed.");
				Main.logger.fatal(e);
				
				//System.exit(1);
			}
		} catch (IOException e) {
			Main.logger.fatal("Could not listen on port: " + port);
			
			System.exit(1);
			//
			// Findbugs says this is better.
			//
			//throw new RuntimeException();
		} finally {
			try {
				if (serverSocket != null)
					serverSocket.close();
			} catch (IOException e) {
				Main.logger.error("Could not close port: " + port);
				//System.exit(1);
				throw new RuntimeException();
			}
		}
	}

	public void run() {
		this.startServer();
	}

	/**
	 * Explicitly set the maintenance state.
	 * 
	 * @param b
	 */
	public void setMaintenace(boolean b) {
		this.maintenance = b;
	}

	/**
	 * Toggle maintenance state on if it is off, and vice versa.
	 */
	public void toggleMintenance() {
		maintenance = !maintenance;
		
		Main.logger.info("Server maintenance set to " + maintenance);
	}

	/**
	 * @return the server state: running, maintenance or stopped
	 */
	public String getServerState() {
		String result = "stopped";

		if (running == true) {
			if (maintenance == true) {
				result = "maintenance";
			} else
				result = "running";
		} 
		
		return result;
	}
	
	
	public void reset(){
		this.maintenance = false;
		this.running = false;

		if (this.serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				Main.logger.error("Error closing server socket while reseting ConnectionManager.");
				return ;
			}
		}
		
		Thread.currentThread().interrupt();
		
		Main.logger.info("Server reset.");
	}
	
	/**
	 * Set the port number is server is in maintenance or stopped.
	 * @param port
	 */
	public void setPort(int port) {
		if (this.maintenance  || !this.running) {
			this.port = port;
		}
		else {
			System.out.println("Server must be in maintenance mode to change port.");
		}
	}

	public boolean maintenance() {
		return maintenance;
	}

	public void setWebRoot(String webRoot) {
		if ( ! webRoot.endsWith("/")) {
			webRoot += "/";
		}
		this.ROOT = webRoot;
	}

	// Methods used for testing
	
	public int getPort() {
		return this.port;
	}
	
	public String getWwwRoot() {
		return this.ROOT;
	}
}
