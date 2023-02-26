package com.game.enchanter.entities;
import static org.lwjgl.opengl.GL11.*;

import com.game.enchanter.entities.interfaces.Movable;
import com.game.enchanter.entities.interfaces.Renderable;
import static com.game.enchanter.consts.Consts.*;

public class PlayerObject extends GameObject implements Renderable, Movable {

    private static final float[] COLOR = { 1.0f, 0.0f, 0.0f }; // red
    private static final int MOVE_AMOUNT = CELL_SIZE;

    public PlayerObject(float x, float y) {
    	
        super(x, y, 32, 64);
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

	@Override
	public void moveUp() {
		setY(getY()+MOVE_AMOUNT);		
	}

	@Override
	public void moveDown() {
		setY(getY()-MOVE_AMOUNT);
	}

	@Override
	public void moveLeft() {
		setX(getX()-MOVE_AMOUNT);
	}

	@Override
	public void moveRight() {
		setX(getX()+MOVE_AMOUNT);
	}

}