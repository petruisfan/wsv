package com.vvs.webserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import com.vvs.Main;
import com.vvs.webserver.helperObjects.HttpResponse;

public class PageProcesor {
	private String webRoot = "";
	private BufferedOutputStream out = null; 
	private String maintenanceString = "<html><body><h1>Down for maintenance!</h1></body></html>";
	
	
	public PageProcesor(String root, BufferedOutputStream out) {
		if (root == null || root.equalsIgnoreCase("")){
			throw new IllegalArgumentException("Invalid argument");
		}
		this.webRoot = root;
		
		this.out = out;
	}
	
	/**
	 * Get the content from the specified string, according to the webRoot, 
	 * and print it's content out.
	 * @param page
	 * @param out
	 * @return
	 */
	public boolean processPage(String page) {
		if (page == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		
		if (page.equals("/")){
			page = "index.html";
			
			Main.logger.info("No page selected. Using index.html");
		}
		
		boolean result = false;
		String path = webRoot + page;   // page we must serve.

		if (! (new File(path)).exists()){
			return false;
		}
		
		FileInputStream fileR = null;
		Vector<Integer> dataVector = new Vector<Integer>();
		
		try {
			fileR = new FileInputStream(path);

			//
			// Read the data from the file.
			//
			int data;
			while ( (data = fileR.read()) != -1){
				dataVector.add(data);
			}

			//
			//Create an Http reponse.
			//
			// TODO create this in a single place.
			HttpResponse response = new HttpResponse(dataVector.size());
			
			if (page.endsWith(".png") || 
					page.endsWith(".jpg") ||
					page.endsWith(".jpeg") ||
					page.endsWith(".jfif") ||
					page.endsWith(".bmp") ||
					page.endsWith(".tif") ||
					page.endsWith(".tiff") ||
					page.endsWith(".ico")) {
				response.setContentType("image/x-icon");
			}
			
			out.write(response.getHeader().getBytes());
			
			for (Integer i:dataVector) {
				out.write(i);
			}
			
			result = true;  // this means all went well, the page was served.

		} catch (FileNotFoundException e) {
			Main.logger.error(e);
		} catch (IOException e) {
			Main.logger.error(e);
		} finally {
			try {
				fileR.close();
			} catch (IOException e) {
			}
		}
		
		
		return result;
	}

	/**
	 * @param out
	 * @return		The "Down for maintenance message".
	 * @throws IOException 
	 */
	public boolean maintenance() throws IOException {
		if (out == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		boolean result = false;
		
		out.write(maintenanceString.getBytes());
		
		result = true;
		return result;
	}
}
