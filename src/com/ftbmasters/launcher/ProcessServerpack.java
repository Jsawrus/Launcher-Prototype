package com.ftbmasters.launcher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProcessServerpack implements Runnable {
	
	private static URL packURL;
	private static HashMap<String, String> files = new HashMap<String, String>();
	private static HashMap<String, String> optionalfiles = new HashMap<String, String>();
	private static HashMap<String, String> urls = new HashMap<String, String>();
	protected static ArrayList<String> toDownload = new ArrayList<String>();
	private static File localServerpackFolder;

	@Override
	public void run() {
		MainFrame.btnUpdate.setEnabled(false);
		setPackURL();
		setLocalPackFile();
		MainFrame.btnUpdate.setEnabled(false);
		
		try {
			downloadServerpack(getPackURL(), getLocalPackFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			parseServerpack(getLocalPackFile());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		new Thread(new UpdateModpack()).start();
		
	}

	private static URL getPackURL() {
		return packURL;
	}

	private static void setPackURL() {
		try {
			ProcessServerpack.packURL = new URL("http://ns3369622.ovh.net/launcher/v2/ServerPack.xml");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	} 
	
	private static void setLocalPackFile() {
		ProcessServerpack.localServerpackFolder = new File(MainFrame.mainDir + File.separator + "assets" + File.separator + "ServerPack.xml");

	}
	
	private static File getLocalPackFile() {
		return localServerpackFolder;
	}
	
	private static void downloadServerpack(URL url, File file) throws IOException {
		MainFrame.progressBar.setString("Downloading the Server Information Payload!");
		MainFrame.progressBar.setStringPainted(true);
		
		BufferedInputStream in = null;
        FileOutputStream fout = null;
        try
        {
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(file);

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

        MainFrame.progressBar.setString("Checking file consistency!");
		System.out.println("[INFO] Received the Server Information Payload");
	}
	
	private static void parseServerpack(File f) throws SAXException, IOException, ParserConfigurationException {
		System.out.println("[INFO] Parsing the Server Information Payload");
		MainFrame.progressBar.setString("Checking for updates!");
		f = new File(MainFrame.mainDir + File.separator + "assets" + File.separator + "ServerPack.xml");
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(f);
		
		doc.getDocumentElement().normalize();

		System.out.println("[INFO] ServerPack Version: " + doc.getDocumentElement().getAttribute("version"));
		
		NodeList serverInfo = doc.getElementsByTagName("Server");
		
		int requiredFiles = 0, optionalFiles = 0;
		for (int i = 0; i < serverInfo.getLength(); i++) {
			Node serverNode = serverInfo.item(i);
			
			//System.out.println("     > ------------------------------------------------------------");
		
			if (serverNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element element = (Element) serverNode;
				
				//System.out.println("       Cluster Name: " + element.getAttribute("id"));
				//System.out.println("       Modpack Name: " + element.getAttribute("name"));
				//System.out.println("       Modpack Version: " + element.getAttribute("revision"));
				//System.out.println("       Minecraft Version: " + element.getAttribute("version"));
				//System.out.println("       Server URL: " + element.getAttribute("serverAddress"));
				//System.out.println("       Minecraft Main Class: " + element.getAttribute("mainClass"));
			}
			
			
		}
		
		//System.out.println("     > ------------------------------------------------------------");
		
		NodeList modules = doc.getElementsByTagName("Module");
		
		for (int x = 0; x < modules.getLength(); x++) {
			Node modulesNode = modules.item(x);


			if (modulesNode.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element element = (Element) modulesNode;
				
				if (element.getElementsByTagName("Required").item(0).getTextContent().equalsIgnoreCase("false")) {
					optionalFiles++;
				} else {
					requiredFiles++;
				}
				
				
					//System.out.println("       Mod Name: " + element.getAttribute("name"));
					//System.out.println("       Mod URL: " + element.getElementsByTagName("URL").item(0).getTextContent());
					//System.out.println("       Mod Required: " + element.getElementsByTagName("Required").item(0).getTextContent());
					//System.out.println("       Mod Type: " + element.getElementsByTagName("ModType").item(0).getTextContent());
					//System.out.println("       Mod MD5: " + element.getElementsByTagName("MD5").item(0).getTextContent());
					//System.out.println("     > ------------------------------------------------------------");
					
					if (element.getElementsByTagName("Required").item(0).getTextContent().equalsIgnoreCase("true")) {
						files.put(element.getAttribute("name"), element.getElementsByTagName("MD5").item(0).getTextContent());
						urls.put(element.getAttribute("name"), element.getElementsByTagName("URL").item(0).getTextContent());
						//System.out.println("[INFO] Adding " + element.getAttribute("name") + " to required mods set!");
						doUpdateFile(element.getElementsByTagName("URL").item(0).getTextContent(), element.getAttribute("name"));
					} else {
						optionalfiles.put(element.getAttribute("name"), element.getElementsByTagName("MD5").item(0).getTextContent());
						urls.put(element.getAttribute("name"), element.getElementsByTagName("URL").item(0).getTextContent());
						//System.out.println("[INFO] Adding " + element.getAttribute("name") + " to optional mods set!");
						//System.out.println(element.getElementsByTagName("URL").item(0).getTextContent());
						doUpdateFile(element.getElementsByTagName("URL").item(0).getTextContent(), element.getAttribute("name"));
					}
					
			}
			
			
		}
		
		//System.out.println("[INFO] " + requiredFiles + " required files and " + optionalFiles + " optional files have been queued for checking!");
	}
	
	private static void doUpdateFile(String url, String modname) {
		String formattedURL = url.replace("http://ns3369622.ovh.net/launcher/v2/", "");
		
		if (new File(MainFrame.mainDir + File.separator + formattedURL).exists()) {
			if (files.containsKey(modname)) {
			if (!returnChecksum(new File(MainFrame.mainDir + File.separator + formattedURL)).equalsIgnoreCase(files.get(modname))) {
				//download.
				System.out.println("[INFO] An update has been found for: " + modname);
				toDownload.add(url);
			} else {
				
			}
			} else if (optionalfiles.containsKey(modname)) {
				if (!returnChecksum(new File(MainFrame.mainDir + File.separator + formattedURL)).equalsIgnoreCase(optionalfiles.get(modname))) {
					//download.
					System.out.println("[INFO] An update has been found for: " + modname);
					toDownload.add(url);
				} else {
					
				}
			} else {
				
				
			}
		} else {
			//Download
			System.out.println("[INFO] An update has been found for: " + modname);
			toDownload.add(url);
		}
		
		MainFrame.progressBar.setMaximum(toDownload.size());
		
		if (toDownload.isEmpty()) {
			MainFrame.progressBar.setMaximum(100);
			MainFrame.progressBar.setValue(100);
			MainFrame.progressBar.setString("No updates have been found!");
			MainFrame.btnUpdate.setEnabled(false);
		}
	}
	
	protected static String returnChecksum(File file){
        String checksum = null;
        try {  
            checksum = DigestUtils.md5Hex(new FileInputStream(file.getCanonicalPath()));
        } catch (IOException ex) {
        	
        }
        return checksum;
    }

}
