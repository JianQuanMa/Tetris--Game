package com.tetris.ui;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import com.tetris.config.GameConfig;

public class IMG_SOUND {

	private IMG_SOUND() {
	}

	// retrieve the game play sound
	private static File SND_GAMEPLAY = new File("materials/Sounds/gamePlay.wav").getAbsoluteFile();

	// to retrieve the window image
	private static Image IMG_WINDOW = new ImageIcon("materials/Graphics/window/windowMain.png").getImage();
	// to retrieve the author image
	private static Image IMG_AUTHOR = new ImageIcon("materials/Graphics/strings/author.png").getImage();
	// to retrieve the background image
	private static Image IMG_BACKGROUND = new ImageIcon("materials/Graphics/bg/bgPic.png").getImage();
	// to retrieve the start image
	public static ImageIcon IMG_START = new ImageIcon("materials/Graphics/strings/start.png");
	// to retrieve the config and credit image
	public static ImageIcon IMG_CONFIG_CREDIT = new ImageIcon("materials/Graphics/strings/config_and_credit.png");
	// to retrieve the quit image
	private static Image IMG_QUIT = new ImageIcon("materials/Graphics/strings/quit.jpg").getImage();
	// to retrieve the version image
	private static Image IMG_VERSION = new ImageIcon("materials/Graphics/strings/tetris.png").getImage();
	// to retrieve the local record string image
	private static Image IMG_LOCALRECORD = new ImageIcon("materials/Graphics/strings/localRecord.png").getImage();
	// to retrieve the piece image
	private static Image IMG_PIECE = new ImageIcon("materials/Graphics/game/piece.png").getImage();
	// to retrieve the score image
	private static Image IMG_SCORE = new ImageIcon("materials/Graphics/strings/score.png").getImage();
	// to retrieve the pause Image
	private static Image IMG_PAUSE = new ImageIcon("materials/Graphics/strings/pause.png").getImage();
	// create a list of picture of digits
	static Image[] IMG_LIST_DIGITS = new Image[10];

	public static ImageIcon getIMG_START() {
		return IMG_START;
	}

	public static ImageIcon getIMG_CONFIG_CREDIT() {
		return IMG_CONFIG_CREDIT;
	}

	static {
		for (int i = 0; i < IMG_LIST_DIGITS.length; i++) {
			IMG_LIST_DIGITS[i] = new ImageIcon("materials/Graphics/strings/" + i + ".png").getImage();
		}
	}

	public static Image getIMG_AUTHOR() {
		return IMG_AUTHOR;
	}

	public static Image getIMG_BACKGROUND() {
		return IMG_BACKGROUND;
	}

	public static Image getIMG_QUIT() {
		return IMG_QUIT;
	}

	public static Image getIMG_VERSION() {
		return IMG_VERSION;
	}

	public static Image getIMG_LOCALRECORD() {
		return IMG_LOCALRECORD;
	}

	public static Image getIMG_PIECE() {
		return IMG_PIECE;
	}

	public static Image getIMG_SCORE() {
		return IMG_SCORE;
	}

	public static Image getIMG_WINDOW() {
		return IMG_WINDOW;
	}

	public static Image[] getIMG_LIST_DIGITS() {
		return IMG_LIST_DIGITS;
	}

	public static Image getIMG_PAUSE() {
		return IMG_PAUSE;
	}

	public static File getSND_GAMEPLAY() {
		return SND_GAMEPLAY;
	}

	public static ImageIcon resizeIcon(ImageIcon icon) {
		Image img = icon.getImage();
		ImageIcon newIcon = new ImageIcon(img.getScaledInstance(
				(int) (GameConfig.getFRAME_CONFIG().getSCALER() * img.getWidth(null)),
				(int) (GameConfig.getFRAME_CONFIG().getSCALER() * img.getHeight(null)), java.awt.Image.SCALE_SMOOTH));
		return newIcon;
	}

}
