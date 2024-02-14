package com.enchanter.game.core;
import org.lwjgl.*;

import com.enchanter.game.engine.Mejjiq;


public class App {
	
	//Engine
	Mejjiq mejjiq = Mejjiq.getInstance();

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		mejjiq.init();
		mejjiq.run();
	}

	public static void main(String[] args) {
		new App().run();
	}

}
