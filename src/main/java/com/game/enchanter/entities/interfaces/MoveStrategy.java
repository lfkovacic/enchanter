package com.game.enchanter.entities.interfaces;
import com.game.enchanter.entities.GameObject;

public interface MoveStrategy extends Movable {
    void move(GameObject gameObject);
}
