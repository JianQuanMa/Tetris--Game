package com.tetris.dao;

import java.util.List;

import com.tetris.dto.PlayerDTO;

//not yet implemented
public class DataBase implements Data {

	// create access information for the database
	// private final String bdUrl;
	// private final String bdUser;
	// private final String bdPassword;
	//
	// public DataBase(HashMap<String, String> param) {
	// this.bdUrl = param.get("bdUrl");
	// this.bdUser = param.get("bdUser");
	// this.bdPassword = param.get("bdPassword");
	// try {
	// Class.forName(param.get("drive"));
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	public List<PlayerDTO> loadPlayData() {
		return null;
	}

	@Override
	public void savePlayData(List<PlayerDTO> playerData) {
	}

	@Override
	public void savePlayData(PlayerDTO playerData) {

	}

}
