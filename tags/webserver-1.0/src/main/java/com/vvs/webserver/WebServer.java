package com.vvs.webserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.vvs.Main;
import com.vvs.webserver.helperObjects.HttpResponse;

public class WebServer extends Thread {
	private String ROOT = "./www/";
	boolean maintenance = false;

	protected Socket clientSocket;
	private PageProcesor pp = null;
	private ArrayList<String> httpGet = new ArrayList<String>(); 
	
	
	public WebServer(Socket clientSoc, boolean maintenance, String webRoot) {
		if (clientSoc == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		this.maintenance = maintenance;
		
        clientSocket = clientSoc;
        ROOT = webRoot;
        
        
        start();
    }

	public void run() {
		String aux = "", inputLine;
		BufferedOutputStream out ;
		BufferedReader in;
		
		
		Main.logger.info("New Communication Thread Started");

		try {
			out= new  BufferedOutputStream(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			pp = new PageProcesor(ROOT, out);
			
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
	 * @throws IOException 
	 */
	void handleRequest(String inputLine, BufferedOutputStream out) throws IOException {
		if (inputLine.startsWith("GET") && inputLine.endsWith("HTTP/1.1")) {
			this.processHttpGet(inputLine, out);		
		}
	}

	/**
	 * Handle a http get request for a page.
	 * @param inputLine
	 * @param out
	 * @throws IOException 
	 */
	void processHttpGet(String inputLine, BufferedOutputStream out) throws IOException {
		boolean processResult = false;
		String[] strings = inputLine.split(" ");
		Main.logger.info("Page: " + strings[1] + " was requested.");

		if (strings.length <3){
			Main.logger.info("http get composed of two strings..... ?!");
		} 
		else {
			if (!maintenance){
				processResult = pp.processPage(strings[1]);
				
				Main.logger.info(strings[1] + " will be processed."); 
						
			} else {
				processResult = pp.maintenance();
				
				Main.logger.info("Maintenance page will be replied");
			}
		}
		
		if (!processResult) {
			HttpResponse response = new HttpResponse(0);
			
			response.setResponseCode(404);
			response.setResponseStatus("Not Found");
			
			out.write(response.getHeader().getBytes());
			
			Main.logger.error("Something bad happened. Code 404 has been replied.");
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