package com.tetris.config;

import javax.script.ScriptException;

import org.dom4j.Element;

import com.tetris.util.StringCalculator;

public class ButtonConfig {

	private final int buttonW;
	private final int buttonH;

	private final int startX;
	private final int startY;
	private final int CCX;
	private final int CCY;

	public ButtonConfig(Element button) throws ScriptException {
		this.buttonW = (StringCalculator.cal(button.attributeValue("w")));
		this.buttonH = (StringCalculator.cal(button.attributeValue("h")));
		this.startX = StringCalculator.cal(button.element("start").attributeValue("x"));
		this.startY = StringCalculator.cal(button.element("start").attributeValue("y"));
		this.CCX = StringCalculator.cal(button.element("pause").attributeValue("x"));
		this.CCY = StringCalculator.cal(button.element("pause").attributeValue("y"));
	}

	public int getButtonW() {
		return buttonW;
	}

	public int getButtonH() {
		return buttonH;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getCCX() {
		return CCX;
	}

	public int getCCY() {
		return CCY;
	}

}
