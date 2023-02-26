package com.game.enchanter.entities;
import java.awt.event.KeyEvent;
import static org.lwjgl.opengl.GL11.*;
import com.game.enchanter.engine.Controller;
import com.game.enchanter.engine.interfaces.Movable;
import com.game.enchanter.entities.interfaces.Renderable;

public class PlayerObject extends GameObject implements Renderable, Movable {

    private static final float[] COLOR = { 1.0f, 0.0f, 0.0f }; // red
    private static final int MOVE_AMOUNT = 16;

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
		System.out.println("Y: "+getY());
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