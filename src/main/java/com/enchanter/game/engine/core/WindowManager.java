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

    public WindowManager() {
        keyBindings = new ArrayList<>();
    }

    public void createWindow(int width, int height) {
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
                    (vidmode.height() - pHeight.get(0)) / 2
            );
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
    GL.createCapabilities();
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    while (!glfwWindowShouldClose(window)) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Render your game objects here
        renderer.render(); // Render the game objects using the provided renderer

        glfwSwapBuffers(window);
        glfwPollEvents();
    }
}

    public void setKeyBindings(List<KeyBinding> keyBindings) {
        this.keyBindings = keyBindings;
    }

    public long getWindow() {
        return window;
    }
}
