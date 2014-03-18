package com.ftbmasters.launcher;

import java.awt.Color;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class DynamicWorker implements Runnable {

	public static String serverURL = "ns3369622.ovh.net";
	public boolean reachable;

	@Override
	public void run() {
			checkServer();		
	}

	private void checkServer() {
		Socket socket = null;
		this.reachable = false;
		try {
			try {
				socket = new Socket(serverURL, 54322);
				this.reachable = true;
			} catch (UnknownHostException e) {
				this.reachable = false;
				return;
			} catch (IOException e) {
				this.reachable = false;
				return;
			}
			
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (ConnectException e) {
		
				} catch (IOException e) {
					
				} 
		}
		
		MainFrame.lblReachable.setForeground(Color.GREEN);
		MainFrame.lblReachable.setText("The server is currently online!");
	}

}
