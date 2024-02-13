package com.enchanter.game.engine.scene;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.entities.Obstacle;
import com.enchanter.game.engine.entities.PlayerObject;
import com.enchanter.game.engine.entities.Wall;
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
		this.id = jo.getInt("id");

		this.sceneObjects = new ArrayList<>();
		this.sceneStators = new ArrayList<>();

		JSONArray sceneObjectsJson = jo.getJSONArray("sceneObjects");
		for (int i = 0; i < sceneObjectsJson.length(); i++) {
			Object sceneObject = sceneObjectsJson.get(i);
			JSONObject sceneObjectJson = (JSONObject) sceneObject;
			System.out.println(sceneObjectJson.toString());
			System.out.println(sceneObjectJson.getString("type"));

			if (sceneObjectJson.getString("type").equals("player")) {
				System.out.println("Making new player");
				addObject(new PlayerObject(sceneObjectJson));
			}
		}

		JSONArray sceneStatorsJson = jo.getJSONArray("sceneStators");
		for (int i = 0; i < sceneStatorsJson.length(); i++) {
			Object sceneStator = sceneStatorsJson.get(i);
			JSONObject sceneStatorJson = (JSONObject) sceneStator;
			if (sceneStatorJson.getString("type").equals("wall")) {
				addObstacle(new Wall(sceneStatorJson));
			}
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
