package com.enchanter.game.engine.entities;

import org.json.JSONObject;

public abstract class Obstacle extends GameObject {

	public Obstacle(int id, int _x, int _y, int _width, int _height, boolean _passable) {
		super(id, _x, _y, _width, _height, _passable);
	}

	public Obstacle(JSONObject jo) {
		super(jo);
		super.setPassable(jo.getBoolean("passable"));
	}

}
