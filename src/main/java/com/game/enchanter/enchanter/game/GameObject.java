package com.game.enchanter.enchanter.game;

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
	
	private void setX(float _x) {
		this.x = _x;
	}
	private void setY(float _y) {
		this.y = _y;
	}
	private void setWidth(float _width) {
		this.width = _width;
	}
	private void setHeight(float _height) {
		this.width = _height;
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
