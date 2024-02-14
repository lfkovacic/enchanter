package com.enchanter.game.engine.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import com.enchanter.game.engine.entities.interfaces.Renderable;
import com.enchanter.game.engine.events.input.KeyBinding;
import com.enchanter.game.engine.graphics.Renderer;

public class WindowManager {

    private long window;
    private List<KeyBinding> keyBindings;
    private int SCREEN_HEIGHT, SCREEN_WIDTH;
    private double lastFPSTime = 0.0;
    private double fps = 0.0;
    private int frameCount = 0;

    public WindowManager() {
        keyBindings = new ArrayList<>();
    }

    public void createWindow(int width, int height) {
        SCREEN_HEIGHT = height;
        SCREEN_WIDTH = width;
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "Enchanter", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                for (KeyBinding input : keyBindings) {
                    if (input.isKeyPressed(key, action))
                        input.execute();
                }
            }
        };

        glfwSetKeyCallback(window, keyCallback);

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, 0, height, -1, 1);

        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    public void render(Renderer<Renderable> renderer) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderer.render(); // Render the game objects using the provided renderer
        renderFPS();

        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    private void renderFPS() {
        // Get the current time
        double currentTime = glfwGetTime();

        // Calculate the time difference since the last frame
        double frameTime = currentTime - lastFPSTime;

        // Increment the frame counter
        frameCount++;

        // If one second has passed, update the FPS
        if (frameTime >= 1.0) {
            fps = frameCount / frameTime;
            lastFPSTime = currentTime;
            frameCount = 0;
        }

        // Render the FPS
        glLoadIdentity();
        glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, -1, 1);
        glColor3f(1.0f, 1.0f, 1.0f);
        glRasterPos2f(10, SCREEN_HEIGHT - 20);
        glfwSetWindowTitle(window, "Enchanter - FPS: " + String.format("%.2f", fps));
    }

    public void setKeyBindings(List<KeyBinding> keyBindings) {
        this.keyBindings = keyBindings;
    }

    public long getWindow() {
        return window;
    }
}
