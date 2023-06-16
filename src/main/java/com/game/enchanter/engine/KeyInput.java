package com.game.enchanter.engine;

import com.game.enchanter.engine.interfaces.KeyBinding;
import static org.lwjgl.glfw.GLFW.*;

public class KeyInput implements KeyBinding {

    private Runnable execute;
    private int keyCode;
    private int keyAction;
    
    public KeyInput(int keyCode, int KeyAction) {
        setKeyCode(keyCode);
        setKeyAction(keyAction);
    }
    
    //Setters 
    
    @Override
    public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
    @Override
    public void setKeyAction(int keyAction) {
		this.keyAction = keyAction;
	}
    @Override
    public void setExecute(Runnable execute) {
        this.execute = execute;
    }
    
    //Getters
    @Override
    public int getKeyCode() {
		return keyCode;
	}
    @Override
	public int getKeyAction() {
		return this.keyAction;
	}
    @Override
	public Runnable getExecute() {
		// TODO Auto-generated method stub
		return this.execute;
	}
	
    //Functions
    
    public void execute() {
        if (execute != null) {
            execute.run();
        }
    }

	public boolean isKeyPressed (int key, int action) {
		
		return getKeyCode()==key&& (action==GLFW_PRESS || action==GLFW_REPEAT);
	}

	
    
}