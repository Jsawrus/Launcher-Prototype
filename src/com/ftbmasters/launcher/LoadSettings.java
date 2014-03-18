package com.ftbmasters.launcher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadSettings implements Runnable {
	
	@Override
	public void run() {
		try {
			ExitProcedure.checkConf();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(ExitProcedure.g));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		try {
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(":");
				
				if (parts[0].equalsIgnoreCase("username")) {		
							MainFrame.txtUsername.setText(parts[1]);
				} else if (parts[0].equalsIgnoreCase("opis")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxOpis.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("novamenu")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxNovamenu.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("bupload")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxBupload.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("zyinshud")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxZyinsHud.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("zansminimap")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxZansminimap.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("shatter")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxShatter.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("journeymap")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxJourneymap.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("soundcontrol")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxSoundcontrol.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("craftguide")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxCraftguide.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("inventorytweaks")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxInventoryTweaks.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("wailia")) {
					if (parts[1].equalsIgnoreCase("true"))	
							MainFrame.rdbtnNewRadioButton.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("advancedhud")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.rdbtnNewRadioButton_1.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("optifine")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.rdbtnOptifine.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("damageindicators")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxDamageIndicators.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("hotbarswapper")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxHotbarswapper.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("fpsplus")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxFpsplusV.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("compactdisplayhud")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxCompactDisplayHud.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("animatedplayermod")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxAnimatedPlayerMod.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("mobiuscore")) {
					if (parts[1].equalsIgnoreCase("true"))
							MainFrame.chckbxMobiuscore.setSelected(true);
				} else if (parts[0].equalsIgnoreCase("password")) {
					MainFrame.passwordField.setText(parts[1]);
				} else if (parts[0].equalsIgnoreCase("memoryalloc")) {
					MainFrame.choice_1.select(parts[1]);
				} 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	
	}

}
