package com.tetris.ui.window;

import javax.swing.JFrame;

import com.tetris.config.GameConfig;
import com.tetris.util.FrameUtil;

public class FrameGame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameGame(PanelGame panelGame) {
		this.setTitle("Tetris Game");

		this.setSize(GameConfig.getFRAME_CONFIG().getWIDTH_FRAME(), GameConfig.getFRAME_CONFIG().getHEIGHT_FRAME());
		// set the frame center
		FrameUtil.setFrameCenter(this);
		// set the frameGame to contain PanelGame
		this.setContentPane(panelGame);

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
