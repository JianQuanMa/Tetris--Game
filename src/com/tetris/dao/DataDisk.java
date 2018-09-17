package com.tetris.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.tetris.config.GameConfig;
import com.tetris.dto.PlayerDTO;

/*
 * this class provides save and load from the save file to display leader board
 */
public class DataDisk implements Data {

	// the save file path
	private String SAVE_PATH;
	// create a PlayerDTO object
	private List<PlayerDTO> playersDTO;

	public DataDisk(HashMap<String, String> param) {
		this.SAVE_PATH = param.get("path");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerDTO> loadPlayData() {
		// create an ObjectInput stream object
		ObjectInputStream ois = null;
		// create a List of PlayerDTO objects
		List<PlayerDTO> playersDTO = null;
		FileInputStream saveFile = null;
		try {
			saveFile = new FileInputStream(SAVE_PATH);
			// set save file path to the ObjectOutputStream object
			ois = new ObjectInputStream(saveFile);

			try {
				// read data from the object
				playersDTO = (List<PlayerDTO>) ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				// close streaming
				ois.close();
				saveFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return playersDTO;
	}

	/*
	 * save a list of players in the save file
	 * 
	 */
	public void savePlayData(List<PlayerDTO> playersData) {
		// create an ObjectOutputSTream object
		ObjectOutputStream oos = null;
		FileOutputStream saveFile = null;
		try {
			// create a FileOutputStream object
			saveFile = (new FileOutputStream(this.SAVE_PATH));
			// set the save file path to the ObjectOutputSTream object
			oos = new ObjectOutputStream(saveFile);
			// write data to the file
			oos.writeObject(playersData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// close streaming
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * save one player's data to the disk, insert the player based on his score
	 * and rank
	 */
	public void savePlayData(PlayerDTO newPlayer) {

		this.playersDTO = this.loadPlayData();
		// retrieve the number of max number of players stored in the file
		int maxRow = GameConfig.getDATA_CONFIG().getMAX_DATA_ROWS();
		// if there are empty space, just add the new player in
		if (this.playersDTO == null || this.playersDTO.size() < maxRow) {
			this.playersDTO.add(newPlayer);
			Collections.sort(playersDTO);

			this.savePlayData(this.playersDTO);
			return;
		}
		// if the list is full, insert the player in
		outer: for (int i = 0; i < maxRow; i++) {
			/*
			 * if the current player's score is lower than the new score, loop
			 * through the rest of the list, add the new player in and push
			 * every one below him down
			 */
			if (newPlayer.getPlayerScore() > this.playersDTO.get(i).getPlayerScore()) {
				PlayerDTO temp;
				for (; i < maxRow; i++) {
					temp = this.playersDTO.get(i);
					this.playersDTO.set(i, newPlayer);
					newPlayer = temp;
				}
				break outer;
			}
		}
		// finally save the new list to the file
		this.savePlayData(this.playersDTO);
	}

	// clear local record
	public void clearLocalRecord() {
		this.playersDTO = new ArrayList<>();
		for (int i = 0; i < GameConfig.getDATA_CONFIG().getMAX_DATA_ROWS(); i++) {
			this.playersDTO.add(new PlayerDTO("null", 0));
		}
		this.savePlayData(playersDTO);
	}

}
