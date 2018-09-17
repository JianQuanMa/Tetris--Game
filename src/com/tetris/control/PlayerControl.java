package com.tetris.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.tetris.dto.GameDTO;

public class PlayerControl extends KeyAdapter {

	private GameControl gameControl;
	private GameDTO dto = null;

	public PlayerControl(GameControl gameControl, GameDTO dto) {
		this.gameControl = gameControl;
		this.dto = dto;
	}

	// @Override
	// public void keyPressed(KeyEvent e) {
	// // to decide the gameBehavior,up, down , left , or right based on inputs
	// this.gameControl.actionByKeyCode(e.getKeyCode());
	// }
	@Override
	public void keyPressed(KeyEvent e) {
		// to decide the game behavior, up, down , left , or right based on user
		// input

		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			this.gameControl.keyFuncLeft();
			if (this.dto.isPause()) {
				return;
			}
			break;
		case KeyEvent.VK_UP:
			this.gameControl.keyUp();
			break;
		case KeyEvent.VK_DOWN:
			this.gameControl.keyDown();
			break;
		case KeyEvent.VK_LEFT:
			this.gameControl.keyLeft();
			break;
		case KeyEvent.VK_RIGHT:
			this.gameControl.keyRight();
			break;
		case KeyEvent.VK_U:
			this.gameControl.keyTest();
			break;
		case KeyEvent.VK_S:
			this.gameControl.keyFuncDown();
			break;
		case KeyEvent.VK_W:
			this.gameControl.keyFuncUp();
			break;
		case KeyEvent.VK_D:
			this.gameControl.keyFuncRight();
			break;
		default:
		}
	}

}
