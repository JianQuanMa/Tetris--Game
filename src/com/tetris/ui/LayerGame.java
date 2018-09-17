package com.tetris.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import com.tetris.config.GameConfig;
import com.tetris.dto.GameDTO;
import com.tetris.entity.GameBehavior;

public class LayerGame extends Layer {

	// PIECE_SIZE tells what size of the each rectangle of the piece should be
	private final static int PIECE_SIZE = GameConfig.getFRAME_CONFIG().getPIECE_SIZE();
	// GAP tells how wide between the panel and frame
	private final static int GAP = GameConfig.getSYSTEM_CONFIG().getGAP();
	// retrieve the height of the piece image
	private final static int IMG_PIECE_HEIGHT = IMG_SOUND.getIMG_PIECE().getHeight(null);
	// retrieve the pause image
	private final static Image IMG_PAUSE = IMG_SOUND.getIMG_PAUSE();

	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);

	}

	public void paint(Graphics g) {
		// create the window
		this.createWindow(g);
		// to retrieve GameBehavior object
		GameBehavior gameBehavior = this.dto.getGameBehavior();
		// only draw main behavior if an GameBehavior object was created
		if (gameBehavior != null) {
			// draw main behavior
			this.drawMainBehavior(g, dto);
			// draw the map

		}
		// draw the map
		this.drawMap(g);
		if (this.dto.isPause()) {

			this.centrePaint(this.x, this.y, this.w, this.h, IMG_PAUSE, IMG_PAUSE.getWidth(null),
					IMG_PAUSE.getHeight(null), g);

		}

	}

	// draw the map once the application is started (before main thread)
	private void drawMap(Graphics g) {
		// retrieve the map object to print the map
		boolean[][] map = this.dto.getGameMap();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y]) {
					this.drawPointByCoord(x, y, -1, g);
				}
			}
		}
	}

	// draw main behavior draws the piece and the shadow
	private void drawMainBehavior(Graphics g, GameDTO dto) {
		this.dto = dto;
		this.setGameDTO(this.dto);

		// to get the list of points of the piece
		Point[] points = this.dto.getGameBehavior().getBehaviorPoint();
		// to paint the shadow on columns of piece
		this.drawShadow(points, dto.isShadow(), g);
		// to get and draw the piece code
		int typeCode = dto.getGameBehavior().getTypeCode();
		for (int i = 0; i < points.length; i++) {
			drawPointByCoord(points[i].x, points[i].y, typeCode, g);
		}
	}

	/*
	 * to draw a square point for the piece
	 */
	private void drawPointByCoord(int x, int y, int imgID, Graphics g) {
		g.drawImage(IMG_SOUND.getIMG_PIECE(), this.x + x * PIECE_SIZE + GAP, this.y + y * PIECE_SIZE + GAP,
				this.x + x * PIECE_SIZE + PIECE_SIZE + GAP, this.y + y * PIECE_SIZE + PIECE_SIZE + GAP,
				(imgID + 1) * IMG_PIECE_HEIGHT, 0, (imgID + 1) * IMG_PIECE_HEIGHT + IMG_PIECE_HEIGHT, IMG_PIECE_HEIGHT,
				null);

	}

	/*
	 * drawShadow method takes in a Point[] and a boolean value to decide if
	 * should paint shadow strips on the column(s) of the piece using the last
	 * square piece of the IMG_PIECE
	 */
	private void drawShadow(Point[] points, boolean canDrawShadow, Graphics g) {
		// create mostLeftX and mostRightX to indicate the x's of the most left
		// and most right points
		if (!canDrawShadow)
			return;
		int mostLeftX = GameConfig.getSYSTEM_CONFIG().getX_END();
		int mostRightX = GameConfig.getSYSTEM_CONFIG().getX_START();
		// loop through to find the most left and most right x
		for (Point point : points) {

			mostLeftX = point.x < mostLeftX ? point.x : mostLeftX;
			mostRightX = point.x > mostRightX ? point.x : mostRightX;
		}
		// paint the shadow
		mostRightX += 1;
		for (int i = mostLeftX; i < mostRightX; i++) {
			g.drawImage(IMG_SOUND.getIMG_PIECE(), this.x + mostLeftX * PIECE_SIZE + PADDING / 5 * 4,
					this.y + PADDING / 2, this.x + mostRightX * PIECE_SIZE + PADDING / 5 * 4,
					this.y + this.h - PADDING / 2, IMG_SOUND.getIMG_PIECE().getWidth(null) - IMG_PIECE_HEIGHT, 0,
					IMG_SOUND.getIMG_PIECE().getWidth(null), IMG_PIECE_HEIGHT, null);
		}
	}

	public void setGameDTO(GameDTO dto) {
		this.dto = dto;
	}
}
