package com.tetris.service;

import javax.sound.sampled.Clip;

public interface GameServiceInterface {
	// up key
	public void keyUp();

	// down key
	public boolean keyDown();

	// left key
	public void keyLeft();

	// right key
	public void keyRight();

	public void keyTest();

	// func up key
	public void keyFuncUp();

	// func down key
	public void keyFuncDown();

	// func left key
	public void keyFuncLeft();

	// func right key
	public void keyFuncRight();

	// main thread
	public void startGame();

	// main drop thread controls if the piece should auto drop
	public void threadDrop();

	// toggle sound
	public void toggleSound();

	// get clip
	public Clip getClip();

}
