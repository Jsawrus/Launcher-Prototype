package com.ftbmasters.launcher;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;


public class Authenticator implements Runnable {
	
	public static File MCDir = new File(MainFrame.mainDir + File.separator + "modpack");
    private String username, password;
        
    private JSONObject obj;
   
    private URL url;
    private HttpsURLConnection con;
    private OutputStreamWriter out;
   
    private BufferedReader in;
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Authenticate(MainFrame.txtUsername.getText(), MainFrame.passwordField.getText());
	}
   
    public void Authenticate(String username, String password){
            this.username = username;
            this.password = password;
           
            writePayload();
            streamPayload();
            readRespone();
    }
   
    private void writePayload(){
            obj = new JSONObject();
           
            obj.put("username", username);
            obj.put("password", password);
            obj.put("clientToken", UUID.randomUUID().toString());
            obj.put("name", "Minecraft");
            obj.put("version", "1");
            
    }
   
    private void streamPayload(){
            try {
                    url = new URL("https://authserver.mojang.com/authenticate");
                    con = (HttpsURLConnection) url.openConnection();
                   
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setDoOutput(true);
                   
                   //System.out.println("JSON Payload SENT!");
                    System.out.println("  > "+ obj.toString());
                   
                    out = new OutputStreamWriter(con.getOutputStream());
                    out.write(obj.toString());
                   
                    out.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }
   
    private void readRespone(){
            try {
            	if (con.getResponseCode() == 400) {
            		MainFrame.lblErrorIncorrect.setForeground(new Color(204,0,0));
            		System.out.println("Error 400: Not enough data was supplied in the JSON Payload.");
            		MainFrame.lblErrorIncorrect.setText("An Unknown Error has occured");
            		MainFrame.lblErrorIncorrect.setVisible(true);
            		MainFrame.btnLogin.setEnabled(true);
            		return;
            		//error.
            	} 
            	
            	if (con.getResponseCode() == 403) {
            		System.out.println("Error 403: Invalid username or password.");
            		MainFrame.lblErrorIncorrect.setForeground(new Color(204,0,0));
            		MainFrame.lblErrorIncorrect.setText("Invalid Username/Password Combination");
            		MainFrame.lblErrorIncorrect.setVisible(true);
            		MainFrame.passwordField.setText(null);
            		MainFrame.btnLogin.setEnabled(true);
            		return;
            		//error.
            	}
            	
            	if (con.getResponseCode() == 200) {
            		//System.out.println("JSON Payload SUCCESS!");
            		MainFrame.lblErrorIncorrect.setForeground(new Color(0,102,0));
            		MainFrame.lblErrorIncorrect.setText("                    Logged in sucessfully");	
            		MainFrame.lblErrorIncorrect.setVisible(true);
            		
            	} 
            	
            	
            	
                    //System//.out.println(con.getResponseCode());
                   
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                   
                    String response;
                    while((response = in.readLine()) != null){
                            System.out.println("  > " + response);  
                    }
                    
                    String[] responseMessage = con.getResponseMessage().split(",");
            		String accessToken = responseMessage[0];
            		String[] nakedAccessToken = accessToken.split(":");
            		String finalAccessToken = nakedAccessToken[0];
            		
            		String AT = finalAccessToken.substring(1, finalAccessToken.length() - 1);
            		
            		try {
						System.out.println("[INFO] Access token received!");
						System.out.println("  > " + AT);
						System.out.println("[INFO] Launching the modpack!");
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            		
            	
            		
            		//EXECUTE COMMAND TO LAUNCH MINECRAFT
            		// SOMETHING LIKE THIS: java -Xms512m -Xmx1g -Djava.library.path=natives/ -cp "minecraft.jar;lwjgl.jar;lwjgl_util.jar" net.minecraft.client.Minecraft <username> <sessionID>
            		// Send AT to mc client.
            		
                    in.close();
                    con.disconnect();
           
            } catch (IOException e) {
                    e.printStackTrace();
            }
            
            
    }

}
