package com.tetris.config;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.dom4j.Element;

public class SystemConfig {

	private final int X_START;
	private final int X_END;
	private final int Y_START;
	private final int Y_END;
	private final int GAP;
	private final List<Point[]> TYPE_CONFIG;
	public static List<Integer> UNSHIFTABLES;

	public SystemConfig(Element system) throws ScriptException {
		// to retrieve the boundary of the game panel
		this.X_START = Integer.parseInt(system.attributeValue("X_START"));
		// this.X_END = Integer.parseInt(system.attributeValue("X_END"));
		this.X_END = Integer.parseInt(system.attributeValue("X_END"));
		this.Y_START = Integer.parseInt(system.attributeValue("Y_START"));
		this.Y_END = Integer.parseInt(system.attributeValue("Y_END"));
		// to retrieve the width of the frame in game panel
		this.GAP = Integer.parseInt(system.attributeValue("GAP"));
		// create a list of type piece
		List<Element> rects = system.elements("rect");
		TYPE_CONFIG = new ArrayList<Point[]>(rects.size());
		for (Element rect : rects) {
			List<Element> pointConfig = rect.elements("point");
			Point[] p = new Point[pointConfig.size()];
			for (int i = 0; i < p.length; i++) {
				int x = Integer.parseInt(pointConfig.get(i).attributeValue("x"));
				int y = Integer.parseInt(pointConfig.get(i).attributeValue("y"));
				p[i] = new Point(x, y);
			}
			TYPE_CONFIG.add(p);

		}
		// add all unshiftable pieces to the list
		SystemConfig.UNSHIFTABLES = SystemConfig.findUnshiftables(system);

	}

	// unshiftables returns a list of type codes that are unshiftable
	public static List<Integer> findUnshiftables(Element system) {
		List<Integer> unshiftableList = new ArrayList<Integer>();
		for (int i = 0; i < system.elements("rect").size(); i++) {
			if (Boolean.valueOf(system.elements("rect").get(i).attributeValue("shiftable"))) {
				unshiftableList.add(i);
			}
		}
		return unshiftableList;
	}

	public int getX_START() {
		return X_START;
	}

	public int getX_END() {
		return X_END;
	}

	public int getY_START() {
		return Y_START;
	}

	public int getY_END() {
		return Y_END;
	}

	public List<Point[]> getTYPE_CONFIG() {
		return TYPE_CONFIG;
	}

	public int getGAP() {
		return GAP;
	}

	public static List<Integer> getUNSHIFTABLES() {
		return UNSHIFTABLES;
	}

}
