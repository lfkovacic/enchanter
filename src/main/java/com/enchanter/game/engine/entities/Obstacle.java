package com.enchanter.game.engine.entities;

public abstract class Obstacle extends GameObject {

	public Obstacle(int _x, int _y, int _width, int _height, boolean _passable) {
		super(_x, _y, _width, _height, _passable);
	}

}
