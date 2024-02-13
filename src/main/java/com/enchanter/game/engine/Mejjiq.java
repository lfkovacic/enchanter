package com.enchanter.game.engine;

import static com.enchanter.game.consts.Consts.*;
import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.entities.interfaces.Renderable;
import com.enchanter.game.engine.events.EventManager;
import com.enchanter.game.engine.graphics.Renderer;
import com.enchanter.game.engine.interfaces.CollisionCallback;
import com.enchanter.game.engine.interfaces.MovementCallback;
import com.enchanter.game.engine.resources.ResourceManager;
import com.enchanter.game.engine.scene.SceneGrid;
import com.enchanter.game.engine.scene.Scene;
import com.enchanter.game.engine.scene.SceneID;
import com.enchanter.game.engine.scene.SceneManager;
import com.enchanter.game.consts.Consts;
import com.enchanter.game.engine.core.WindowManager;

/**
 * /**
 * The Mejjiq class represents the main class of the game. It initializes and
 * manages various components of the game, such as the scene grid, renderer,
 * window manager, event manager, and resource manager. It also provides methods
 * for initializing the game, running the game loop, and handling movement and
 * collision callbacks.
 * 
 * The Mejjiq class is responsible for loading game resources, creating the game
 * window, setting up key bindings, and rendering game objects. It uses the
 * SceneGrid class to manage the grid-based scene and collision detection. The
 * Renderer class is used to render game objects on the screen. The
 * WindowManager class is used to create and manage the game window. The
 * SceneManager class is used to manage different scenes in the game. The
 * EventManager class is used to handle game events and key bindings. The
 * ResourceManager class is used to manage game resources such as memory,
 * textures, audio files, and shaders.
 * 
 * The Mejjiq class provides a method to move game objects and defines movement
 * and collision callbacks. The movement callback is responsible for updating
 * the position of game objects based on the specified movement vector. The
 * collision callback is responsible for checking if a given position is
 * colliding with any obstacles in the scene grid.
 * 
 * The Mejjiq class follows the singleton design pattern, ensuring that only one
 * instance of the class is created throughout the game.
 */
public class Mejjiq {

	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;
	private static final int CELL_SIZE = 32;

	private SceneGrid grid;
	private Renderer<Renderable> renderer;
	private WindowManager windowManager;
	private SceneManager sceneManager;
	private EventManager eventManager;
	private ResourceManager resourceManager;

	private static Mejjiq instance;

	private Mejjiq() {
		try {
			grid = new SceneGrid(SCREEN_WIDTH, SCREEN_HEIGHT, CELL_SIZE, CELL_SIZE);
			renderer = new Renderer<>(3);
			windowManager = new WindowManager();
			resourceManager = new ResourceManager();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Initializes the game by loading game resources, setting up key bindings, and
	 * rendering game objects.
	 */
	public void init() {
		loadGameResources();
	
		System.out.println("GLFF PRESS: " + GLFW_PRESS);
		System.out.println("GLFF REPEAT: " + GLFW_REPEAT);
		System.out.println("GLFF RELEASE: " + GLFW_RELEASE);
	
		sceneManager = new SceneManager();
	
		// Load scene from file using ResourceManager
		try {
			Scene startScene = ResourceManager.loadScene("src\\main\\resources\\scenes\\startScene.json");
			sceneManager.addScene(startScene);
			eventManager = new EventManager();
			eventManager.setScene(startScene);
			eventManager.loadHardcodedBindings(collisionCallback, movementCallback);
	
			grid.setObstacles(startScene.getSceneStators());
	
			renderer.addRenderable((Renderable) startScene.getObjectById(0), 1);
			renderer.addRenderable((Renderable) startScene.getStatorById(1), 0);
	
			windowManager.createWindow(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
			windowManager.setKeyBindings(eventManager.getBindings());
		} catch (IOException e) {
			System.out.println("Error loading scene from file: " + e.getMessage());
		}
	}

	/**
	 * Runs the game loop, which continuously renders game objects and handles user
	 * input.
	 */
	public void loop() {
		windowManager.loop(renderer);
	}

	/**
	 * Moves a game object to the specified position.
	 * 
	 * @param obj The game object to move.
	 * @param x   The x-coordinate of the new position.
	 * @param y   The y-coordinate of the new position.
	 */
	public void moveTo(GameObject obj, int x, int y) {
		obj.setCellX(x);
		obj.setCellY(y);
	}

	/**
	 * Returns the instance of the Mejjiq class.
	 * 
	 * @return The instance of the Mejjiq class.
	 */
	public static Mejjiq getInstance() {
		if (instance == null) {
			instance = new Mejjiq();
		}
		return instance;
	}

	private MovementCallback movementCallback = (obj, dx, dy) -> {
		moveTo(obj, obj.getCellX() + dx, obj.getCellY() + dy);
	};

	private CollisionCallback collisionCallback = (x, y) -> {
		return x < 0 || y < 0 || grid.checkCollision(x, y);
	};

	private void loadGameResources() {
		resourceManager.addResource("Memory", 2048);
		resourceManager.addResource("Textures", 100);
		resourceManager.addResource("AudioFiles", 50);
	}

	public long getWindow() {
		return windowManager.getWindow();
	}

}
