package com.tetris.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import com.tetris.config.GameConfig;
import com.tetris.dao.DataDisk;
import com.tetris.dto.GameDTO;

public class LayerDataBase extends Layer {
	// set the number of max top matches to be stored in the database
	private static final int MAX_ROW = GameConfig.getDATA_CONFIG().getMAX_DATA_ROWS();
	// retrieve data from dataDIsk
	private static HashMap<String, String> dataSource = (HashMap<String, String>) GameConfig.getDATA_CONFIG().getDataB()
			.getParam();
	private GameDTO dto = new GameDTO();
	private DataDisk dd = new DataDisk(dataSource);
	// create a 2D array for the xywh of the squares of local record bars
	private static int[][] barDimensions = new int[GameConfig.getDATA_CONFIG().getMAX_DATA_ROWS()][4];
	private static int barHeight;

	public LayerDataBase(int x, int y, int w, int h) {
		super(x, y, w, h);
		// calculate bar height
		barHeight = (this.h - (LayerDataBase.MAX_ROW + 2) * Layer.PADDING
				- IMG_SOUND.getIMG_LOCALRECORD().getHeight(null)) / 5;
		// calculate dimensions of the bars
		for (int i = 0; i < barDimensions.length; i++) {
			// x
			barDimensions[i][0] = this.x + Layer.PADDING + Layer.CORNER_SIZE;
			// y
			barDimensions[i][1] = this.y + IMG_SOUND.getIMG_LOCALRECORD().getHeight(null) + 2 * Layer.PADDING
					+ i * (LayerDataBase.barHeight + Layer.PADDING);
			// w
			barDimensions[i][2] = this.w - 2 * (Layer.PADDING + Layer.CORNER_SIZE);
			// h
			barDimensions[i][3] = LayerDataBase.barHeight;
		}
	}

	public void paint(Graphics g) {

		this.createWindow(g);

		this.dto.setLocalRecord(dd.loadPlayData());
		// draw the string of local record
		g.drawImage(IMG_SOUND.getIMG_LOCALRECORD(), this.x + Layer.PADDING, this.y + Layer.PADDING, null);
		int currentPlayerScore;
		String currentPlayerName;
		for (int i = 0; i < LayerDataBase.MAX_ROW; i++) {
			if (this.dto.getLocalRecord() == null) {
				currentPlayerScore = 0;
				currentPlayerName = "null";
			} else {
				// retrieve the current player's score in the leader board
				currentPlayerScore = this.dto.getLocalRecord().get(i).getPlayerScore();
				// retrieve the current player's name
				currentPlayerName = this.dto.getLocalRecord().get(i).getPlayerName();
			}
			// paint the process bars of players
			this.paintBar(barDimensions[i][0], barDimensions[i][1], barDimensions[i][2], barDimensions[i][3],
					currentPlayerScore, GameDTO.getMaxScore(), g);
			g.setFont(g.getFont().deriveFont(18.0f));
			g.setColor(Color.BLACK);
			// print the names of the players
			this.centrePaint(barDimensions[i][0], barDimensions[i][1],
					g.getFontMetrics().stringWidth(currentPlayerName) + 2 * Layer.PADDING, barDimensions[i][3],
					currentPlayerName, g);
			// print the scores of the players
			this.rightPaint(barDimensions[i][0], barDimensions[i][1], barDimensions[i][2], barDimensions[i][3],
					String.valueOf(currentPlayerScore), g);

		}
	}

}
