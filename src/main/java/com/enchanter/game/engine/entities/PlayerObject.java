package com.enchanter.game.engine.entities;

import org.json.JSONObject;

import com.enchanter.game.engine.entities.interfaces.Renderable;

public class PlayerObject extends GameObject implements Renderable {

    public PlayerObject(int id, int x, int y, String filename) {
        super(id, x, y, 1, 2);
        super.setSprite(filename);
    }

    public PlayerObject(JSONObject jo) {

        super(jo);
        super.setSprite(jo.getString("spritePath"));
        System.out.println("Making new player!");
    }

    public void render() {

        renderSprite(2);
    }

}