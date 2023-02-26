package com.game.enchanter.engine;

import com.game.enchanter.entities.GameObject;
import static com.game.enchanter.consts.Consts.*;

public class Mejjiq {
	
	Grid grid;
	
	public Mejjiq() {
		grid = new Grid(SCREEN_WIDTH, SCREEN_HEIGHT, CELL_SIZE, CELL_SIZE);
	}
	
	public void moveTo(GameObject obj, int x, int y) {
		obj.setCellX(x);
		obj.setCellY(y);
	}
	
	public boolean checkCollision(Grid grid, float x, float y) {
		return grid.checkCollision((int)x, (int)y);
	}
		
}
