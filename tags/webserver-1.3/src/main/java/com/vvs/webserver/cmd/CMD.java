package com.vvs.webserver.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.vvs.Main;
import com.vvs.webserver.ConnectionManager;
import com.vvs.webserver.helperObjects.PersistanceState;

public class CMD{
	private static final String welcomeMessage = "This is the webserver in terminal mode. Press \"h\" for help\n";
	
	private ConnectionManager server = null;
	private Thread thread = null;
	private PersistanceState state = new PersistanceState();
	
	
	public CMD() {
		server = new ConnectionManager( state.getPort() );
		server.setWebRoot ( state.getWWWroot() );
		thread = new Thread( server );
	}
	
	public void run() throws IOException, InterruptedException {
		String cmd = "";
		char command;
		
		System.out.println(welcomeMessage);
		
		for (;;) {
			cmd = this.readFromKeyboard(" > ");
			
			// If the reading went wrong, use i for invalid. 
			if (cmd == null || cmd.length() == 0) {
				cmd = "i";
			}
			
			command = cmd.charAt(0);
		
			switch (command) {
			case 'h':
				System.out.println( helpMessage() );
				
				break;
			case 's':
				Main.logger.info("User hit \"s\" to start the server.");
				this.startServer();
				
				break;
			case 'm': 
				Main.logger.info("User hit \"m\" to togle maintenance state on server.");
				this.toggleMaintenance();

				break;
			case 'k':
				Main.logger.info("User hit \"k\" to kill the server.");
				this.killServer();

				break;
			case 'c':
				Main.logger.info("User hit \"c\" to change port.");
				this.changePort();

				break;
			case 'w':
				Main.logger.info("User hit \"w\" to change server web root.");
				this.changeWebRoot();
				
				break;
			case 'p':
				System.out.println(server.getServerState());
				Main.logger.info("User hit \"p\" to show server state.");

				break;
			case 'q':
				Main.logger.info("User hit \"q\" to exit the application.");
				
				try {
					server.reset();
					thread.join(1000);
				} catch (Exception e) {
					Main.logger.error(e);
				}
				
				return;
			
			default:
				System.out.println("Invalid command. Press h for help.");
				break;
			}
		}
	}
	
	/**
	 * Reads a new web root from keyboard. Updates server & persistence.
	 */
	void changeWebRoot() {
		if (server.maintenance()) {
			String webRoot = null;
			try {
				webRoot = this.readFromKeyboard("Enter the new web root: ");
			} catch (IOException e) {
				Main.logger.error("Error when trying to read web root from keyboard.");
			}

			if (webRoot != null){
				server.setWebRoot(webRoot);

				state.setWWWroot(webRoot);
			}
		} else {
			System.out.println("Can only change web root if in maintenance");
		}
	}

	/**
	 * Reads a new port from keyboard. Updates server & persistence.
	 */
	void changePort() {
		if (server.maintenance()) {
			int port = -1;

			while (port == -1) {
				try {
					port = Integer.valueOf(this.readFromKeyboard("Change port number to: "));
				} catch (NumberFormatException e) {
					Main.logger.error("Error when changing port: " + e);
				} catch (IOException e) {
					Main.logger.error("Error when changing port: " + e);
				}
			}

			if (port >0 && port < 65535) {
				this.killServer();
				
				state.setPort(port);

				server.setPort(port);

				this.startServer();
				
				server.setMaintenace(true);
			} else {
				System.out.println("Port number not in acceptable range.");
			}
		} else {
			System.out.println("Server must be in maintenance mode to change port.");
		}

	}

	/**
	 * Reads a string from keyboard. Prints a message first.
	 * @param message
	 * @return
	 * @throws IOException
	 */
	String readFromKeyboard(String message) throws IOException {
		BufferedReader input = new BufferedReader ( new InputStreamReader ( System.in));
		String result = null;		

		System.out.print(message);
		result = input.readLine();
		
		return result;
	}

	void killServer() {
		server.reset();
	}

	/**
	 * Toggle maintenance state on/off on server.
	 */
	void toggleMaintenance() {
		server.toggleMintenance();
	}

	/**
	 * Start the server
	 */
	void startServer() {
		if (! thread.isAlive() ) {
			thread = new Thread(server);
			thread.start();
		}	
	}
	
	String helpMessage() {
		String result = "";
		
		result += " s - start the webserver.\n";
		result += " m - toggle maintenance mode on/off.\n";
		result += " k - stop (kill) the webserver.\n";
		result += " c - change port.\n";
		result += " w - change web root.\n";
		result += " p - print current state.\n";
		result += " h - print this help menu.\n";
		result += " q - quit the application.\n";
				
		return result;
	}
	
	// methods introduced for testing
	
	ConnectionManager getServer () {
		return this.server;
	}
}
