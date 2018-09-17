package com.tetris.control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sound.sampled.Clip;

import com.tetris.config.DataInterfaceConfig;
import com.tetris.config.GameConfig;
import com.tetris.dao.Data;
import com.tetris.dao.DataDisk;
import com.tetris.dto.GameDTO;
import com.tetris.dto.PlayerDTO;
import com.tetris.service.GameService;
import com.tetris.service.GameServiceInterface;
import com.tetris.ui.window.FrameControllConfig;
import com.tetris.ui.window.FrameGame;
import com.tetris.ui.window.FrameSaveScore;
import com.tetris.ui.window.PanelGame;

/*
 * Receive player's keyboard input
 * controls the game panel(display)
 * controls the game flow(logic)
 * */
public class GameControl {

	/*
	 * data access implement, data base not implemented
	 */
	private DataDisk data1;
	// private DataBase data2;
	/*
	 * controls the game panel
	 */
	private PanelGame panelGame;
	/*
	 * controls how the game flows including score adding up and killing rows
	 */
	private GameServiceInterface gameService;
	/*
	 * create map to control key input actions
	 */
	private Map<Integer, Method> actionList;

	// create a FrameSaveScore object
	private FrameSaveScore frameSaveScore;

	// create a FrameConfig object
	private FrameControllConfig frameControllConfig;

	// create gameDTO object
	private GameDTO dto = null;

	// create game thread
	private Thread thread = null;

	public GameControl() {

		// create a dto object
		this.dto = new GameDTO();
		// create GameService and connect it to dto
		this.gameService = new GameService(dto);
		// from implementation data1 connect to dataBase
		this.data1 = (DataDisk) createDataObject(GameConfig.getDATA_CONFIG().getDataB());
		// this.dto.setLocalRecord(data1.loadPlayData());
		// from implementation data2 connect to dataBase
		// this.data2 =
		// createDataObject(GameConfig.getDATA_CONFIG().getDataB());
		// this.dto.setDbRecord(data2.loadPlayData());
		// create GamePanel
		this.panelGame = new PanelGame(dto, this);

		// install control on the panel
		this.panelGame.setGameControl(this);

		// init game control config
		this.setControllerConfig();
		// init FrameControllConfig
		this.frameControllConfig = new FrameControllConfig(this);
		// cretae a save score frame
		this.frameSaveScore = new FrameSaveScore(this);
		// create FrameGame and make it contains the panelGame
		new FrameGame(this.panelGame);

	}

	// to read the controller config
	private void setControllerConfig() {
		this.actionList = new HashMap<Integer, Method>();
		try {
			// create ObjectInputStream object
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			// create a HashMap object
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
			// reflect the entry set in the form of set
			Set<Entry<Integer, String>> entrySet = cfgSet.entrySet();
			// put each value by method name in each element in the entry set
			for (Entry<Integer, String> e : entrySet) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * create the data object
	 */
	private Data createDataObject(DataInterfaceConfig dConfig) {
		Class<?> cls;
		try {
			// to retrieve class object
			cls = Class.forName(dConfig.getClassName());
			// to retrieve the constructor
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			// create an object
			return (Data) ctr.newInstance(dConfig.getParam());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// // decides what to do upon keys pressed
	public void actionByKeyCode(int keyCode) {
		// decide if key is allowed
		if (this.actionList.containsKey(keyCode)) {
			try {

				// to call the method
				this.actionList.get(keyCode).invoke(this.gameService);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// refresh the panel game
			this.panelGame.repaint();
		}
	}

	public void keyUp() {
		this.gameService.keyUp();
		this.panelGame.repaint();

	}

	public void keyDown() {
		this.gameService.keyDown();
		this.panelGame.repaint();

	}

	public void keyLeft() {
		this.gameService.keyLeft();
		this.panelGame.repaint();

	}

	public void keyRight() {
		this.gameService.keyRight();
		this.panelGame.repaint();

	}

	public void keyTest() {
		this.gameService.keyTest();
		this.panelGame.repaint();
	}

	// display the user config controller
	public void showUserConfig() {
		this.frameControllConfig.setVisible(true);
	}

	// move the piece as low as possible
	public void keyFuncDown() {
		this.gameService.keyFuncDown();
		this.panelGame.repaint();

	}

	// move the piece as low as possible
	public void keyFuncUp() {
		this.gameService.keyFuncUp();
		this.panelGame.repaint();
	}

	// move the piece as low as possible
	public void keyFuncLeft() {
		this.gameService.keyFuncLeft();
		this.panelGame.repaint();
	}

	// move the piece as low as possible
	public void keyFuncRight() {
		this.gameService.keyFuncRight();
		this.panelGame.repaint();
	}

	/*
	 * daughter window closed event
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControllerConfig();
	}

	/*
	 * afterGameEvent performed (after player inputs the player name in save
	 * score panel and hit the save button)
	 */
	public void afterGameEvent() {
		// display a saceScore panel with your current score
		this.showScore(this.dto.getNowScore());
		// turn back the buttons on when a game ends
		this.panelGame.buttonSwitch(true);
	}

	// show score
	public void showScore(int score) {
		this.frameSaveScore.showScore(score);

		this.frameSaveScore.setVisible(true);
	}

	// save score
	public void saveScore(String name) {
		PlayerDTO player = new PlayerDTO(name, this.dto.getNowScore());

		this.data1.savePlayData(player);
		this.dto.setLocalRecord(data1.loadPlayData());
		this.panelGame.repaint();
	}

	// start the game
	public void startMainThread() {
		// make buttons unclickable once the thread starts
		this.panelGame.buttonSwitch(false);
		// close the window
		this.frameControllConfig.setVisible(false);
		// close the saveScore window
		this.frameSaveScore.setVisible(false);
		// starts the main thread
		this.gameService.startGame();
		// start the sound
		this.gameService.getClip().start();
		this.gameService.getClip();
		// loop the sound
		this.gameService.getClip().loop(Clip.LOOP_CONTINUOUSLY);
		// refresh the panel
		this.panelGame.repaint();
		// create a game thread
		this.thread = new MainThread();
		// start the thread
		this.thread.start();
		// refresh the panel
		this.panelGame.repaint();
	}

	private class MainThread extends Thread {
		@Override
		public void run() {
			while (GameDTO.isStart()) {
				try {
					// wait for 800 ms for another drop
					Thread.sleep(800);
					// if paused, don't execute the main thread
					if (dto.isPause()) {
						continue;
					}
					// let the piece drop by one unit
					gameService.threadDrop();
					// refresh the panel
					panelGame.repaint();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			afterGameEvent();
		}
	}
}
