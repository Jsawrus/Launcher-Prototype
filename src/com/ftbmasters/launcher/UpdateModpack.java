package com.ftbmasters.launcher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateModpack implements Runnable {
	
	protected URL serverURL;

	@Override
	public void run() {
		try {
			serverURL = new URL("http://" + DynamicWorker.serverURL);
		} catch (MalformedURLException e) {
			System.out.println("[SEVERE] Couldn't find the Server! Oh noes!");
		}
		
		MainFrame.btnUpdate.setEnabled(false);
		MainFrame.progressBar.setStringPainted(true);
		MainFrame.progressBar.setString("Waiting for server!");
		
		//stuff
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			downloadQueuedFiles(MainFrame.mainDir.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		MainFrame.progressBar.setString("Updating completed Sucessfully!");
		try {
			new File(MainFrame.mainDir + File.separator + "assets" + File.separator + "latest.ver").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MainFrame.btnUpdate.setEnabled(true);
		MainFrame.btnLogin.setEnabled(true);
	}
	
	private static void downloadQueuedFiles(String destination) 
			throws MalformedURLException, IOException {
		for (int i = 0; i < ProcessServerpack.toDownload.size(); i++) {
			if (ProcessServerpack.toDownload.get(i).equalsIgnoreCase("http://ns3369622.ovh.net/launcher/v2/config.zip")) {
				System.out.println("Downloading update " + (i+1) + " of " + ProcessServerpack.toDownload.size() + "!");
				System.out.println("  > " + ProcessServerpack.toDownload.get(i).replace("http://ns3369622.ovh.net/launcher/v2/", ""));
				MainFrame.progressBar.setString("Downloading update " + (i+1) + " of " + ProcessServerpack.toDownload.size() + " : " + ProcessServerpack.toDownload.get(i).replace("http://ns3369622.ovh.net/launcher/v2/", ""));
				MainFrame.progressBar.setValue(MainFrame.progressBar.getValue() + 1);
				saveUrl(MainFrame.mainDir + File.separator + "config.zip", "http://ns3369622.ovh.net/launcher/v2/config.zip");
			} else {
				System.out.println("Downloading update " + (i+1) + " of " + ProcessServerpack.toDownload.size() + "!");
				System.out.println("  > " + ProcessServerpack.toDownload.get(i).replace("http://ns3369622.ovh.net/launcher/v2/", ""));
				MainFrame.progressBar.setString("Downloading update " + (i+1) + " of " + ProcessServerpack.toDownload.size() + " : " + ProcessServerpack.toDownload.get(i).replace("http://ns3369622.ovh.net/launcher/v2/", ""));
				MainFrame.progressBar.setValue(MainFrame.progressBar.getValue() + 1);
				saveUrl(MainFrame.mainDir + File.separator + ProcessServerpack.toDownload.get(i).replace("http://ns3369622.ovh.net/launcher/v2/", ""), ProcessServerpack.toDownload.get(i));
			}
		}
	}
	
	
	private static void saveUrl(String filename, String urlString)
		      throws MalformedURLException, IOException
		    {
		        BufferedInputStream in = null;
		        FileOutputStream fout = null;
		        try
		        {
		            in = new BufferedInputStream(new URL(urlString).openStream());
		            fout = new FileOutputStream(filename);

		            byte data[] = new byte[1024];
		            int count;
		            while ((count = in.read(data, 0, 1024)) != -1)
		            {
		                fout.write(data, 0, count);
		            }
		        }
		        finally
		        {
		            if (in != null)
		                in.close();
		            if (fout != null)
		                fout.close();
		        }
		    }

}
