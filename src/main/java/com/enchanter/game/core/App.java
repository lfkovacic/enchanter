package com.enchanter.game.core;
import org.lwjgl.*;

import com.enchanter.game.engine.Mejjiq;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;


public class App {
	
	//Engine
	Mejjiq mejjiq = Mejjiq.getInstance();

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		mejjiq.init();
		mejjiq.loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(mejjiq.getWindow());
		glfwDestroyWindow(mejjiq.getWindow());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public static void main(String[] args) {
		new App().run();
	}

}
