package com.tetris.config;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.dom4j.Element;

import com.tetris.util.StringCalculator;

public class FrameConfig {

	private final List<LayerConfig> layerConfig;
	private final int WIDTH_FRAME;
	private final int HEIGHT_FRAME;
	private final int LIFTER;
	private final int CORNER_SIZE_LAYER_GAME;
	private final int PIECE_SIZE;
	private final int SPACE_NEXT;
	private final int WIDTH_DIGIT;
	private final int HEIGHT_DIGIT;
	private final double SCALER;
	private final int PADDING;
	// button properties
	private final ButtonConfig buttonConfig;

	public FrameConfig(Element frame) throws NumberFormatException, ScriptException {
		/*
		 * pass in attributes form the xml file
		 */

		List<Element> layers = frame.elements("layer");
		layerConfig = new ArrayList<LayerConfig>();

		for (Element layer : layers) {
			LayerConfig lc = new LayerConfig(layer.attributeValue("className"),
					(int) (StringCalculator.cal(layer.attributeValue("x"))),
					(int) (StringCalculator.cal(layer.attributeValue("y"))),
					StringCalculator.cal((layer.attributeValue("w"))),
					StringCalculator.cal((layer.attributeValue("h"))));

			layerConfig.add(lc);

		}
		// init button properties
		buttonConfig = new ButtonConfig(frame.element("button"));

		this.WIDTH_FRAME = Integer.valueOf((StringCalculator.cal((frame.attributeValue("WIDTH_FRAME")))));
		this.HEIGHT_FRAME = StringCalculator.cal(frame.attributeValue("HEIGHT_FRAME"));
		this.LIFTER = Integer.valueOf(frame.attributeValue("LIFTER"));
		this.CORNER_SIZE_LAYER_GAME = Integer.valueOf(frame.attributeValue("CORNER_SIZE_LAYER_GAME"));
		this.PIECE_SIZE = StringCalculator.cal(frame.attributeValue("PIECE_SIZE"));
		this.SPACE_NEXT = StringCalculator.cal(frame.attributeValue("SPACE_NEXT"));
		this.WIDTH_DIGIT = StringCalculator.cal(frame.attributeValue("WIDTH_DIGIT"));
		this.HEIGHT_DIGIT = StringCalculator.cal(frame.attributeValue("HEIGHT_DIGIT"));
		this.SCALER = Double.valueOf(frame.attributeValue("SCALER"));
		this.PADDING = Integer.valueOf(frame.attributeValue("PADDING"));
	}

	public List<LayerConfig> getLayerConfig() {
		return layerConfig;
	}

	public double getSCALER() {
		return SCALER;
	}

	public int getWIDTH_FRAME() {
		return WIDTH_FRAME;
	}

	public int getHEIGHT_FRAME() {
		return HEIGHT_FRAME;
	}

	public int getLIFTER() {
		return LIFTER;
	}

	public int getCORNER_SIZE_LAYER_GAME() {
		return CORNER_SIZE_LAYER_GAME;
	}

	public int getPIECE_SIZE() {
		return PIECE_SIZE;
	}

	public int getSPACE_NEXT() {
		return SPACE_NEXT;
	}

	public int getWIDTH_DIGIT() {
		return WIDTH_DIGIT;
	}

	public int getHEIGHT_DIGIT() {
		return HEIGHT_DIGIT;
	}

	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}

	public int getPADDING() {
		return PADDING;
	}

}
