package com.tetris.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.tetris.config.GameConfig;
import com.tetris.dao.DataDisk;
import com.tetris.entity.GameBehavior;

public class GameDTO {
	// create data
	private List<PlayerDTO> dbRecord;
	private List<PlayerDTO> localRecord;
	// game map
	private boolean[][] gameMap;
	// dimension of the map
	public final static int COLUMNS = GameConfig.getSYSTEM_CONFIG().getX_END() + 1;
	public final static int ROWS = GameConfig.getSYSTEM_CONFIG().getY_END() + 1;
	// max score to achieve
	private static final int MAX_SCORE = 50000;
	// decides if the main thread has started
	private static boolean start;
	// decides if the main game thread is paused
	private boolean isPause;

	// decides if should show shadow
	private boolean isShadow = true;

	private GameBehavior gameBehavior;
	// next piece to generate in the game panel
	private static int next;
	// current score
	private static int nowScore;
	private static int nowRemoveLine;
	private static DataDisk dd = new DataDisk(
			(HashMap<String, String>) GameConfig.getDATA_CONFIG().getDataB().getParam());

	// constructor
	public GameDTO() {
		this.DTOInit();

	}

	// initialization of an DTO object
	public void DTOInit() {

		this.gameMap = new boolean[COLUMNS][ROWS];
		GameDTO.nowScore = 0;
	}

	// clear local record
	public void clear() {
		GameDTO.dd.clearLocalRecord();

	}

	public List<PlayerDTO> getDbRecord() {
		return dbRecord;
	}

	public void setDbRecord(List<PlayerDTO> dbRecord) {
		this.fillRecord(dbRecord);
		this.dbRecord = dbRecord;
		Collections.sort(dbRecord);
	}

	public List<PlayerDTO> getLocalRecord() {
		return localRecord;
	}

	public void setLocalRecord(List<PlayerDTO> localRecord) {

		this.fillRecord(localRecord);
		this.localRecord = localRecord;

	}

	// fillRecord fills the record with N/A blanks if data is missing
	private List<PlayerDTO> fillRecord(List<PlayerDTO> record) {
		// create a new ArrayList of playerDTO if record is null
		if (record == null) {
			record = new ArrayList<PlayerDTO>();
		}
		// if record is less than 5, fill it with N/A blanks
		while (record.size() < GameConfig.getDATA_CONFIG().getMAX_DATA_ROWS()) {
			record.add(new PlayerDTO("N/A", 0));
		}
		return record;
	}

	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameBehavior getGameBehavior() {
		return gameBehavior;
	}

	public void setGameBehavior(GameBehavior gameBehavior) {
		this.gameBehavior = gameBehavior;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		GameDTO.next = next;
	}

	public int getNowScore() {
		return nowScore;
	}

	public void setNowScore(int nowScore) {
		GameDTO.nowScore = nowScore;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		GameDTO.nowRemoveLine = nowRemoveLine;
	}

	public static int getMaxScore() {
		return MAX_SCORE;
	}

	public static boolean isStart() {
		return start;
	}

	public static void setStart(boolean started) {
		start = started;
	}

	public boolean isShadow() {
		return this.isShadow;
	}

	public void setShadow(boolean isShadow) {
		this.isShadow = isShadow;
	}

	// toggle shadow
	public void toggleShadow() {
		this.isShadow = !this.isShadow;
	}

	public boolean isPause() {
		return isPause;
	}

	public void togglePause() {
		this.isPause = !isPause;

	}

}
