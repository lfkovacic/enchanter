package com.game.enchanter.core;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.game.enchanter.engine.Grid;
import com.game.enchanter.entities.GameObject;
import com.game.enchanter.entities.PlayerObject;
import com.game.enchanter.entities.Wall;
import com.game.enchanter.entities.interfaces.Renderable;
import com.game.enchanter.graphics.Renderer;

import java.nio.*;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static com.game.enchanter.consts.Consts.*;

public class App {
	
	//Renderer
	Renderer<Renderable> renderer = new Renderer<Renderable>(3);
	
	//Key bindings
	
	KeyBindings keyBindings = new KeyBindings();
	
	//Grid
	
	Grid grid = new Grid(SCREEN_WIDTH, SCREEN_HEIGHT, CELL_SIZE, CELL_SIZE);

	// The window handle
	private long window;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		
		//Game objects		
		
		PlayerObject player = new PlayerObject(0, 0);
		Wall wall = new Wall (4, 5, 6, 2, false);		
		
		renderer.addRenderable(player, 1);
		renderer.addRenderable(wall, 0);
		
		//Setting the bindings
		
		KeyInput moveUp = new KeyInput(GLFW.GLFW_KEY_W);
		KeyInput moveDown = new KeyInput(GLFW.GLFW_KEY_S);
		KeyInput moveLeft = new KeyInput(GLFW.GLFW_KEY_A);
		KeyInput moveRight = new KeyInput(GLFW.GLFW_KEY_D);
		
		/*keyBindings.add(moveUp, (Runnable)player::moveUp);
		keyBindings.add(moveDown, (Runnable)player::moveDown);
		keyBindings.add(moveLeft, (Runnable)player::moveLeft);
		keyBindings.add(moveRight, (Runnable)player::moveRight);*/
		
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Create a new key callback
	    GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
	        @Override
	        public void invoke(long window, int key, int scancode, int action, int mods) {
	        	
	        	/*for (KeyInput input : keyBindings.getInputs()) {
	        		if (input.isKeyPressed(key, action)) input.execute(); 
	        	}*/
	        }
	    };

	    // Set the key callback
	    glfwSetKeyCallback(window, keyCallback);

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();
	    glOrtho(0, 900, 0, 900, -1, 1);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			renderer.render();
			glfwSwapBuffers(window); // swap the color buffers
			

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

	public static void main(String[] args) {
		new App().run();
	}

}
