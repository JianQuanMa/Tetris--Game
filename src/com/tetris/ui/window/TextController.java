package com.tetris.ui.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class TextController extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int keyCode;
	private final String METHOD_NAME;
	final int x, y, w, h;

	public TextController(int x, int y, int w, int h, String methodName) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.METHOD_NAME = methodName;
		// retrieve the string value of the key code
		this.setText(KeyEvent.getKeyText(keyCode));
		// set the bounds
		this.setBounds(x, y, w, h);
		// add key listener
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// set key code
				setKeyCode(e.getKeyCode());
				// set text equal to text of the input
				setText(KeyEvent.getKeyText(e.getKeyCode()));
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
		this.setText(KeyEvent.getKeyText(this.keyCode));
	}

	public String getMethodName() {
		return METHOD_NAME;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
