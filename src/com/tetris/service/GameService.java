package com.tetris.service;

import java.awt.Point;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.tetris.config.GameConfig;
import com.tetris.dto.GameDTO;
import com.tetris.entity.GameBehavior;
import com.tetris.ui.IMG_SOUND;

/*
 * GameService explains the game logics
 * */
public class GameService implements GameServiceInterface {
	// to obtain a GmaeDTO object
	private GameDTO dto;
	// random number generator
	private Random random = new Random();
	// set the number of types of pieces equals to 6
	private static final int NUMBER_OF_TYPE = GameConfig.getSYSTEM_CONFIG().getTYPE_CONFIG().size() - 1;
	// create an ais object
	private AudioInputStream audioGamePlay;
	// create a Clip object
	private Clip clip;

	public GameService(GameDTO dto) {
		// retrieve the dto object
		this.dto = dto;

		try {
			this.audioGamePlay = AudioSystem.getAudioInputStream(IMG_SOUND.getSND_GAMEPLAY());
			this.clip = AudioSystem.getClip();
			this.clip.open(audioGamePlay);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// shift the piece
	public void keyUp() {
		/*
		 * synchronize the dto so that it avoids miss positioning when
		 * performing two actions at the same time
		 */
		synchronized (this.dto) {
			this.dto.getGameBehavior().shift(this.dto.getGameMap());
		}
	}

	// move down the piece, fortify the piece if touch down, and generate a new
	// piece upon touch down
	public boolean keyDown() {
		synchronized (this.dto) {
			if (this.dto.getGameBehavior().isMovable(0, 1, this.dto.getGameMap())) {
				return true;
			}
			// to retrieve the gameMap object
			boolean[][] map = this.dto.getGameMap();
			Point[] piece = this.dto.getGameBehavior().getBehaviorPoint();
			for (int i = 0; i < piece.length; i++) {
				map[piece[i].x][piece[i].y] = true;
			}
			// decide if can remove lines
			this.isremoveLinesable();

			/*
			 * to retrieve the next piece from the generated next piece and put
			 * it in the game panel
			 */

			this.dto.getGameBehavior().init(this.dto.getNext());
			// to randomly generate a new next piece

			this.dto.setNext(random.nextInt(NUMBER_OF_TYPE));
			// if the game has ended, perform end events
			if (this.isEnd()) {
				GameDTO.setStart(false);
			}
			return false;
		}
	}

	// check if the game has ended
	private boolean isEnd() {
		// retrieve the current points
		Point[] piece = this.dto.getGameBehavior().getBehaviorPoint();
		// retrieve the current map
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < piece.length; i++) {
			if (map[piece[i].x][piece[i].y]) {
				this.clip.stop();
				return true;
			}
		}

		return false;
	}

	// to remove a line
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < map.length; x++) {
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y - 1];
			}
		}
	}

	// decide if can remove lines, if yes, remove lines and add points
	private boolean isremoveLinesable() {
		boolean removeLines = false;
		// count how many lines to be removed
		int numberOfLinesRemoved = 0;
		// retrieve the game map
		boolean[][] map = this.dto.getGameMap();
		ROWS: for (int y = 0; y < GameDTO.ROWS; y++) {
			for (int x = 0; x < GameDTO.COLUMNS; x++) {
				if (!map[x][y]) {
					continue ROWS;
				}
			}
			removeLines = true;
			// remove a line and add scores
			this.removeLine(y, map);
			numberOfLinesRemoved++;
		}
		// add points if removed lines
		if (removeLines)
			this.addPoints(numberOfLinesRemoved);
		return removeLines;
	}

	// add points based on lines removed
	private void addPoints(int lines) {
		int pointsToAdd = 5;
		for (; lines > 1; lines--) {
			pointsToAdd += pointsToAdd;
		}
		this.dto.setNowScore(this.dto.getNowScore() + pointsToAdd);
	}

	// move the piece to the left
	public void keyLeft() {
		synchronized (this.dto) {
			this.dto.getGameBehavior().isMovable(-1, 0, this.dto.getGameMap());
		}
	}

	// move the piece to the right
	public void keyRight() {
		synchronized (this.dto) {
			this.dto.getGameBehavior().isMovable(1, 0, this.dto.getGameMap());
		}
	}

	// for testing and cheating only
	public void keyTest() {

		this.dto.setNowScore(this.dto.getNowScore() + 500);
	}

	// toggle shadow
	@Override
	public void keyFuncUp() {
		synchronized (this.dto) {
			this.dto.toggleShadow();
		}
	}

	// sends the piece to the ground land
	@Override
	public void keyFuncDown() {
		synchronized (this.dto) {
			while (this.keyDown()) {
				this.keyDown();
			}
		}
	}

	// toggles the game thread
	@Override
	public void keyFuncLeft() {
		synchronized (this.dto) {
			this.dto.togglePause();
			this.toggleSound();
		}
	}

	// toggle sound
	@Override
	public void keyFuncRight() {
		synchronized (this.dto) {
			this.toggleSound();
		}
	}

	// start the main thread of the game
	@Override
	public void startGame() {
		// set next piece to generate
		this.dto.setNext(random.nextInt(NUMBER_OF_TYPE));
		// set the gameBehavior
		this.dto.setGameBehavior(new GameBehavior(random.nextInt(NUMBER_OF_TYPE)));
		// start the main thread
		GameDTO.setStart(true);
		// play the game play sound
		this.clip.start();
		// init the dto
		this.dto.DTOInit();
	}

	// main drop thread controls if the piece should auto drop
	@Override
	public void threadDrop() {
		this.keyDown();

	}

	@Override
	// toggle the sound
	public void toggleSound() {
		if (this.clip.isActive()) {
			this.clip.stop();
		} else {
			this.clip.start();
		}
	}

	@Override
	public Clip getClip() {
		return this.clip;
	}

}
