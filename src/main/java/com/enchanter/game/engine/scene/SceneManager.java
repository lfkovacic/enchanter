package com.enchanter.game.engine.scene;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.entities.Obstacle;
import com.enchanter.game.engine.entities.PlayerObject;
import com.enchanter.game.engine.entities.Wall;

import java.util.LinkedList;
import java.util.List;

public class SceneManager {
    private static LinkedList<Scene> scenes;

    public SceneManager() {
        scenes = new LinkedList<>();
        createScenesHardcoded();
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

    public void createScene(SceneID id, GameObject... gameObjects) {
        Scene scene = new Scene(id.getID());
        addObjects(scene, gameObjects);
        scenes.add(scene);
    }

    public void createScene(SceneID id, List<GameObject> gameObjects) {
        Scene scene = new Scene(id.getID());
        addObjects(scene, gameObjects);
        scenes.add(scene);
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public Scene getScene(int index) {
        if (index >= 0 && index < scenes.size()) {
            return scenes.get(index);
        } else {
            throw new IllegalArgumentException("Invalid scene index");
        }
    }

    public Scene findSceneById(SceneID id) {
        for (Scene scene : scenes) {
            if (scene.getId() == id.getID()) {
                return scene;
            }
        }
        return null; // Scene with the specified id not found
    }

    public int getSceneCount() {
        return scenes.size();
    }

    private void addObjects(Scene scene, GameObject... gameObjects) {
        for (GameObject gameObject : gameObjects) {
            scene.addObject(gameObject);

            if (gameObject instanceof Obstacle) {
                scene.addObstacle((Obstacle) gameObject);
            }
        }
    }

    private void addObjects(Scene scene, List<GameObject> gameObjects) {
        for (GameObject gameObject : gameObjects) {
            scene.addObject(gameObject);

            if (gameObject instanceof Obstacle) {
                scene.addObstacle((Obstacle) gameObject);
            }
        }
    }

    public void createScenesHardcoded() {
        Scene startScene = new Scene(SceneID.START_SCENE.getID());
        PlayerObject player = new PlayerObject(0, 0, 0);
		Wall wall = new Wall(1, 4, 5, 10, 6, false);
		startScene.addObject(player);
		startScene.addObstacle(wall);

        addScene(startScene);

        // Create and add scenes manually
       

        // Add more scenes as needed
    }
}