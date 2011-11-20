package com.vvs.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.vvs.Main;

public class WebServer extends Thread {
	private static final String ROOT = "./www/";
	boolean maintenance = false;

	protected Socket clientSocket;
	private PageProcesor pp = null;
	private ArrayList<String> httpGet = new ArrayList<String>(); 
	
	
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
		String aux = "", inputLine;
		PrintWriter out ;
		BufferedReader in;
		
		
		Main.logger.info("New Communication Thread Started");

		try {
			out= new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			while ((inputLine = in.readLine()) != null) {
				
				httpGet.add(inputLine);
				
				if (inputLine.trim().equals(""))
					break;
			}
			
			//
			// for safety, let's log the http get.
			//
			aux = "Server received this http request:\n";
			for (String s:httpGet) {
				aux += s + "\n";
			}
			Main.logger.info(aux);
			
			
			//
			// Here we believe that the first line in the http get tells us what to return. 
			//
			if (httpGet != null && httpGet.size()>0) {
				aux = httpGet.get(0);
				
				// use this only for tests
				//this.basicTest(inputLine, out);
				
				// use this for real server
				this.handleRequest(aux, out);
				
				Main.logger.info("Server responds to this line: " + aux);
			}

			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			Main.logger.error("Problem with Communication Server");
			System.exit(1);
		}
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
		Main.logger.info("Page: " + strings[1] + " was requested.");

		if (strings.length <3){
			Main.logger.info("http get composed of two strings..... ?!");
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

	
	/**
	 * Print the http GET message from the browser.
	 * @param inputLine
	 * @param out
	 */
	@SuppressWarnings("unused")
	private void basicTest(String inputLine, PrintWriter out) {
		//System.out.println("Server: " + inputLine);
		out.println(inputLine);
	}
}