package com.enchanter.game.engine.scene;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.entities.Obstacle;
import com.enchanter.game.engine.entities.interfaces.Renderable;

public class Scene {
	private final int id;
	private List<GameObject> sceneObjects;
	private List<Obstacle> sceneStators;

	public Scene(int id) {
		this.id = id;
		sceneObjects = new ArrayList<>();
		sceneStators = new ArrayList<>();
	}

	public Scene(JSONObject jo) {
		this.id = Integer.valueOf(jo.getString("id"));
		sceneObjects = new ArrayList<>();
		sceneStators = new ArrayList<>();

		for (Object sceneObjectJson : jo.getJSONArray("sceneObjects")) {
			addObject((GameObject) sceneObjectJson);
		}

		for (Object sceneStatorJson : jo.getJSONArray("sceneStators")) {
			addObstacle((Obstacle) sceneStatorJson);
		}
	}

	public int getId() {
		return this.id;
	}

	// Adding the objects to the lists

	public void addObject(GameObject obj) {
		this.sceneObjects.add(obj);
	}

	public void addObstacle(Obstacle obj) {
		this.sceneStators.add(obj);
	}

	// Getting the lists of objects

	public List<GameObject> getSceneObjects() {
		return this.sceneObjects;
	}

	public List<Obstacle> getSceneStators() {
		return this.sceneStators;
	}

	public List<Renderable> getRenderables() {
		List<Renderable> renderables = new ArrayList<>();
		for (GameObject obj : sceneObjects) {
			if (obj instanceof Renderable) {
				renderables.add((Renderable) obj);
			}
		}
		for (Obstacle obstacle : sceneStators) {
			if (obstacle instanceof Renderable) {
				renderables.add((Renderable) obstacle);
			}
		}
		return renderables;
	}

	public GameObject getObjectById(int objectId) {
		for (GameObject obj : sceneObjects) {
			if (obj.getId() == objectId) {
				return obj;
			}
		}
		return null; // Object with specified ID not found
	}

	public Obstacle getStatorById(int statorId) {
		for (Obstacle obj : sceneStators) {
			if (obj.getId() == statorId) {
				return obj;
			}
		}
		return null;
	}

}
