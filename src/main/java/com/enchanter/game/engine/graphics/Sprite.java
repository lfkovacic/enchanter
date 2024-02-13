package com.enchanter.game.engine.graphics;

import com.enchanter.game.engine.entities.interfaces.Renderable;
import com.enchanter.game.engine.resources.ResourceManager;
import static com.enchanter.game.consts.Consts.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class Sprite implements Renderable {
    private BufferedImage bufferedimage;
    private int scale, width, height;

    public Sprite(String filename, int scale) throws IOException {
        this.bufferedimage = ResourceManager.loadTexture(filename);
        setWidth();
        setHeight();
    }

    private void setWidth(){
        this.width = getTexture().getWidth();
    }
    
    private void setHeight(){
        this.height = getTexture().getHeight();
    }

    private int getWidth(){
        return this.width;
    }

    private int getHeight(){
        return this.height;
    }
    public BufferedImage getTexture() {
        return this.bufferedimage;
    }

    public void render() {
        int scaledWidth = getWidth() * scale;
        int scaledHeight = getHeight() * scale;

        // Create a buffer to hold the pixel data of the sprite
        ByteBuffer buffer = createTextureBuffer();

        // Create a texture ID and bind the texture
        int textureID = createTextureID(buffer, getWidth(), getHeight());

        // Render the textured quad with the scaled dimensions
        renderTexturedQuad(textureID, scaledWidth, scaledHeight);

        // Cleanup the texture resources
        // cleanupTexture(textureID);

    }
    private ByteBuffer createTextureBuffer() {
		// Get the pixel data from the image
		int[] pixels = getTexture().getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());

		// Create a ByteBuffer to hold the pixel data
		ByteBuffer buffer = BufferUtils.createByteBuffer(getWidth() * getHeight() * 4);

		// Iterate over the pixels and extract the color components
		for (int y = getHeight() - 1; y >= 0; y--) {
			for (int x = 0; x < getWidth(); x++) {
				int pixel = pixels[y * getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component
			}
		}

		buffer.flip();
		return buffer;
	}

    private int createTextureID(ByteBuffer buffer, int width, int height) {
		// Enable blending for transparency
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Enable 2D texturing
		glEnable(GL_TEXTURE_2D);

		// Generate a texture ID and bind the texture
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);

		// Load the pixel data into the texture
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		// Set texture parameters for scaling and filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		return textureID;
	}

    private void renderTexturedQuad(int textureID, int width, int height) {
		// Begin rendering the quad
		glBegin(GL_QUADS);

		// Reset color to white before rendering the textured quad
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		// Specify the texture coordinates and vertices of the quad
		// glTexCoord2f(0, 0);
		// glVertex2f(getX(), getY());
		// glTexCoord2f(1, 0);
		// glVertex2f(getX() + width, getY());
		// glTexCoord2f(1, 1);
		// glVertex2f(getX() + width, getY() + height);
		// glTexCoord2f(0, 1);
		// glVertex2f(getX(), getY() + height);

		// End rendering the quad
		glEnd();
	}

}