package com.enchanter.game.engine.interfaces;
import com.enchanter.game.engine.entities.GameObject;

@FunctionalInterface
public interface MovementCallback {
    void moveBy(GameObject obj, int dx, int dy);
}
