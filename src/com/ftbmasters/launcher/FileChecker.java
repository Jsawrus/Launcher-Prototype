package com.ftbmasters.launcher;

import java.io.File;

public class FileChecker implements Runnable {
	
	@Override
	public void run() {
		if (!MainFrame.mainDir.exists()) {
			System.out.println("[INFO] Working Directory Missing!");
			MainFrame.mainDir.mkdirs();
			System.out.println("[INFO] New Directory Created!");
		} else {
			System.out.println("[INFO] Working Directory Found!");
		}
		
		if (!new File(MainFrame.mainDir + File.separator + "bin").exists()) {
			new File(MainFrame.mainDir + File.separator + "bin").mkdir();
		}
		
		if (!new File(MainFrame.mainDir + File.separator + "config").exists()) {
			new File(MainFrame.mainDir + File.separator + "config").mkdir();
		}
		
		if (!new File(MainFrame.mainDir + File.separator + "lib").exists()) {
			new File(MainFrame.mainDir + File.separator + "lib").mkdir();
		}
		
		if (!new File(MainFrame.mainDir + File.separator + "assets").exists()) {
			new File(MainFrame.mainDir + File.separator + "assets").mkdir();
		}
		
		if (!new File(MainFrame.mainDir + File.separator + "mods").exists()) {
			new File(MainFrame.mainDir + File.separator + "mods").mkdir();
		}
		
		if (!new File(MainFrame.mainDir + File.separator + "optional").exists()) {
			new File(MainFrame.mainDir + File.separator + "optional").mkdir();
		}
		
	}

}
