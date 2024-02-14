package com.enchanter.game.engine.entities;

import org.json.JSONObject;

public abstract class Stator extends GameObject {

	public Stator(int id, int _x, int _y, int _width, int _height, boolean _passable) {
		super(id, _x, _y, _width, _height, _passable);
	}

	public Stator(JSONObject jo) {
		super(jo);
	}
}
