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
	private String ROOT = "www/";
	boolean maintenance = false;

	protected Socket clientSocket;
	private PageProcesor pp = null;
	private ArrayList<String> httpGet = new ArrayList<String>(); 
	private volatile BufferedOutputStream out ;
	
	
	public WebServer(Socket clientSoc, boolean maintenance, String webRoot) {
		if (clientSoc == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		this.maintenance = maintenance;
		
        clientSocket = clientSoc;
        
        if (!webRoot.endsWith("/") ) {
        	webRoot+="/";
        }
        	
        ROOT = webRoot;
        
        start();
    }

	public void run() {
		String aux = "", inputLine;
		//
		// Firebug says StringBuffer is better to use in a loop instead of +=
		//
		StringBuffer aux2 = new StringBuffer("");
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
			aux2.append("Server received this http request:\n");
			for (String s:httpGet) {
				aux2.append(s + "\n");
			}
			Main.logger.info(aux2);
			
			
			//
			// Here we believe that the first line in the http get tells us what to return. 
			//
			if (httpGet != null && httpGet.size()>0) {
				aux = httpGet.get(0);
				
				// use this only for tests
				//this.basicTest(inputLine, out);
				
				// use this for real server
				this.handleRequest(aux);
				
				Main.logger.info("Server responds to this line: " + aux);
			}

			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			Main.logger.error("Problem with Communication Server");
			
			//System.exit(1);
			
			//
			// Findbugs says this is better.
			//
			throw new RuntimeException();
		}
	}


	/**
	 * See what the client wrote to us.
	 * @param inputLine
	 * @param out
	 * @throws IOException 
	 */
	boolean handleRequest(String inputLine) throws IOException {
		boolean result = false;
		
		if (inputLine.startsWith("GET") && inputLine.endsWith("HTTP/1.1")) {
			result = this.processHttpGet(inputLine);		
		}
		return result;
	}

	/**
	 * Handle a http get request for a page.
	 * @param inputLine
	 * @param out
	 * @throws IOException 
	 */
	boolean processHttpGet(String inputLine) throws IOException {
		boolean processResult = false;
		
		if (inputLine == null || !inputLine.contains(" "))
			return false;
		
		String[] strings = inputLine.split(" ");
		Main.logger.info("Page: " + strings[1] + " was requested.");

		if (strings.length <3){
			Main.logger.info("http get composed of two strings..... ?!");
		} 
		else {
			if (!maintenance){
				Main.logger.info(strings[1] + " will be processed.");
				
				processResult = pp.processPage(strings[1]);
				
			} else {
				Main.logger.info("Maintenance page will be replied");
					
				processResult = pp.maintenance();
			}
		}
		
		if (!processResult) {
			HttpResponse response = new HttpResponse(0);
			
			response.setResponseCode(404);
			response.setResponseStatus("Not Found");
			
			out.write(response.getHeader().getBytes());
			
			Main.logger.error("Something bad happened. Code 404 has been replied.");
		}
		
		return processResult;
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
	
	
// used for test
	void setPP(BufferedOutputStream out) {
		pp = new PageProcesor(ROOT, out);
		this.out = out;
	}
}