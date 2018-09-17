package com.tetris.ui;

import java.awt.Graphics;

import com.tetris.config.GameConfig;

public class LayerBackGround extends Layer {

	public LayerBackGround(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(IMG_SOUND.getIMG_BACKGROUND(), 0, 0, GameConfig.getFRAME_CONFIG().getWIDTH_FRAME(),
				GameConfig.getFRAME_CONFIG().getHEIGHT_FRAME(), null);
	}

}
