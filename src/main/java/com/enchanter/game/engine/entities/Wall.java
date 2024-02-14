package com.enchanter.game.engine.entities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.json.JSONObject;

import com.enchanter.game.engine.entities.interfaces.Renderable;

public class Wall extends Stator implements Renderable {

	private static final float[] COLOR = { 0.0f, 0.0f, 1.0f }; // blue

	public Wall(int id, int _x, int _y, int _width, int _height, boolean _passable) {
		super(id, _x, _y, _width, _height, _passable);
	}

	public Wall(JSONObject jo) {
		super(jo);
		super.setPassable(jo.getBoolean("passable"));
	}

	@Override
	public void render() {
		glColor3f(COLOR[0], COLOR[1], COLOR[2]);
		glBegin(GL_QUADS);
		glVertex2f(getX(), getY());
		glVertex2f(getX() + getWidth(), getY());
		glVertex2f(getX() + getWidth(), getY() + getHeight());
		glVertex2f(getX(), getY() + getHeight());
		glEnd();

	}

}
