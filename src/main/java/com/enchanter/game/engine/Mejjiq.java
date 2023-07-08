package com.enchanter.game.engine;

import static com.enchanter.game.consts.Consts.*;
import static org.lwjgl.glfw.GLFW.*;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.entities.interfaces.Renderable;
import com.enchanter.game.engine.events.EventManager;
import com.enchanter.game.engine.graphics.Renderer;
import com.enchanter.game.engine.interfaces.CollisionCallback;
import com.enchanter.game.engine.interfaces.MovementCallback;
import com.enchanter.game.engine.resources.ResourceManager;
import com.enchanter.game.engine.scene.Grid;
import com.enchanter.game.engine.scene.Scene;
import com.enchanter.game.engine.scene.SceneID;
import com.enchanter.game.engine.scene.SceneManager;
import com.enchanter.game.consts.Consts;
import com.enchanter.game.engine.core.WindowManager;

public class Mejjiq {

	public Grid grid;
	public Renderer<Renderable> renderer;
	private WindowManager windowManager;
	private SceneManager sceneManager;
	private EventManager eventManager;
	private ResourceManager resourceManager;

	public Mejjiq() {
		grid = new Grid(SCREEN_WIDTH, SCREEN_HEIGHT, CELL_SIZE, CELL_SIZE);
		renderer = new Renderer<Renderable>(3);
		windowManager = new WindowManager();
		resourceManager = new ResourceManager();
	}

	public void init() {
		loadGameResources();

		System.out.println("GLFF PRESS: " + GLFW_PRESS);
		System.out.println("GLFF REPEAT: " + GLFW_REPEAT);
		System.out.println("GLFF RELEASE: " + GLFW_RELEASE);

		// Scene Manager
		this.sceneManager = new SceneManager();
		Scene startScene = sceneManager.getScene(SceneID.START_SCENE.getID());

		// Event Manager
		eventManager = new EventManager();
		eventManager.setScene(startScene);
		eventManager.loadHardcodedBindings(collisionCallback, movementCallback);

		this.grid.setObstacles(startScene.getSceneStators());

		this.renderer.addRenderable((Renderable) startScene.getObjectById(0), 1);
		this.renderer.addRenderable((Renderable) startScene.getStatorById(1), 0);

		windowManager.createWindow(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
		windowManager.setKeyBindings(eventManager.getBindings());
	}

	public void loop() {
		windowManager.loop(renderer);
	}

	public void moveTo(GameObject obj, int x, int y) {
		obj.setCellX(x);
		obj.setCellY(y);
	}

	MovementCallback movementCallback = (obj, dx, dy) -> {
		moveTo(obj, obj.getCellX() + dx, obj.getCellY() + dy);
	};
	CollisionCallback collisionCallback = (x, y) -> {
		if (x < 0 || y < 0)
			return true;
		else
			return grid.checkCollision(x, y);
	};
	private void loadGameResources() {
        // Allocate resources based on project needs
        resourceManager.addResource("Memory", 2048); // Allocate 2048 MB of memory
        resourceManager.addResource("Textures", 100); // Allocate 100 textures
        resourceManager.addResource("AudioFiles", 50); // Allocate 50 audio files
        resourceManager.addResource("Shaders", 10); // Allocate 10 shaders
    }

	public long getWindow() {
		return windowManager.getWindow();
	}

}
