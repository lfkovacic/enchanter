package com.enchanter.game.engine.scene;

public enum SceneID {
    START_SCENE(0),
    SCENE_2(1),
    SCENE_3(2);

    private int id;

    SceneID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
