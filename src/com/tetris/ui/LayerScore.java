package com.tetris.ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.tetris.config.GameConfig;
import com.tetris.dto.GameDTO;

public class LayerScore extends Layer {

	// LayerScore.SPACE_NEXT is how much space to reserve for the next piece
	private static final int SPACE_NEXT = GameConfig.getFRAME_CONFIG().getSPACE_NEXT();
	// set the width of each digit
	private static final int DIGIT_WIDTH = GameConfig.getFRAME_CONFIG().getWIDTH_DIGIT();
	// height of the digits
	private static final int DIGIT_HEIGHT = GameConfig.getFRAME_CONFIG().getHEIGHT_DIGIT();

	// get the height for the score string and surrounding padding
	private static final int SPACE_SCORE_STRING = (int) ((PADDING * 2 + IMG_SOUND.getIMG_SCORE().getHeight(null))
			* GameConfig.getFRAME_CONFIG().getSCALER());

	private static boolean canDraw;

	// to retrieve GameDTO object
	GameDTO dto = new GameDTO();

	// create a list of image for the next piece to put

	private static Image[] NEXT_IMG;
	static {
		NEXT_IMG = new Image[7];

		for (int i = 0; i < NEXT_IMG.length; i++) {
			NEXT_IMG[i] = new ImageIcon("materials/Graphics/game/type" + i + ".png").getImage();
		}
	}

	// pieceCode stores the type of the piece
	int pieceCode;

	public LayerScore(int x, int y, int w, int h) {

		super(x, y, w, h);

	}

	// to print the score panel
	public void paint(Graphics g) {
		// create the window
		this.createWindow(g);
		// to draw the next piece if the main thread has started
		if (GameDTO.isStart()) {
			int doNext = this.dto.getNext() + 1;
			g.drawImage(NEXT_IMG[doNext],
					this.x + (LayerScore.SPACE_NEXT - NEXT_IMG[doNext].getWidth(null)) / 2 + Layer.CORNER_SIZE,
					this.y + (this.h - NEXT_IMG[doNext].getHeight(null)) / 2, null);
			// }
		}
		// draw the score string
		this.centrePaint(this.x + LayerScore.SPACE_NEXT, this.y, this.w - LayerScore.SPACE_NEXT,
				IMG_SOUND.getIMG_SCORE().getHeight(null) + PADDING * 2, IMG_SOUND.getIMG_SCORE(),
				IMG_SOUND.getIMG_SCORE().getWidth(null), SPACE_SCORE_STRING, g);

		// draw the current score
		this.drawNumber(this.dto.getNowScore(), g);

		// draw a bar to show the current score in relation to the max score
		this.paintBar(this.x + PADDING, this.y + SPACE_SCORE_STRING + DIGIT_HEIGHT, this.w - 2 * PADDING,
				this.h - SPACE_SCORE_STRING - DIGIT_HEIGHT - PADDING, this.dto.getNowScore(), GameDTO.getMaxScore(), g);

	}

	public static boolean isCanDraw() {
		return canDraw;
	}

	public static void setCanDraw(boolean canDraw) {
		LayerScore.canDraw = canDraw;
	}

	/*
	 * draw the score
	 */
	private void drawNumber(int numberToPrint, Graphics g) {

		// convert the score to a string
		String str = String.valueOf(numberToPrint);

		// length of the string
		int strLength = str.length();

		// to loop print every digit
		for (int i = 0; i < strLength; i++) {
			// retrieve the current digit
			int currentDigit = str.charAt(i) - 48;
			// draw the next piece on the left right of the panel
			g.drawImage(IMG_SOUND.getIMG_LIST_DIGITS()[currentDigit],
					this.x + LayerScore.SPACE_NEXT + (this.w - LayerScore.SPACE_NEXT - strLength * DIGIT_WIDTH) / 2
							+ i * DIGIT_WIDTH,
					this.y + SPACE_SCORE_STRING, DIGIT_WIDTH, this.h - SPACE_SCORE_STRING - 2 * PADDING, null);

		}
	}

}
