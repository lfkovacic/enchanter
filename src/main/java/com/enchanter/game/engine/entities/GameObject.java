package com.enchanter.game.engine.entities;
import static com.enchanter.game.consts.Consts.*;

public abstract class GameObject {
	
	private int id;
	
	private int cellX, cellY;
	private int cellWidth, cellHeight;
	private boolean passable=true;
	
	public GameObject(int _x, int _y, int _width, int _height) {
		setCellX(_x);
		setCellY(_y);
		setCellWidth(_width);
		setCellHeight(_height);		
	}
	public GameObject(int _x, int _y, int _width, int _height, boolean _passable) {
		setCellX(_x);
		setCellY(_y);
		setCellWidth(_width);
		setCellHeight(_height);	
		setPassable(_passable);
	}
	
	
	//Setters
	public void setX(int _x) {
		this.cellX = _x / CELL_SIZE;
	}
	public void setY(int _y) {
		this.cellY = _y / CELL_SIZE;
	}	
	public void setCellX(int _x) {
		this.cellX = _x;
	}
	public void setCellY(int _y) {
		this.cellY = _y;
	}
	protected void setCellWidth(int _width) {
		this.cellWidth = _width;
	}
	protected void setCellHeight(int _height) {
	    this.cellHeight = _height;
	}
	protected void setPassable(boolean _passable) {
		this.passable = _passable;
	}
	
	//Getters
	
	public int getX() {
		return this.cellX*CELL_SIZE;
	}
	public int getY() {
		return this.cellY*CELL_SIZE;
	}
	public int getWidth() {
		return this.cellWidth*CELL_SIZE;
	}
	public int getHeight() {
		return this.cellHeight*CELL_SIZE;
	}
	public boolean getPassable() {
		return this.passable;
	}
	public int getCellWidth() {
		return this.cellWidth;
	}
	public int getCellHeight() {
		return this.cellHeight;
	}	
	public int getCellX() {
		return cellX;
	}
	public int getCellY() {
		return cellY;
	}
	
	//Render
	
	public abstract void render();
	
	
	
	

}
