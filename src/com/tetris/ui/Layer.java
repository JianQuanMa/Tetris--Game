package com.tetris.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.tetris.config.GameConfig;
import com.tetris.dto.GameDTO;

/*
 * 
 * the Layer class is for manufacturing panels
 * @author: Jian Ma
 * 
 * */

public abstract class Layer {

	protected static int WINDOW_IMG_W = IMG_SOUND.getIMG_WINDOW().getWidth(null),
			WINDOW_IMG_H = IMG_SOUND.getIMG_WINDOW().getHeight(null),
			CORNER_SIZE = GameConfig.getFRAME_CONFIG().getCORNER_SIZE_LAYER_GAME(),
			PADDING = GameConfig.getFRAME_CONFIG().getPADDING();
	protected int x, y, w, h;
	protected GameDTO dto = null;

	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

	}
	// create the window

	protected void createWindow(Graphics g) {

		// upper-left corner
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x, y, x + CORNER_SIZE, y + CORNER_SIZE, 0, 0, CORNER_SIZE, CORNER_SIZE,
				null);
		// left bar
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x, y + CORNER_SIZE, x + CORNER_SIZE, y + h - CORNER_SIZE, 0, CORNER_SIZE,
				CORNER_SIZE, WINDOW_IMG_H - CORNER_SIZE, null);
		// lower-left corner
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x, y + h - CORNER_SIZE, x + CORNER_SIZE, y + h, 0,
				WINDOW_IMG_H - CORNER_SIZE, CORNER_SIZE, WINDOW_IMG_H, null);
		// upper bar
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x + CORNER_SIZE, y, x + w - CORNER_SIZE, y + CORNER_SIZE, CORNER_SIZE, 0,
				WINDOW_IMG_W - CORNER_SIZE, CORNER_SIZE, null);
		// upper right corner
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x + w - CORNER_SIZE, y, x + w, y + CORNER_SIZE,
				WINDOW_IMG_W - CORNER_SIZE, 0, WINDOW_IMG_W, CORNER_SIZE, null);
		// centre
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x + CORNER_SIZE, y + CORNER_SIZE, x + w - CORNER_SIZE,
				y + h - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, WINDOW_IMG_W - CORNER_SIZE, WINDOW_IMG_H - CORNER_SIZE,
				null);
		// right bar
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x + w - CORNER_SIZE, y + CORNER_SIZE, x + w, y + h - CORNER_SIZE,
				WINDOW_IMG_W - CORNER_SIZE, CORNER_SIZE, WINDOW_IMG_W, WINDOW_IMG_H - CORNER_SIZE, null);
		// lower bar
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x + CORNER_SIZE, y + h - CORNER_SIZE, x + w - CORNER_SIZE, y + h,
				CORNER_SIZE, WINDOW_IMG_H - CORNER_SIZE, WINDOW_IMG_W - CORNER_SIZE, WINDOW_IMG_H, null);
		// lower-right corner
		g.drawImage(IMG_SOUND.getIMG_WINDOW(), x + w - CORNER_SIZE, y + h - CORNER_SIZE, x + w, y + h,
				WINDOW_IMG_W - CORNER_SIZE, WINDOW_IMG_H - CORNER_SIZE, WINDOW_IMG_W, WINDOW_IMG_H, null);

	}

	abstract public void paint(Graphics g);

	/*
	 * centrePaint method takes in x,y,w,h of a box and a image along with its w
	 * and h, draw the image in the center of the box
	 */

	protected void centrePaint(int xBox, int yBox, int wBox, int hBox, Image image, int wImg, int hImg, Graphics g) {
		g.drawImage(image, xBox + (wBox - wImg) / 2, yBox + (hBox - hImg) / 2, wImg, hImg, null);
	}
	/*
	 * centrePaint method takes in x,y,w,h of a box and a image along with its w
	 * and h, draw the string in the center of the box
	 */

	protected void centrePaint(int xBox, int yBox, int wBox, int hBox, String str, Graphics g) {
		int wStr = g.getFontMetrics().stringWidth(str);
		int hStr = g.getFontMetrics().getHeight();
		g.drawString(str, xBox + (wBox - wStr) / 2, yBox + (hBox - hStr) * 4);

	}

	/*
	 * print the bar showing the process to victory
	 */

	protected void paintBar(int x, int y, int w, int h, int currentPoint, int maxPoint, Graphics g) {
		// set the roundness
		int roundness = 10;
		// set the color of the bar frame
		g.setColor(new Color(205, 244, 255));
		// draw the frame
		g.drawRoundRect(x, y, w, h, roundness, roundness);
		// get the percentage as currentPoint over maxPoint
		float percentage = ((float) currentPoint) / maxPoint;
		// set the color of the bar
		percentage = percentage > 1 ? 1 : percentage;
		g.setColor(new Color(180 - (int) (130 * percentage), 100 + (int) (130 * percentage),
				160 - (int) (60 * percentage)));
		// draw the bar
		g.fillRoundRect(x + 1, y + 1, (int) ((w - 2) * percentage), h - 2, roundness, roundness);

	}

	/*
	 * print the string on the right of the designated area
	 */

	protected void rightPaint(int x, int y, int w, int h, String string, Graphics g) {
		int strWidth = g.getFontMetrics().stringWidth(string);
		this.centrePaint(x + w - PADDING - strWidth, y, strWidth, h, string, g);
	}

	public void setDTO(GameDTO dto) {
		this.dto = dto;
	}
}
