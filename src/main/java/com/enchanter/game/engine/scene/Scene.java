package com.enchanter.game.engine.scene;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.enchanter.game.engine.entities.BackgroundTile;
import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.entities.Stator;
import com.enchanter.game.engine.entities.PlayerObject;
import com.enchanter.game.engine.entities.Wall;
import com.enchanter.game.engine.entities.interfaces.Renderable;

public class Scene {
	private final int id;
	private List<GameObject> sceneObjects;
	private List<Stator> sceneStators;
	private List<BackgroundTile> sceneBackground;

	public Scene(int id) {
		this.id = id;
		sceneObjects = new ArrayList<>();
		sceneStators = new ArrayList<>();
	}

	public Scene(JSONObject jo) {
		this.id = jo.getInt("id");

		this.sceneObjects = new ArrayList<>();
		this.sceneStators = new ArrayList<>();
		this.sceneBackground = new ArrayList<>();

		JSONArray sceneObjectsJson = jo.getJSONArray("sceneObjects");
		for (Object sceneObject : sceneObjectsJson) {
			JSONObject sceneObjectJson = (JSONObject) sceneObject;

			if (sceneObjectJson.getString("type").equals("player")) {
				addObject(new PlayerObject(sceneObjectJson));
			}
		}

		JSONArray sceneStatorsJson = jo.getJSONArray("sceneStators");
		for (Object sceneStator : sceneStatorsJson) {
			JSONObject sceneStatorJson = (JSONObject) sceneStator;
			if (sceneStatorJson.getString("type").equals("wall")) {
				addObstacle(new Wall(sceneStatorJson));
			}
		}

		JSONArray sceneBackgroundTilesJson = jo.getJSONArray("sceneBackground");
		for (Object sceneBackgroundTile : sceneBackgroundTilesJson) {
			JSONObject sceneBackgroundTileJson = (JSONObject) sceneBackgroundTile;
			addBackgroundTile(new BackgroundTile(sceneBackgroundTileJson));
		}
	}

	public int getId() {
		return this.id;
	}

	// Adding the objects to the lists

	public void addObject(GameObject obj) {
		this.sceneObjects.add(obj);
	}

	public void addObstacle(Stator obj) {
		this.sceneStators.add(obj);
	}

	public void addBackgroundTile(BackgroundTile obj) {
		this.sceneBackground.add(obj);
	}

	// Getting the lists of objects

	public List<GameObject> getSceneObjects() {
		return this.sceneObjects;
	}

	public List<Stator> getSceneStators() {
		return this.sceneStators;
	}

	public List<BackgroundTile> getSceneBackground() {
		return this.sceneBackground;
	}

	public List<Renderable> getRenderables() {
		List<Renderable> renderables = new ArrayList<>();
		for (GameObject obj : sceneObjects) {
			if (obj instanceof Renderable) {
				renderables.add((Renderable) obj);
			}
		}
		for (Stator obstacle : sceneStators) {
			if (obstacle instanceof Renderable) {
				renderables.add((Renderable) obstacle);
			}
		}
		for (BackgroundTile backgroundTile : sceneBackground) {
			if (backgroundTile instanceof Renderable) {
				renderables.add((Renderable) backgroundTile);
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

	public Stator getStatorById(int statorId) {
		for (Stator obj : sceneStators) {
			if (obj.getId() == statorId) {
				return obj;
			}
		}
		return null;
	}

	public BackgroundTile getBackgroundById(int bgId) {
		for (BackgroundTile obj : sceneBackground) {
			if (obj.getId() == bgId) {
				return obj;
			}
		}
		return null;
	}

}
