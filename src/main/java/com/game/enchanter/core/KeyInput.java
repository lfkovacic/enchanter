package com.game.enchanter.core;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class KeyInput {

    private Map<Integer, KeyBinding> keyBindings = new HashMap<>();
    private Runnable execute;
    private int keyCode;
    
    public KeyInput(int keyCode) {
        setKeyCode(keyCode);
    }

    public void addKeyBinding(int key, KeyBinding keyBinding) {
        keyBindings.put(key, keyBinding);
    }
    
    public void execute() {
        if (execute != null) {
            execute.run();
        }
    }

    public void setExecute(Runnable execute) {
        this.execute = execute;
    }

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public interface KeyBinding {
		void setKeyCode();
        int getKeyCode();
        void setExecute();
        Runnable getExecute();
        void execute();
    }

	public boolean isKeyPressed(int key, int action) {
		return getKeyCode()==key&&action == GLFW_PRESS;
	}
    
}