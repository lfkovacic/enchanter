package com.game.enchanter.engine;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Controller extends GLFWKeyCallback {
    
    protected static Object Event;
	private Map<Integer, Runnable> actions;
    
    public Controller() {
        actions = new HashMap<>();
    }
    
    public void bindKey(int key, Runnable action) {
        actions.put(key, action);
    }
    
    public void unbindKey(int key) {
        actions.remove(key);
    }
    
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (actions.containsKey(key) && action == GLFW_PRESS) {
            actions.get(key).run();
        }
    }
}

