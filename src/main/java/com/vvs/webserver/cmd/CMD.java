package com.vvs.webserver.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.vvs.Main;
import com.vvs.webserver.ConnectionManager;

public class CMD{
	private ConnectionManager server = null;
	private Thread thread = null;
	
	
	public CMD() {
		this.reset();
	}
	
	private void reset() {
		server = new ConnectionManager();
		thread = new Thread(server);
	}

	public void run() throws IOException, InterruptedException {
		BufferedReader input = new BufferedReader ( new InputStreamReader ( System.in));
		String cmd = "";
		char command;
		
		System.out.println("This is the webserver in terminal mode. Press \"h\" for help\n");
		
		for (;;) {
			System.out.print(" > ");
			cmd = input.readLine();
			
			// If the reading went wrong, use i for invalid. 
			if (cmd.length() == 0 || cmd == null) {
				cmd = "i";
			}
			
			command = cmd.charAt(0);
		
			
			switch (command) {
			case 'h':
				System.out.println( helpMessage() );
				break;

			case 's':
				if (! thread.isAlive() ) {
					thread = new Thread(server);
					thread.start();
				}
				Main.logger.info("User hit \"s\" to start the server.");
				
				break;
				
			case 'm': 
				server.toggleMintenance();
				
				Main.logger.info("User hit \"m\" to togle maintenance state on server.");
				break;
			
			case 'k':
				server.reset();
				
				Main.logger.info("User hit \"k\" to kill the server.");
				break;
			
			case 'p':
				System.out.println(server.getServerState());
				
				Main.logger.info("User hit \"p\" to show server state.");
				break;
			
			case 'q':
				try {
					server.reset();
					thread.join(1000);
				} catch (Exception e) {
					Main.logger.error(e);
				}
				Main.logger.info("User hit \"q\" to exit the application.");
				return;
			
			default:
				System.out.println("Invalid command. Press h for help.");
				break;
			}
		}
	}
	
	private String helpMessage() {
		String result = "";
		
		result += " s - start the webserver.\n";
		result += " m - toggle maintenance mode on/off.\n";
		result += " k - stop (kill) the webserver.\n";
		//result += " c - change port.\n";
		//result += " w - change web root.\n";
		result += " p - print current state.\n";
		result += " h - print this help menu.\n";
		result += " q - quit the application.\n";
				
		return result;
	}
}
