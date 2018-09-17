package com.tetris.ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tetris.control.GameControl;
import com.tetris.util.FrameUtil;

public class FrameSaveScore extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// create Button object for confirm
	private JButton BTNOK = null;
	// create Button object for confirm
	private JButton BTNCancel = null;
	// create a JLabel for score
	private JLabel score = null;
	// Create a text field for user name
	private JTextField playerName = null;
	// create a string for player name
	private String playerNameString = null;
	// create a JLabel object for error message

	private JLabel errorMsg = null;
	// set player name length
	private int nameLimitLength = 15;
	// create GameControl object for score
	private GameControl gameControl = null;

	public FrameSaveScore(GameControl gameControl) {

		this.gameControl = gameControl;
		this.setTitle("statistic");
		this.setSize(300, 200);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createCam();
		this.createActions();

	}

	// create events for buttons
	private void createActions() {
		// retrieve player's name
		this.BTNOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				playerNameString = playerName.getText();
				if (playerNameString.length() > nameLimitLength || playerNameString == null
						|| "".equals(playerNameString)) {
					errorMsg.setText("Length should be between 1-15!");
				} else {
					setVisible(false);
					gameControl.afterGameEvent();
					gameControl.saveScore(playerNameString);
				}
				setVisible(false);
				setAlwaysOnTop(false);
				initData();
			}
		});

		this.BTNCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				setAlwaysOnTop(false);
				initData();
			}
		});
	}

	// clear all remains
	private void initData() {
		playerName.setText("");
		score.setText("");
	}

	// create save score panel
	private void createCam() {
		// create north panel to display score
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.score = new JLabel();
		north.add(score);
		// create error message label
		this.errorMsg = new JLabel();
		this.errorMsg.setForeground(Color.red);
		north.add(errorMsg);
		this.add(north, BorderLayout.NORTH);

		JPanel centre = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.playerName = new JTextField(nameLimitLength);

		centre.add(new JLabel("You name is: "));
		centre.add(this.playerName);
		this.add(centre, BorderLayout.CENTER);

		this.BTNOK = new JButton("OK");
		this.BTNCancel = new JButton("Don't save");
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// add BTNOK to the panel
		south.add(BTNOK);
		// add BTNCancel to the panel
		south.add(BTNCancel);
		this.add(south, BorderLayout.SOUTH);
	}

	// show score
	public void showScore(int nowScore) {
		this.score.setText("your score is: " + nowScore);
		this.setAlwaysOnTop(true);
		this.setVisible(true);

	}
}
