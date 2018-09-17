package com.tetris.ui;

import java.awt.Graphics;

public class LayerAuthor extends Layer {

	public LayerAuthor(int x, int y, int w, int h) {
		super(x, y, w, h);

	}

	public void paint(Graphics g) {
		this.createWindow(g);
		this.centrePaint(this.x, this.y, this.w, this.h, IMG_SOUND.getIMG_AUTHOR(), IMG_SOUND.getIMG_AUTHOR().getWidth(null),
				IMG_SOUND.getIMG_AUTHOR().getHeight(null), g);

	}

}
