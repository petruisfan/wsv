package com.vvs.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WebServer extends Thread {
	private static final String ROOT = "./www/";
	boolean maintenance = false;

	protected Socket clientSocket;
	private PageProcesor pp = null;
	
	
	public WebServer(Socket clientSoc, boolean maintenance) {
		if (clientSoc == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		pp = new PageProcesor(ROOT);
		
		this.maintenance = maintenance;
		
        clientSocket = clientSoc;
        start();
    }

	public void run() {
		System.out.println("New Communication Thread Started");

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				// use this only for tests
				///this.basicTest(inputLine, out);
				
				// use this for real server
				this.handleRequest(inputLine, out);

				if (inputLine.trim().equals(""))
					break;
			}

			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	/**
	 * Print the http GET message from the browser.
	 * @param inputLine
	 * @param out
	 */
	@SuppressWarnings("unused")
	private void basicTest(String inputLine, PrintWriter out) {
		System.out.println("Server: " + inputLine);
		out.println(inputLine);
	}

	/**
	 * See what the client wrote to us.
	 * @param inputLine
	 * @param out
	 */
	void handleRequest(String inputLine, PrintWriter out) {
		if (inputLine.startsWith("GET") && inputLine.endsWith("HTTP/1.1")) {
			this.processHttpGet(inputLine, out);		}
	}

	/**
	 * Handle a http get request for a page.
	 * @param inputLine
	 * @param out
	 */
	void processHttpGet(String inputLine, PrintWriter out) {
		boolean processResult = false;
		String[] strings = inputLine.split(" ");
		//out.println("You asked for page: " + s[1]);

		if (strings.length <3){
			// logging!!!
			System.out.println("http get composed of two strings..... ?!");
		} else {
			if (!maintenance){
				processResult = pp.processPage(strings[1], out);
			} else {
				processResult = pp.maintenance(out);
			}
		}
		
		if (!processResult) {
			out.println("<h1>404: not found: " + strings[1] + " </h1>");
		}
	}
}