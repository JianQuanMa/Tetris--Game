package com.tetris.config;

import javax.script.ScriptException;

import org.dom4j.Element;

import com.tetris.util.StringCalculator;

public class DataConfig {

	private final int MAX_DATA_ROWS;
	private final int HEIGHT_BAR;

	private final DataInterfaceConfig dataA;
	private final DataInterfaceConfig dataB;

	public DataConfig(Element data) throws ScriptException {

		this.dataA = new DataInterfaceConfig(data.element("dataA"));
		this.dataB = new DataInterfaceConfig(data.element("dataB"));
		this.MAX_DATA_ROWS = Integer.valueOf(data.attributeValue("MAX_DATA_ROWS"));
		this.HEIGHT_BAR = StringCalculator.cal(data.attributeValue("HEIGHT_BAR"));

	}

	public DataInterfaceConfig getDataA() {
		return dataA;
	}

	public DataInterfaceConfig getDataB() {
		return dataB;
	}

	public int getMAX_DATA_ROWS() {
		return MAX_DATA_ROWS;
	}

	public int getHEIGHT_BAR() {
		return HEIGHT_BAR;
	}

}
