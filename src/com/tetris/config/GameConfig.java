package com.tetris.config;

import javax.script.ScriptException;

import org.dom4j.Document;
import org.dom4j.DocumentException;

/*
 * 
 * this class if to read the xml configuration files
 * */

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GameConfig {

	private static DataConfig DATA_CONFIG = null;
	private static FrameConfig FRAME_CONFIG = null;
	private static SystemConfig SYSTEM_CONFIG = null;
	static {
		// create a reader
		SAXReader reader = new SAXReader();
		try {
			// get the root element of the document
			Document doc = reader.read("data/cfg.xml");
			// to retrieve the root element
			Element game = doc.getRootElement();
			// create data configuration object
			DATA_CONFIG = new DataConfig(game.element("data"));

			// create frame configuration object
			FRAME_CONFIG = new FrameConfig(game.element("frame"));
			// create system configuration object
			SYSTEM_CONFIG = new SystemConfig(game.element("system"));

		} catch (DocumentException | NumberFormatException | ScriptException e) {
			e.printStackTrace();
		}

	}

	private GameConfig() {
	}

	// allow public access to DATA_CONFIG
	public static DataConfig getDATA_CONFIG() {
		return DATA_CONFIG;
	}

	// allow public access to FRAME_CONFIG
	public static FrameConfig getFRAME_CONFIG() {
		return FRAME_CONFIG;
	}

	// allow public access to SYSTEM_CONFIG
	public static SystemConfig getSYSTEM_CONFIG() {
		return SYSTEM_CONFIG;
	}

}
