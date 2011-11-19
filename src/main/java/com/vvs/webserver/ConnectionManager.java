package com.vvs.webserver;

import java.io.IOException;
import java.net.ServerSocket;

import com.vvs.Main;
import com.vvs.webserver.WebServer;

public class ConnectionManager implements Runnable {
	private boolean maintenance = false;
	private boolean running = false;
	private int port = 10008;
	private ServerSocket serverSocket = null;
	
	
	public ConnectionManager() {
	}

	public ConnectionManager(int p) {
		this();
		this.port = p;
	}
	
	private void startServer() {
		try {
			serverSocket = new ServerSocket(port);

			running = true;

			Main.logger.info("Connection Socket Created");

			try {
				while (!Thread.currentThread().isInterrupted()) {
					Main.logger.info("Waiting for Connection");

					new WebServer(serverSocket.accept(), maintenance);
				}
			} catch (IOException e) {
				Main.logger.fatal("Accept failed.");
				
				System.exit(1);
			}
		} catch (IOException e) {
			Main.logger.fatal("Could not listen on port: " + port);
			
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				Main.logger.error("Could not close port: " + port);
				System.exit(1);
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
	
	public void reset() throws IOException {
		Thread.currentThread().interrupt();
		
		this.maintenance = false;
		this.running = false;

		if (this.serverSocket != null) {
			this.serverSocket.close();
		}
		
		Main.logger.info("Server reset.");
	}
}