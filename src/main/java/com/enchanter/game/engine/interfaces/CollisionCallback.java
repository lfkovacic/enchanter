package com.enchanter.game.engine.interfaces;

@FunctionalInterface
public interface CollisionCallback {
    boolean checkCollision(int x, int y);
}
