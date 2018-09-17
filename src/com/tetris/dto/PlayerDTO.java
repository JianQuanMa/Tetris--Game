package com.tetris.dto;

import java.io.Serializable;

public class PlayerDTO implements Comparable<PlayerDTO>, Serializable {

	private static final long serialVersionUID = 1L;
	private String playerName;
	private int playerScore;

	public PlayerDTO(String playerName, int playerScore) {
		this.playerName = playerName;
		this.playerScore = playerScore;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	@Override
	public int compareTo(PlayerDTO player) {

		return player.playerScore - this.playerScore;
	}

}
