package com.tetris.ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.tetris.control.GameControl;
import com.tetris.dto.GameDTO;
import com.tetris.util.FrameUtil;

public class FrameControllConfig extends JFrame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// create ok button
	private JButton BtnOK = new JButton("OK");
	// create cancel button
	private JButton BtnCancel = new JButton("cancel");
	// create apply button
	private JButton BtnApply = new JButton("apply");
	// create a JTextField array for the keys
	private TextController[] keyText = new TextController[8];
	// create explainatory text
	private final static String[] EXPLAIN_TEXT_ARRAY = { "SHIFT", "DOWN", "LEFT", "RIGHT", "keyFuncUp", "keyFuncDown",
			"keyFuncLeft", "keyFuncRight" };
	// create method name array
	private final static String[] METHOD_NAME_ARRAY = { "keyUp", "keyDown", "keyLeft", "keyRight", "keyFunctionUp",
			"keyFunctionDown", "keyFunctionLeft", "keyFunctionRight" };
	// create a list to tell which key matches to which key
	private final static String[] KEY_NAME_ARRAY = { "up", "down", "left", "right", "w", "s", "a", "d" };
	// config file path
	private final static String CONTROL_PATH = "data/control.dat";
	// error message
	private JLabel errorMSG = new JLabel();
	// create a dto object
	private GameDTO dto = new GameDTO();
	// create a GameControl object
	private GameControl gameControl;

	/*
	 * create an array for contributors for the work and their links, odd lines
	 * are attributes and even lines are links
	 * 
	 */
	private final static String[] CONTRIBUTORS_ARRAY = {
			"BackGround Picture: ¡°Glacial Mountains: Parallax Background¡± by vnitti is licensed under CC BY 0.0",
			"GamePlay sound: \"awesomeness.wav\" by mrpoly is licensed under CC BY 0.0 " };
	// create an array for the links of the work
	private final static JLabel[] LINKS_ARRAY = {
			new JLabel(
					"<html> Website : <a href=\"\"> https://vnitti.itch.io/glacial-mountains-parallax-background </a></html>"),
			new JLabel("<html> Website : <a href=\"\"> https://opengameart.org/content/menu-music </a></html>") };

	// Explanatory text array
	private final static String[] EXPLANATORY_TEXT_ARRAY = { "keyUp: shift the piece", "keyDown: move down",
			"keyLeft: move left", "keyRight: move right", "KeyFuncUp: toggle shadow", "keyFunctionDown: goundlanding",
			"keyFunctionLeft: pause", "keyFunctionRight: toggle music", "U: add 500 points" };

	public FrameControllConfig(GameControl gameControl) {

		// init the text field array
		this.initTextField();
		this.writeConfig();
		// init GameControl object
		this.gameControl = gameControl;
		// set layout manager
		this.setLayout(new BorderLayout());
		// set size
		this.setSize(800, 500);
		// set title
		this.setTitle("config");
		// set frame center
		FrameUtil.setFrameCenter(this);
		// to add the main panel
		this.add(this.createMainPanel(), BorderLayout.CENTER);
		// to add the button panel
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);

	}

	// init the text field array
	@SuppressWarnings("unchecked")
	private void initTextField() {
		this.addTextFields();

		try {
			// create a objectInputStream object
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONTROL_PATH));
			// store the data as a hashMap object

			HashMap<Integer, String> cfgKeySet;

			cfgKeySet = (HashMap<Integer, String>) ois.readObject();

			// close the ois
			ois.close();
			// use set<ENtry<Integer,String>> for convenience
			Set<Entry<Integer, String>> entrySet = cfgKeySet.entrySet();
			/*
			 * loop through entry set, if methodName matches set their value
			 * equal
			 */
			for (Entry<Integer, String> e : entrySet) {
				for (TextController tc : keyText) {
					if (tc.getMethodName().equals(e.getValue())) {
						tc.setKeyCode(e.getKey());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// create the panel for buttons
	private JPanel createButtonPanel() {
		// create panel
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// add OK button to the panel
		this.BtnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!writeConfig()) {
					setVisible(false);
					gameControl.setOver();
				}
			}

		});
		this.errorMSG.setForeground(Color.red);
		jp.add(this.errorMSG);
		jp.add(this.BtnOK);
		// add ActionListenner to the cancel button
		this.BtnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
			}
		});
		// add cancel button to the panel
		jp.add(this.BtnCancel);
		// add ActionListenner to the apply button
		this.BtnApply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeConfig();
			}

		});

		jp.add(this.BtnApply);
		return jp;
	}

	// create the main panel
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Setting", this.createControllerPanel());
		jtp.addTab("Credit", this.createCreditPanel());
		jtp.addTab("clear data", this.createClearDataPanel());
		jtp.setVisible(true);
		return jtp;

	}

	// create a panel for clearing local record
	private JPanel createClearDataPanel() {
		JButton clear = new JButton("Clear local record");

		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dto.clear();
			}
		});
		JPanel jp = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				setLayout(null);

			}
		};
		jp.add(clear);
		return jp;
	}

	// create the panel for credit
	private JPanel createCreditPanel() {

		JPanel jp = new JPanel() {

			private static final long serialVersionUID = 1L;
			int x = 20, y = 20, gap = 30;

			@Override
			public void paintComponent(Graphics g) {
				addCreditList(g, x, y, gap);
			}
		};
		jp.setLayout(null);
		int x = 20, y = 60, gap = 30, width = 500, height = 30;
		for (JLabel jb : LINKS_ARRAY) {

			jb.setBounds(x, y, width, height);
			jp.add(jb);
			y += gap * 2 + 10;
		}
		return jp;
	}

	// add the list of contributors of the work on the credit panel
	protected void addCreditList(Graphics g, int x, int y, int gap) {

		for (int i = 0; i < CONTRIBUTORS_ARRAY.length; i++) {

			g.drawLine(x, y, this.getWidth() - gap, y);
			y += gap;
			g.drawString(CONTRIBUTORS_ARRAY[i], x, y);
			y += gap + 10;

		}
	}

	// create a controller panel for the main tabbed pane
	private JPanel createControllerPanel() {
		JPanel jp = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				addControllerKey(g);
				addExplantoryText(g);
			}
		};
		// set layout manager
		jp.setLayout(null);
		// add text fields
		for (JTextField f : keyText) {
			jp.add(f);
		}
		return jp;
	}

	// write user's preference to the config file
	private boolean writeConfig() {
		// create an HashMap object
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		// put each text in text fields the key set
		for (int i = 0; i < this.keyText.length; i++) {
			int keyCode = this.keyText[i].getKeyCode();
			if (keyCode == 0) {
				return false;
			}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		if (keySet.size() != 8) {
			return false;
		}
		// write the key set to the data file
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONTROL_PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			this.errorMSG.setText(e.getMessage());
			return false;
		}
		this.errorMSG.setText(null);
		return true;
	}

	// add Controller Keys
	private void addControllerKey(Graphics g) {
		int x1 = 100;
		int x2 = 300;
		int y = 50;
		for (int i = 0; i < EXPLAIN_TEXT_ARRAY.length; i++) {
			g.drawString(EXPLAIN_TEXT_ARRAY[i], x1 + (x2 - x1) * (i / 4) - 100, (y += 40) % (4 * 40) + 22);
		}
	}

	// add explanatory text
	private void addExplantoryText(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.BOLD, 14));
		int height = 185;
		for (String s : EXPLANATORY_TEXT_ARRAY) {
			g.drawString(s, 40, height);
			height += 25;
		}
	}

	// add text fields
	private void addTextFields() {
		int x1 = 100;
		int x2 = 300;
		int y = 50;
		int w = 80;
		int h = 40;
		// add text fields to the panel
		for (int i = 0; i < 8; i++) {
			keyText[i] = new TextController(x1 + (x2 - x1) * (i / 4), (y += 40) % (4 * 40), w, h, METHOD_NAME_ARRAY[i]);
			keyText[i].setText(KEY_NAME_ARRAY[i]);
		}
	}
}
