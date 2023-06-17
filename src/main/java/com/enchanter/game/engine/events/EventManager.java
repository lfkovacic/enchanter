package com.enchanter.game.engine.events;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.events.input.KeyBinding;
import com.enchanter.game.engine.scene.Scene;
import com.enchanter.game.engine.interfaces.CollisionCallback;
import com.enchanter.game.engine.interfaces.MovementCallback;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

public class EventManager {
    private Scene scene;
    private List<KeyBinding> keyBindings;

    public EventManager() {
        keyBindings = new ArrayList<>();
    }

    public void setScene(Scene scene) {
        this.scene = scene;   
    }

    public void addBinding(KeyBinding keyBinding) {
        keyBindings.add(keyBinding);
    }

    public void removeBinding(KeyBinding keyBinding) {
        keyBindings.remove(keyBinding);
    }

    public void clearBindings() {
        keyBindings.clear();
    }

    public List<KeyBinding> getBindings(){
        return this.keyBindings;
    }

    public void processInput(int key, int action) {
        // Iterate over key bindings and check for matching key and action
        for (KeyBinding keyBinding : keyBindings) {
            if (keyBinding.isKeyPressed(key, action)) {
                keyBinding.execute();
            }
        }
    }

    public void loadHardcodedBindings(CollisionCallback collisionCallback, MovementCallback movementCallback) {
    GameObject player = scene.getObjectById(0);

    // Hardcode the key bindings here
    // Example key bindings:
    // Setting the bindings
    KeyBinding moveUp = new KeyBinding(GLFW.GLFW_KEY_W, GLFW.GLFW_REPEAT, () -> {
        if (!collisionCallback.checkCollision(player.getCellX(), player.getCellY() + 1)) {
            movementCallback.moveBy(player, 0, 1);
        }
    });

    KeyBinding moveDown = new KeyBinding(GLFW.GLFW_KEY_S, GLFW.GLFW_REPEAT, () -> {
        if (!collisionCallback.checkCollision(player.getCellX(), player.getCellY() - 1)) {
            movementCallback.moveBy(player, 0, -1);
        }
    });

    KeyBinding moveLeft = new KeyBinding(GLFW.GLFW_KEY_A, GLFW.GLFW_REPEAT, () -> {
        if (!collisionCallback.checkCollision(player.getCellX() - 1, player.getCellY())) {
            movementCallback.moveBy(player, -1, 0);
        }
    });

    KeyBinding moveRight = new KeyBinding(GLFW.GLFW_KEY_D, GLFW.GLFW_REPEAT, () -> {
        if (!collisionCallback.checkCollision(player.getCellX() + 1, player.getCellY())) {
            movementCallback.moveBy(player, 1, 0);
        }
    });

    // Add the key bindings to the event manager
    // Example code:
    addBinding(moveUp);
    addBinding(moveDown);
    addBinding(moveLeft);
    addBinding(moveRight);
}
}