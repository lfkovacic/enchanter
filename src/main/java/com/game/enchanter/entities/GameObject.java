package com.game.enchanter.entities;

public abstract class GameObject {
	private float x, y;
	private float width, height;
	
	public GameObject(float _x, float _y, float _width, float _height) {
		setX(_x);
		setY(_y);
		setWidth(_width);
		setHeight(_height);		
	}
	
	//Setters
	
	protected void setX(float _x) {
		this.x = _x;
	}
	protected void setY(float _y) {
		this.y = _y;
	}
	protected void setWidth(float _width) {
		this.width = _width;
	}
	protected void setHeight(float _height) {
	    this.height = _height;
	}
	
	//Getters
	
	public float getX() {
		return this.x;
	}
	public float getY() {
		return this.y;
	}
	public float getWidth() {
		return this.width;
	}
	public float getHeight() {
		return this.height;
	}
	
	//Render
	
	public abstract void render();

}
