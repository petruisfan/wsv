package com.vvs.webserver;

import java.io.IOException;
import java.net.ServerSocket;

import com.vvs.webserver.WebServer;

public class ConnectionManager implements Runnable{
	private boolean maintenance = false;;
	private int port = 10008;
	
	
	public ConnectionManager() {
	}

	public ConnectionManager(int p) {
		this();
		this.port = p;
	}
	
	private void startServer() {
		ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Connection Socket Created");
            try {
                while (true) {
                    System.out.println("Waiting for Connection");
                    new WebServer(serverSocket.accept(), maintenance);
                }
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: " + port);
                System.exit(1);
            }
        }
	}
	
	public void run() {
		this.startServer();
	}
	
	public void setMaintenace(boolean b) {
		this.maintenance = b;
	}
}
