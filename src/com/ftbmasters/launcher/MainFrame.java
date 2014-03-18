package com.ftbmasters.launcher;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class MainFrame {

	public JFrame frame;
	public static String ver = "1.8";
	public static JProgressBar progressBar;
	public static JSeparator separator;
	public static JButton btnLogin;
	public static File mainDir = new File(System.getenv("APPDATA") + File.separator + ".MagiTech" + File.separator + "modpack");
	public static JLabel lblReachable;
	public static WindowAdapter listener;
	public static JTextField txtUsername;
	public static JPasswordField passwordField;
	public static JLabel lblErrorIncorrect;
	private JLabel lblUsername;
	private JLabel lblPassword;
	public static JTextPane textPane;
	public static JCheckBox chckbxOpis, chckbxNovamenu, chckbxBupload, chckbxZyinsHud, chckbxZansminimap, chckbxShatter, chckbxJourneymap, chckbxSoundcontrol, chckbxCraftguide,
	chckbxInventoryTweaks, chckbxDamageIndicators, chckbxMobiuscore, chckbxAnimatedPlayerMod, chckbxCompactDisplayHud, chckbxFpsplusV, chckbxHotbarswapper, rdbtnOptifine,
	rdbtnNewRadioButton_1, rdbtnNewRadioButton;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	public static JButton btnUpdate;
	public static Choice choice_1;
	private JLabel lblModpack;
	private JLabel lblMemory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
					
					checkFiles();
					loadSettings();
					dynamics();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		
		frame.setTitle("MagiTech Madness Launcher");
		frame.setSize(850, 540);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.getContentPane().setBackground(new Color(72,72,72));
		
		try {
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL("http://" + DynamicWorker.serverURL + "/client_assets/favicon.png")));
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}
		
		frame.getContentPane().setLayout(null);
		
		btnLogin = new JButton("Launch");
		btnLogin.setBackground(new Color(240,240,240));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnLogin.isEnabled() == true) {
				authenticateUser();
				}
			}
		});
		btnLogin.setBounds(714, 468, 120, 32);
		
		if (new File(mainDir + File.separator + "assets" + File.separator + "latest.ver").exists()) {
		btnLogin.setEnabled(true);
		btnLogin.setToolTipText("Launch the game!");
		} else {
			btnLogin.setEnabled(false);
			btnLogin.setToolTipText("Updating is required to play the game!");
		}
		frame.getContentPane().add(btnLogin);
		
		separator = new JSeparator();
		separator.setBounds(10, 455, 824, 2);
		frame.getContentPane().add(separator);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setValue(0);
		progressBar.setBounds(10, 468, 564, 32);
		progressBar.setToolTipText("There are no files currently downloading!");
		progressBar.setStringPainted(true);
		progressBar.setString("Hit the update button to check for an update!");
		frame.getContentPane().add(progressBar);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(449, 318, 385, 126);
		panel_2.setBackground(new Color(61,89,171));
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setText("Username");
		txtUsername.setBounds(125, 33, 187, 24);
		panel_2.add(txtUsername);
		txtUsername.setToolTipText("Enter your minecraft username!");
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtUsername.setColumns(1);
		txtUsername.setBackground(new Color(240, 240, 240));
		
		passwordField = new JPasswordField();
		passwordField.setText("password");
		passwordField.setBounds(125, 63, 187, 24);
		panel_2.add(passwordField);
		passwordField.setToolTipText("Enter your minecraft password!");
		passwordField.setColumns(1);
		passwordField.setBackground(new Color(240, 240, 240));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(53, 33, 81, 24);
		panel_2.add(lblUsername);
		lblUsername.setToolTipText("Enter your minecraft username!");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(56, 63, 81, 24);
		panel_2.add(lblPassword);
		lblPassword.setToolTipText("Enter your minecraft password!");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblErrorIncorrect = new JLabel("Invalid Username/Password Combination");
		lblErrorIncorrect.setBounds(66, 101, 241, 14);
		panel_2.add(lblErrorIncorrect);
		lblErrorIncorrect.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblErrorIncorrect.setToolTipText("Make sure you typed your password correctly!");
		lblErrorIncorrect.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		lblErrorIncorrect.setVisible(false);
		lblPassword.setVisible(true);
		lblUsername.setVisible(true);
		passwordField.setVisible(true);
		txtUsername.setVisible(true);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "News and Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 16, 421, 281);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(61,89,171));
		
		textPane = new JTextPane();
		textPane.setBounds(10, 22, 405, 248);
		panel_1.add(textPane);
		textPane.setToolTipText("Latest news and information!");
		textPane.setContentType("text/html");
		textPane.setBackground(null);
		textPane.setEnabled(true);
		textPane.setEditable(false);
		
		try {
			textPane.setPage("http://" + DynamicWorker.serverURL + "/client_assets/news.html");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (btnUpdate.isEnabled() == true) {
				UpdateModpack();
				}
			}
		});
		btnUpdate.setEnabled(true);
		btnUpdate.setToolTipText("Update the game!");
		btnUpdate.setBackground(SystemColor.menu);
		btnUpdate.setBounds(584, 468, 120, 32);
		frame.getContentPane().add(btnUpdate);
		
		
		setupRadioButtons();
		
		frame.repaint();
	}
	
	private void setupRadioButtons() {
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Optional Client Mods", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(449, 16, 385, 281);
		panel.setBackground(new Color(61,89,171));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		rdbtnNewRadioButton = new JCheckBox("Waila");
		rdbtnNewRadioButton.setBounds(200, 16, 109, 24);
		panel.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Waila: " + rdbtnNewRadioButton.isSelected());
			}
		});
		rdbtnNewRadioButton.setBackground(null);
		
		rdbtnNewRadioButton_1 = new JCheckBox("Advanced HUD");
		rdbtnNewRadioButton_1.setBounds(200, 43, 109, 23);
		panel.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Advanced HUD: " + rdbtnNewRadioButton_1.isSelected());
			}
		});
		rdbtnNewRadioButton_1.setBackground(null);
		
		rdbtnOptifine = new JCheckBox("Optifine");
		rdbtnOptifine.setBounds(200, 69, 109, 23);
		panel.add(rdbtnOptifine);
		rdbtnOptifine.setToolTipText("Includes many customisation and performance improvements!");
		rdbtnOptifine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Optifine: " + rdbtnOptifine.isSelected());
			}
		});
		rdbtnOptifine.setBackground(null);
		
		chckbxHotbarswapper = new JCheckBox("HotbarSwapper");
		chckbxHotbarswapper.setBounds(200, 121, 133, 23);
		panel.add(chckbxHotbarswapper);
		chckbxHotbarswapper.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("HotbarSwapper: " + chckbxHotbarswapper.isSelected());
			}
		});
		chckbxHotbarswapper.setBackground((Color) null);
		
		chckbxFpsplusV = new JCheckBox("FpsPlus v2");
		chckbxFpsplusV.setBounds(200, 147, 133, 23);
		panel.add(chckbxFpsplusV);
		chckbxFpsplusV.setToolTipText("Provides an altogether more playable experience!");
		chckbxFpsplusV.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("FpsPlus v2: " + chckbxFpsplusV.isSelected());
			}
		});
		chckbxFpsplusV.setBackground((Color) null);
		
		chckbxCompactDisplayHud = new JCheckBox("Compact Display HUD");
		chckbxCompactDisplayHud.setBounds(200, 173, 150, 23);
		panel.add(chckbxCompactDisplayHud);
		chckbxCompactDisplayHud.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Compact Display HUD: " + chckbxCompactDisplayHud.isSelected());
			}
		});
		chckbxCompactDisplayHud.setBackground((Color) null);
		
		chckbxAnimatedPlayerMod = new JCheckBox("Animated Player Mod");
		chckbxAnimatedPlayerMod.setBounds(200, 199, 150, 23);
		panel.add(chckbxAnimatedPlayerMod);
		chckbxAnimatedPlayerMod.setToolTipText("Prance around with your friends!");
		chckbxAnimatedPlayerMod.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Animated Player Mod: " + chckbxAnimatedPlayerMod.isSelected());
			}
		});
		chckbxAnimatedPlayerMod.setBackground((Color) null);
		
		chckbxMobiuscore = new JCheckBox("MobiusCore");
		chckbxMobiuscore.setBounds(200, 225, 150, 23);
		panel.add(chckbxMobiuscore);
		chckbxMobiuscore.setToolTipText("Offers many optimizations to minecraft!");
		chckbxMobiuscore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Mobius Core: " + chckbxMobiuscore.isSelected());
			}
		});
		chckbxMobiuscore.setBackground((Color) null);
		
		chckbxDamageIndicators = new JCheckBox("Damage Indicators");
		chckbxDamageIndicators.setBounds(200, 95, 150, 23);
		panel.add(chckbxDamageIndicators);
		chckbxDamageIndicators.setToolTipText("Does what it says on the tin!");
		chckbxDamageIndicators.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Damage Indicators: " + chckbxDamageIndicators.isSelected());
			}
		});
		chckbxDamageIndicators.setBackground((Color) null);
		
		chckbxInventoryTweaks = new JCheckBox("Inventory Tweaks");
		chckbxInventoryTweaks.setBounds(40, 251, 150, 23);
		panel.add(chckbxInventoryTweaks);
		chckbxInventoryTweaks.setToolTipText("Some nice inventory improvements!");
		chckbxInventoryTweaks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Inventory Tweaks: " + chckbxInventoryTweaks.isSelected());
			}
		});
		chckbxInventoryTweaks.setBackground((Color) null);
		
		
		chckbxCraftguide = new JCheckBox("CraftGuide");
		chckbxCraftguide.setBounds(40, 225, 150, 23);
		panel.add(chckbxCraftguide);
		chckbxCraftguide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("CraftGuide: " + chckbxCraftguide.isSelected());
			}
		});
		chckbxCraftguide.setBackground((Color) null);
		
		chckbxSoundcontrol = new JCheckBox("SoundControl 10");
		chckbxSoundcontrol.setBounds(40, 199, 150, 23);
		panel.add(chckbxSoundcontrol);
		chckbxSoundcontrol.setToolTipText("Complete sound control!");
		chckbxSoundcontrol.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("SoundControl 10: " + chckbxSoundcontrol.isSelected());
			}
		});
		chckbxSoundcontrol.setBackground((Color) null);
		
		chckbxJourneymap = new JCheckBox("JourneyMap");
		chckbxJourneymap.setBounds(40, 173, 150, 23);
		panel.add(chckbxJourneymap);
		chckbxJourneymap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("JourneyMap: " + chckbxJourneymap.isSelected());
			}
		});
		chckbxJourneymap.setBackground((Color) null);
		
		chckbxShatter = new JCheckBox("Shatter");
		chckbxShatter.setBounds(40, 147, 150, 23);
		panel.add(chckbxShatter);
		chckbxShatter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Shatter: " + chckbxShatter.isSelected());
			}
		});
		chckbxShatter.setBackground((Color) null);
		
		chckbxZansminimap = new JCheckBox("Zans Minimap");
		chckbxZansminimap.setBounds(40, 121, 150, 23);
		panel.add(chckbxZansminimap);
		chckbxZansminimap.setToolTipText("A highly customisable minimap!");
		chckbxZansminimap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Zans Minimap: " + chckbxZansminimap.isSelected());
			}
		});
		chckbxZansminimap.setBackground((Color) null);
		
		chckbxZyinsHud = new JCheckBox("Zyin's HUD");
		chckbxZyinsHud.setBounds(40, 95, 150, 23);
		panel.add(chckbxZyinsHud);
		chckbxZyinsHud.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Zyin's HUD: " + chckbxZyinsHud.isSelected());
			}
		});
		chckbxZyinsHud.setBackground((Color) null);
		
		chckbxBupload = new JCheckBox("bUpload");
		chckbxBupload.setBounds(40, 69, 150, 23);
		panel.add(chckbxBupload);
		chckbxBupload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("bUpload: " + chckbxBupload.isSelected());
			}
		});
		chckbxBupload.setBackground((Color) null);
		
		chckbxNovamenu = new JCheckBox("Novamenu");
		chckbxNovamenu.setBounds(40, 43, 150, 23);
		panel.add(chckbxNovamenu);
		chckbxNovamenu.setToolTipText("A funky-fresh looking menu!");
		chckbxNovamenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Novamenu: " + chckbxNovamenu.isSelected());
			}
		});
		chckbxNovamenu.setBackground(new Color(61,89,171));
		
		chckbxOpis = new JCheckBox("Opis");
		chckbxOpis.setBounds(40, 17, 150, 23);
		panel.add(chckbxOpis);
		chckbxOpis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Opis: " + chckbxOpis.isSelected());
			}
		});
		chckbxOpis.setBackground((Color) null);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 318, 421, 126);
		panel_3.setBackground(new Color(61,89,171));
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		lblReachable = new JLabel("The server is currently offline!");
		lblReachable.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblReachable.setBounds(226, 101, 185, 14);
		panel_3.add(lblReachable);
		lblReachable.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		lblReachable.setBackground(Color.RED);
		
		frame.repaint();
		frame.getContentPane().repaint();
		
		Choice choice = new Choice();
		choice.setBounds(146, 29, 185, 20);
		choice.add("MagiTech Madness");
		panel_3.add(choice);
		
		JLabel lblClickHereTo = new JLabel("Click here to donate");
		lblClickHereTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) { 
				if(Desktop.isDesktopSupported())
				{
				  try {
					Desktop.getDesktop().browse(new URI("https://www.paypal.com/cgi-bin/webscr?=_s-xclick&hosted_button_id=3L743FQ7C9VFN"));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				}
			}
		});
		lblClickHereTo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblClickHereTo.setForeground(Color.YELLOW);
		lblClickHereTo.setBounds(10, 101, 191, 14);
		panel_3.add(lblClickHereTo);
		
		choice_1 = new Choice();
		choice_1.setBounds(146, 55, 185, 20);
		choice_1.add("1024MB");
		choice_1.add(2*1024 + "MB");
		choice_1.add(3*1024 + "MB");
		choice_1.add(4*1024 + "MB");
		choice_1.add(5*1024 + "MB");
		choice_1.add(6*1024 + "MB");
		choice_1.add(7*1024 + "MB");
		choice_1.add(8*1024 + "MB");
		choice_1.add(9*1024 + "MB");
		choice_1.add(10*1024 + "MB");
		choice_1.add(11*1024 + "MB");
		choice_1.add(12*1024 + "MB");
		panel_3.add(choice_1);
		
		lblModpack = new JLabel("Modpack:");
		lblModpack.setBounds(76, 33, 62, 14);
		lblModpack.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_3.add(lblModpack);
		
		lblMemory = new JLabel("Memory:");
		lblMemory.setBounds(80, 57, 72, 14);
		lblMemory.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_3.add(lblMemory);
		
		int mgb = 1024*1024;
		final MemoryMXBean mb = ManagementFactory.getMemoryMXBean();
		System.out.println("[INFO] Free Memory: " + (mb.getHeapMemoryUsage().getMax() - mb.getHeapMemoryUsage().getUsed()) / mgb + "MB");
		
		frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	            exitProcedure();
	        }
	    });
		
	}
	
	public static void exitProcedure() {
		(new Thread(new ExitProcedure())).start();
	}
	
	public static void dynamics() {
		(new Thread(new DynamicWorker())).start();
	}
	
	public static void checkFiles() {
		(new Thread(new FileChecker())).start();
	}
	
	public static void UpdateModpack() {
		(new Thread(new ProcessServerpack())).start();
	}
	
	public static void authenticateUser() {
		btnLogin.setEnabled(false);
		btnLogin.setToolTipText("Logging in...");
		(new Thread(new Authenticator())).start();
	}
	
	public static void loadSettings() {
		(new Thread(new LoadSettings())).start();
	}
}
