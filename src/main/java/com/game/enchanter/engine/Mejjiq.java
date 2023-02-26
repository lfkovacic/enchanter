package com.game.enchanter.engine;

import com.game.enchanter.entities.GameObject;
import com.game.enchanter.entities.interfaces.Renderable;
import com.game.enchanter.graphics.Renderer;

import static com.game.enchanter.consts.Consts.*;

public class Mejjiq {
	
	public Grid grid;
	public Renderer<Renderable> renderer;
	
	public Mejjiq() {
		grid = new Grid(SCREEN_WIDTH, SCREEN_HEIGHT, CELL_SIZE, CELL_SIZE);
		renderer = new Renderer<Renderable>(3);
	}
	
	public void init() {
	}
	
	public void render() {
		renderer.render();
	}
	
	
	public void moveTo(GameObject obj, int x, int y) {
		obj.setCellX(x);
		obj.setCellY(y);
	}
	public void moveBy(GameObject obj, int dx, int dy) {
		moveTo(obj, obj.getCellX()+dx, obj.getCellY()+dy);
	}
	
	public boolean checkCollision(int x, int y) {
		if (x<0||y<0) return true;
		else return grid.checkCollision((int)x, (int)y);
	}
		
}
