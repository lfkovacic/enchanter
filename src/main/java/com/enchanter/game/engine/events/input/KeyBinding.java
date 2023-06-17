package com.enchanter.game.engine.events.input;

import static org.lwjgl.glfw.GLFW.*;

public class KeyBinding implements IKeyBinding {

    private Runnable execute;
    private int keyCode;
    private int keyAction;
    
    public KeyBinding(int keyCode, int keyAction, Runnable execute) {
        setKeyCode(keyCode);
        setKeyAction(keyAction);
        setExecute(execute);
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