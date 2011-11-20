package com.vvs.webserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import com.vvs.webserver.helperObjects.HttpResponse;

public class PageProcesor {
	private String webRoot = "";

	
	public PageProcesor(String root) {
		if (root == null || root.equalsIgnoreCase("")){
			throw new IllegalArgumentException("Invalid argument");
		}
		this.webRoot = root;
	}
	
	/**
	 * Get the content from the specified string, according to the webRoot, 
	 * and print it's content out.
	 * @param page
	 * @param out
	 * @return
	 */
//	public boolean processPage(String page, PrintWriter out) {
//		if (page == null || out == null) {
//			throw new IllegalArgumentException("Invalid argument");
//		}
//		
//		if (page.equals("/")){
//			page = "index.html";
//		}
//		
//		boolean result = false;
//		String path = webRoot + page;
//		
//		File f = new File(path);
//		
//		if (! f.exists()){
//			return false;
//		}
//		
//		BufferedReader fileR = null;
//		try {
//			fileR= new BufferedReader(new FileReader(f));
//			
//			String line = "";
//			while ( (line = fileR.readLine()) != null){
//				out.write(line);
//			}
//			
//			result = true;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	public boolean processPage(String page, PrintWriter out) {
		if (page == null || out == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		
		if (page.equals("/")){
			page = "index.html";
		}
		
		boolean result = false;
		String path = webRoot + page;

		if (! (new File(path)).exists()){
			return false;
		}
		
		FileInputStream f = null;
		DataInputStream fileR = null;
	
		//
		// Read the data from the file.
		//
		Vector<Integer> dataVector = new Vector<Integer>();
		
		try {
			f = new FileInputStream(path);
			fileR= new DataInputStream(f);
			
			int data;
			while ( (data = fileR.read()) != -1){
				dataVector.add(data);
			}

			//
			//Create an Http reponse.
			//
			HttpResponse response = new HttpResponse(dataVector.size());
			
			if (page.endsWith(".png")) {
				response.setContentType("image/x-icon");
			}
			
			out.write(response.getHeader());
			
			for (Integer i:dataVector) {
				out.write(i);
			}
			
			
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * @param out
	 * @return		The "Down for maintenance message".
	 */
	public boolean maintenance(PrintWriter out) {
		if (out == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		boolean result = false;
		
		out.write("<h1>Down for maintenance!</h1>");
		
		result = true;
		return result;
	}
}
