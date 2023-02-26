package com.game.enchanter.core;
import org.lwjgl.*;

import com.game.enchanter.engine.Mejjiq;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;


public class App {
	
	//Engine
	Mejjiq mejjiq = new Mejjiq();

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
