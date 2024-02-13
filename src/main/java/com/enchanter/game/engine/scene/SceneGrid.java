package com.enchanter.game.engine.scene;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.entities.Grid;
import com.enchanter.game.engine.entities.Obstacle;

public class SceneGrid extends Grid{
	List<Obstacle> obstacleList;
	public boolean collisionArray[][];

	public SceneGrid(int _gridWidth, int _gridHeight, int _cellWidth, int _cellHeight) throws IOException {
		super(_gridWidth, _gridHeight, _cellWidth, _cellHeight);

		collisionArray = new boolean[getCellsX()][getCellsY()];
		resetCollisionArray();

		obstacleList = new ArrayList<Obstacle>();

	}

	public void addObstacle(Obstacle obj) {
		this.obstacleList.add(obj);
		populateCollisionArray();
	}

	public void setObstacles(List<Obstacle> obstacleList) {
		for (Obstacle obstacle : obstacleList) {
			addObstacle(obstacle);
		}
	}

	public List<Obstacle> getObstacles() {
		return this.obstacleList;
	}

	public void resetCollisionArray() {
		for (int i = 0; i < collisionArray.length; i++) {
			for (int j = 0; j < collisionArray[i].length; j++) {
				collisionArray[i][j] = false;
			}
		}
	}

	public void populateCollisionArray() {
		for (GameObject object : obstacleList) {
			if (!object.getPassable()) {

				// X and Y are cell coordinates

				int x0 = (int) object.getCellX();
				int y0 = (int) object.getCellY();

				int deltaX = (int) object.getCellWidth();
				int deltaY = (int) object.getCellHeight();

				for (int i = x0; i < x0 + deltaX; i++) {
					for (int j = y0; j < y0 + deltaY; j++) {
						collisionArray[i][j] = true;
					}
				}
			}
		}
	}

	public boolean checkCollision(int x, int y) {
		System.out.println("X: " + x);
		System.out.println("Y: " + y);
		System.out.println("CellsX: " + getCellsX());
		System.out.println("CellsY: " + getCellsY());
		boolean statement = (x >= 0 && x < getCellsX() && y >= 0 && y < getCellsY());
		System.out.println("Statement: " + statement);
		if (statement) {
			return collisionArray[x][y];
		} else
			return true;
	}
}
