package com.tetris.dao;

import java.util.List;

import com.tetris.dto.PlayerDTO;

/*
 * data persistence layer implementation
 */
public interface Data {

	// load saved data
	public List<PlayerDTO> loadPlayData();

	// save the entire list of players
	public void savePlayData(List<PlayerDTO> playersData);

	// check if the new score is top five, if so, write it in
	public void savePlayData(PlayerDTO playerData);

}
