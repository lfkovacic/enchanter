package com.enchanter.game.engine.entities;
import static org.lwjgl.opengl.GL11.*;

import com.enchanter.game.engine.entities.interfaces.Renderable;


public class PlayerObject extends GameObject implements Renderable {

    private static final float[] COLOR = { 1.0f, 0.0f, 0.0f }; // red

    public PlayerObject(int x, int y) {    	
        super(x, y, 1, 2);
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