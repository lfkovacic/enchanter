package com.game.enchanter.scene;

import java.util.ArrayList;
import java.util.List;

import com.game.enchanter.entities.GameObject;
import com.game.enchanter.entities.Obstacle;

public class Scene {
	private List<GameObject> sceneObjects;
	private List<Obstacle> sceneStators;
	
	public Scene() {
		sceneObjects = new ArrayList<GameObject>();
		sceneStators = new ArrayList<Obstacle>();
	}
	
	//Adding the objects to the lists
	
	public void addObject(GameObject obj) {
		this.sceneObjects.add(obj);
	}
	
	public void addObstacle(Obstacle obj) {
		this.sceneStators.add(obj);
	}
	
	//Getting the lists of objects
	
	public List<GameObject> getSceneObjects(){
		return this.sceneObjects;
	}
	
	public List<Obstacle> getSceneStators(){
		return this.sceneStators;
	}
	

}
