package com.game.enchanter.engine;

import com.game.enchanter.entities.GameObject;
import com.game.enchanter.entities.PlayerObject;
import com.game.enchanter.entities.Wall;
import com.game.enchanter.entities.interfaces.Renderable;
import com.game.enchanter.graphics.Renderer;

import static com.game.enchanter.consts.Consts.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import com.game.enchanter.core.KeyBindings;
import com.game.enchanter.core.KeyInput;

public class Mejjiq {
	
	public Grid grid;
	public Renderer<Renderable> renderer;	

	//Key bindings
	
	KeyBindings keyBindings = new KeyBindings();

	// The window handle
	private long window;
	
	public Mejjiq() {
		grid = new Grid(SCREEN_WIDTH, SCREEN_HEIGHT, CELL_SIZE, CELL_SIZE);
		renderer = new Renderer<Renderable>(3);
	}
	
	public void init() {
		
		//Game objects		
		
		PlayerObject player = new PlayerObject(0, 0);
		Wall wall = new Wall (4, 5, 6, 10, false);
		
		this.grid.addObject(wall);
		
		this.renderer.addRenderable(player, 1);
		this.renderer.addRenderable(wall, 0);
		
		//Setting the bindings
		
		KeyInput moveUp = new KeyInput(GLFW.GLFW_KEY_W);
		KeyInput moveDown = new KeyInput(GLFW.GLFW_KEY_S);
		KeyInput moveLeft = new KeyInput(GLFW.GLFW_KEY_A);
		KeyInput moveRight = new KeyInput(GLFW.GLFW_KEY_D);
		
		keyBindings.add(moveUp, () -> {
		    if (!checkCollision(player.getCellX(), player.getCellY() + 1)) {
		        moveBy(player, 0, 1);
		    }
		});

		keyBindings.add(moveDown, () -> {
		    if (!checkCollision(player.getCellX(), player.getCellY() - 1)) {
		        moveBy(player, 0, -1);
		    }
		});

		keyBindings.add(moveLeft, () -> {
		    if (!checkCollision(player.getCellX() - 1, player.getCellY())) {
		        moveBy(player, -1, 0);
		    }
		});

		keyBindings.add(moveRight, () -> {
		    if (!checkCollision(player.getCellX() + 1, player.getCellY())) {
		        moveBy(player, 1, 0);
		    }
		});
		
		
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
	        	
	        	for (KeyInput input : keyBindings.getInputs()) {
	        		if (input.isKeyPressed(key, action)) input.execute(); 
	        	}
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
	
	public void loop() {
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

			render();
			glfwSwapBuffers(window); // swap the color buffers
			

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
	
	public void render() {
		renderer.render();
	}
	
	
	public void moveTo(GameObject obj, int x, int y) {
		obj.setCellX(x);
		obj.setCellY(y);
	}
	public void moveBy(GameObject obj, int dx, int dy) {
		moveTo(obj, obj.getCellX()+dx, obj.getCellY()+dy);
	}
	
	public boolean checkCollision(int x, int y) {
		if (x<0||y<0) return true;
		else return grid.checkCollision((int)x, (int)y);
	}

	public long getWindow() {
		return this.window;
	}
		
}
