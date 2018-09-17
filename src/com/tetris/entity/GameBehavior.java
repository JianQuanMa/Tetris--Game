package com.tetris.entity;

import java.awt.Point;
import java.util.List;

import com.tetris.config.GameConfig;
import com.tetris.config.SystemConfig;

public class GameBehavior {
	// the number of the piece
	private int typeCode;
	// the array of points that the piece made up
	private Point[] behaviorPoints = null;
	// MIN_X, MAX_X, MIN_Y, MAX_Y
	private static int MIN_X = GameConfig.getSYSTEM_CONFIG().getX_START();
	private static int MAX_X = GameConfig.getSYSTEM_CONFIG().getX_END();
	private static int MIN_Y = GameConfig.getSYSTEM_CONFIG().getY_START();
	private static int MAX_Y = GameConfig.getSYSTEM_CONFIG().getY_END();

	private static List<Point[]> BEHAVIOR_CONFIG = GameConfig.getSYSTEM_CONFIG().getTYPE_CONFIG();

	public GameBehavior(int typeCode) {
		// Initializing GameBahavior
		this.init(typeCode);

	}

	public void init(int typeCode) {
		// the first point is the centre point of the piece, the piece shifts
		// around it
		/*
		 * to get a piece randomly from the list of pieces
		 */

		this.typeCode = typeCode;
		Point[] points = BEHAVIOR_CONFIG.get(typeCode);
		behaviorPoints = new Point[points.length];
		/*
		 * loop through the copy of the piece, and give its x and y coords to
		 * the new points of the newly generated piece
		 * 
		 */
		for (int i = 0; i < points.length; i++) {
			behaviorPoints[i] = new Point(points[i].x, points[i].y);
		}
	}

	// this returns the array of points that makeup the newly generated piece
	public Point[] getBehaviorPoint() {
		return behaviorPoints;
	}
	/*
	 * move method decides the movement of the newly generated piece before it
	 * lands
	 */

	public boolean isMovable(int moveX, int moveY, boolean[][] map) {
		// first loop to determine if all points are movable
		for (int i = 0; i < behaviorPoints.length; i++) {
			int afterX = behaviorPoints[i].x + moveX;
			int afterY = behaviorPoints[i].y + moveY;
			if (this.isOutsideMap(afterX, afterY, map)) {
				return false;
			}
		}
		// if movable, second loop to move all points
		for (int i = 0; i < behaviorPoints.length; i++) {
			behaviorPoints[i].x += moveX;
			behaviorPoints[i].y += moveY;

		}
		return true;

	}

	/*
	 * this method controls how the pieces shift when the player hits the up key
	 */
	public void shift(boolean[][] map) {
		if (!SystemConfig.UNSHIFTABLES.contains(this.getTypeCode())) {
			return;
		}

		/*
		 * first loop to determine if all points are within the frame and not
		 * overlay other points
		 */
		for (int i = 0; i < behaviorPoints.length; i++) {
			int newX = behaviorPoints[0].x + behaviorPoints[0].y - behaviorPoints[i].y;
			int newY = behaviorPoints[0].y - behaviorPoints[0].x + behaviorPoints[i].x;
			if (this.isOutsideMap(newX, newY, map)) {
				return;
			}
		}
		/*
		 * if shiftable, second loop to change x and y coordinates of each point
		 * to the new x and y.
		 */
		for (int j = 0; j < behaviorPoints.length; j++) {
			int newX = behaviorPoints[0].x + behaviorPoints[0].y - behaviorPoints[j].y;
			int newY = behaviorPoints[0].y - behaviorPoints[0].x + behaviorPoints[j].x;
			behaviorPoints[j].x = newX;
			behaviorPoints[j].y = newY;
		}

	}

	/*
	 * to determine if the new position or new form of the piece goes out of the
	 * boundary
	 */
	private boolean isOutsideMap(int x, int y, boolean[][] map) {
		return x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y || map[x][y];
	}

	/*
	 * to obtain the number of the piece
	 */
	public int getTypeCode() {
		return typeCode;
	}

}
