package com.vvs.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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
	public boolean processPage(String page, PrintWriter out) {
		if (page == null || out == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		
		if (page.equals("/")){
			page = "index.html";
		}
		
		boolean result = false;
		String path = webRoot + page;
		
		File f = new File(path);
		
		if (! f.exists()){
			return false;
		}
		
		BufferedReader fileR = null;
		try {
			fileR= new BufferedReader(new FileReader(f));
			
			String line = "";
			while ( (line = fileR.readLine()) != null){
				out.write(line);
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
