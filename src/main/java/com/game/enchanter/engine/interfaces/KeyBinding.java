package com.game.enchanter.engine.interfaces;

public interface KeyBinding {
	    
	void setKeyCode(int keyCode);
	void setKeyAction(int keyAction);
	void setExecute(Runnable execute);
	
	int getKeyCode();
	int getKeyAction();
	Runnable getExecute();
	
	void execute();
}
