package com.game.enchanter.engine;

import java.util.ArrayList;
import java.util.List;

import com.game.enchanter.entities.GameObject;

public class Grid {
	List<GameObject> objectList;
	public boolean collisionArray[][];
	private int gridWidth, gridHeight;
	private int cellWidth, cellHeight;
	private int cellsX, cellsY;
	
	public Grid(int _gridWidth, int _gridHeight, int _cellWidth, int _cellHeight) {
		
		setGridWidth(_gridWidth);
		setGridHeight(_gridHeight);
		setCellWidth(_cellWidth);
		setCellHeight(_cellHeight);
		
		setCellsX(_gridWidth/_cellWidth);
		setCellsY(_gridHeight/_cellHeight);
		
		collisionArray = new boolean[getCellsX()][getCellsY()];
		resetCollisionArray();
		
		objectList = new ArrayList<GameObject>();
		
				
	}
	
	public void addObject(GameObject obj) {
		this.objectList.add(obj);
		populateCollisionArray();
	}
	
	public void setObjects(List<GameObject> _objectList) {
		this.objectList = _objectList;
	}
	
	public List<GameObject> getObjects(){
		return this.objectList;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public int getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}

	public int getCellHeight() {
		return cellHeight;
	}

	public void setCellHeight(int cellHeight) {
		this.cellHeight = cellHeight;
	}

	public int getCellsX() {
		return cellsX;
	}

	public void setCellsX(int cellsX) {
		this.cellsX = cellsX;
	}

	public int getCellsY() {
		return cellsY;
	}

	public void setCellsY(int cellsY) {
		this.cellsY = cellsY;
	}
	
	public void resetCollisionArray() {
		for (int i = 0; i < collisionArray.length; i++) {
		    for (int j = 0; j < collisionArray[i].length; j++) {
		        collisionArray[i][j] = false;
		    }
		}
	}
	
	public void populateCollisionArray() {
		for (GameObject object : objectList) {
			if (!object.getPassable()) {
				
				//X and Y are cell coordinates
			
				int x0 = (int) object.getCellX();
				int y0 = (int) object.getCellY();
				
				int deltaX = (int) object.getCellWidth();
				int deltaY = (int) object.getCellHeight();
			
				for (int i = x0; i<x0+deltaX; i++) {
					for (int j = y0; j<y0+deltaY; j++) {
						collisionArray[i][j] = true;
					}
				}
			}
			
		}
	}
	
	public boolean checkCollision(int x, int y) {
		return collisionArray[x][y];
	}

}
