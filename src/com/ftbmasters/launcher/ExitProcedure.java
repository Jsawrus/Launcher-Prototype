package com.ftbmasters.launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExitProcedure implements Runnable {
	
	static File f = new File(System.getenv("APPDATA") + File.separator + ".MagiTech");
	static File g = new File(f.getAbsolutePath() + File.separator + "settings.conf");

	@Override
	public void run() {

		writeToFile();
		
		System.exit(0);
	}
	
	@SuppressWarnings("deprecation")
	private void writeToFile() {
		try {
		
			g.delete();
			g.createNewFile();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(g.getPath()), true));
			
			bw.write("username:" + MainFrame.txtUsername.getText());
			bw.newLine();
			bw.write("password:" + MainFrame.passwordField.getText());
			bw.newLine();
			bw.write("opis:" + MainFrame.chckbxOpis.isSelected());
			bw.newLine();
			bw.write("novamenu:" + MainFrame.chckbxNovamenu.isSelected());
			bw.newLine();
			bw.write("bupload:" + MainFrame.chckbxBupload.isSelected());
			bw.newLine();
			bw.write("zyinshud:" + MainFrame.chckbxZyinsHud.isSelected());
			bw.newLine();
			bw.write("zansminimap:" + MainFrame.chckbxZansminimap.isSelected());
			bw.newLine();
			bw.write("shatter:" + MainFrame.chckbxShatter.isSelected());
			bw.newLine();
			bw.write("journeymap:" + MainFrame.chckbxJourneymap.isSelected());
			bw.newLine();
			bw.write("soundcontrol:" + MainFrame.chckbxSoundcontrol.isSelected());
			bw.newLine();
			bw.write("craftguide:" + MainFrame.chckbxCraftguide.isSelected());
			bw.newLine();
			bw.write("inventorytweaks:" + MainFrame.chckbxInventoryTweaks.isSelected());
			bw.newLine();
			bw.write("wailia:" + MainFrame.rdbtnNewRadioButton.isSelected());
			bw.newLine();
			bw.write("advancedhud:" + MainFrame.rdbtnNewRadioButton_1.isSelected());
			bw.newLine();
			bw.write("optifine:" + MainFrame.rdbtnOptifine.isSelected());
			bw.newLine();
			bw.write("damageindicators:" + MainFrame.chckbxDamageIndicators.isSelected());
			bw.newLine();
			bw.write("hotbarswapper:" + MainFrame.chckbxHotbarswapper.isSelected());
			bw.newLine();
			bw.write("fpsplus:" + MainFrame.chckbxFpsplusV.isSelected());
			bw.newLine();
			bw.write("compactdisplayhud:" + MainFrame.chckbxCompactDisplayHud.isSelected());
			bw.newLine();
			bw.write("animatedplayermod:" + MainFrame.chckbxAnimatedPlayerMod.isSelected());
			bw.newLine();
			bw.write("mobiuscore:" + MainFrame.chckbxMobiuscore.isSelected());
			bw.newLine();
			bw.write("memoryalloc:" + MainFrame.choice_1.getSelectedItem());
			bw.newLine();

			
			bw.close();
		} catch (Exception e) {
			
		}
	}
	
	public static void checkConf() throws IOException {
		if (!f.exists()) {
			f.mkdir();
		}
		
		if (!g.exists()) {
			g.createNewFile();
		}
		
	}

}
