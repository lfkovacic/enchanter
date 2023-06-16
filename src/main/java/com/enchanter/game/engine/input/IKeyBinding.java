package com.enchanter.game.engine.input;

public interface IKeyBinding {
	    
	void setKeyCode(int keyCode);
	void setKeyAction(int keyAction);
	void setExecute(Runnable execute);
	
	int getKeyCode();
	int getKeyAction();
	Runnable getExecute();
	
	void execute();
}
