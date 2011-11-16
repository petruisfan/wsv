package com.vvs.httpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class MyHttpServer {

	private static Executor executor;

	public static void main(String[] args) {
		try {
			InetSocketAddress address = new InetSocketAddress("localhost", 9000);

			HttpServer server = HttpServer.create(address, 10);

			server.setExecutor(executor);
			
			server.start();
			
			System.out.println("Server started!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
