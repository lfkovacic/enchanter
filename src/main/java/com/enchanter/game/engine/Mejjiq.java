package com.enchanter.game.engine;

import static com.enchanter.game.consts.Consts.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFW;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.entities.PlayerObject;
import com.enchanter.game.engine.entities.Wall;
import com.enchanter.game.engine.entities.interfaces.Renderable;
import com.enchanter.game.engine.graphics.Renderer;
import com.enchanter.game.engine.input.KeyBindings;
import com.enchanter.game.engine.input.KeyInput;
import com.enchanter.game.engine.scene.Grid;
import com.enchanter.game.engine.scene.Scene;
import com.enchanter.game.consts.Consts;
import com.enchanter.game.engine.core.WindowManager;

public class Mejjiq {

	public Grid grid;
	public Renderer<Renderable> renderer;
	private WindowManager windowManager;

	// Key bindings

	KeyBindings keyBindings = new KeyBindings();

	public Mejjiq() {
		grid = new Grid(SCREEN_WIDTH, SCREEN_HEIGHT, CELL_SIZE, CELL_SIZE);
		renderer = new Renderer<Renderable>(3);
		windowManager = new WindowManager();
	}

	public void init() {

		System.out.println("GLFF PRESS: " + GLFW_PRESS);
		System.out.println("GLFF REPEAT: " + GLFW_REPEAT);
		System.out.println("GLFF RELEASE: " + GLFW_RELEASE);

		// Scenes
		PlayerObject player = new PlayerObject(0, 0);
		Wall wall = new Wall(4, 5, 10, 6, false);

		Scene startScene = new Scene();
		startScene.addObject(player);
		startScene.addObstacle(wall);

		this.grid.setObstacles(startScene.getSceneStators());

		this.renderer.addRenderable(player, 1);
		this.renderer.addRenderable(wall, 0);

		// Setting the bindings
		KeyInput moveUp = new KeyInput(GLFW.GLFW_KEY_W, GLFW.GLFW_REPEAT);
		KeyInput moveDown = new KeyInput(GLFW.GLFW_KEY_S, GLFW.GLFW_REPEAT);
		KeyInput moveLeft = new KeyInput(GLFW.GLFW_KEY_A, GLFW.GLFW_REPEAT);
		KeyInput moveRight = new KeyInput(GLFW.GLFW_KEY_D, GLFW.GLFW_REPEAT);

		keyBindings.add(moveUp, () -> {
			if (!checkCollision(player.getCellX(), player.getCellY() + 1)) {
				moveBy(player, 0, 1);
			}
		});

		keyBindings.add(moveDown, () -> {
			if (!checkCollision(player.getCellX(), player.getCellY() - 1)) {
				moveBy(player, 0, -1);
			}
		});

		keyBindings.add(moveLeft, () -> {
			if (!checkCollision(player.getCellX() - 1, player.getCellY())) {
				moveBy(player, -1, 0);
			}
		});

		keyBindings.add(moveRight, () -> {
			if (!checkCollision(player.getCellX() + 1, player.getCellY())) {
				moveBy(player, 1, 0);
			}
		});
		windowManager.createWindow(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
		windowManager.setKeyBindings(keyBindings);
	}

	public void loop() {
		windowManager.loop(renderer);
	}

	public void moveTo(GameObject obj, int x, int y) {
		obj.setCellX(x);
		obj.setCellY(y);
	}

	public void moveBy(GameObject obj, int dx, int dy) {
		moveTo(obj, obj.getCellX() + dx, obj.getCellY() + dy);
	}

	public boolean checkCollision(int x, int y) {
		if (x < 0 || y < 0)
			return true;
		else
			return grid.checkCollision((int) x, (int) y);
	}

	public long getWindow() {
		return windowManager.getWindow();
	}

}
