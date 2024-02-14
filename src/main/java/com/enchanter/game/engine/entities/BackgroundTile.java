package com.enchanter.game.engine.entities;

import org.json.JSONObject;

import com.enchanter.game.engine.entities.interfaces.Renderable;

public class BackgroundTile extends Stator implements Renderable {
    public BackgroundTile(JSONObject jo) {
        super(jo);
        super.setSprite(jo.getString("spritePath"));
        System.out.println("Making new background");
    }

    public void render() {
        renderSprite(2);
    }

}
