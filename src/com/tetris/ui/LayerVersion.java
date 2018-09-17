package com.tetris.ui;

import java.awt.Graphics;

public class LayerVersion extends Layer {

	public LayerVersion(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		this.centrePaint(this.x, this.y, this.w, this.h, IMG_SOUND.getIMG_VERSION(), IMG_SOUND.getIMG_VERSION().getWidth(null),
				IMG_SOUND.getIMG_VERSION().getHeight(null), g);
	}

}
