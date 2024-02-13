package com.enchanter.game.engine.entities;

import static com.enchanter.game.consts.Consts.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.json.JSONObject;
import org.lwjgl.BufferUtils;

import com.enchanter.game.engine.resources.ResourceManager;

public abstract class GameObject {

	private int id;

	private int cellX, cellY;
	private int cellWidth, cellHeight;
	private boolean passable = true;

	private BufferedImage sprite;

	public GameObject(int id, int _x, int _y, int _width, int _height) {
		setId(id);
		setCellX(_x);
		setCellY(_y);
		setCellWidth(_width);
		setCellHeight(_height);
	}

	public GameObject(int id, int _x, int _y, int _width, int _height, boolean _passable) {
		setId(id);
		setCellX(_x);
		setCellY(_y);
		setCellWidth(_width);
		setCellHeight(_height);
		setPassable(_passable);
	}

	public GameObject(JSONObject jo) {
		setId((int) jo.get("id"));
		setCellX((int) jo.get("x"));
		setCellY((int) jo.get("y"));
		setCellWidth((int) jo.get("width"));
		setCellHeight((int) jo.get("height"));
	}

	// Setters
	public void setId(int _id) {
		this.id = _id;
	}

	public void setX(int _x) {
		this.cellX = _x / CELL_SIZE;
	}

	public void setY(int _y) {
		this.cellY = _y / CELL_SIZE;
	}

	public void setCellX(int _x) {
		this.cellX = _x;
	}

	public void setCellY(int _y) {
		this.cellY = _y;
	}

	protected void setCellWidth(int _width) {
		this.cellWidth = _width;
	}

	protected void setCellHeight(int _height) {
		this.cellHeight = _height;
	}

	protected void setPassable(boolean _passable) {
		this.passable = _passable;
	}

	protected void setSprite(String filename) {
		try {
			this.sprite = ResourceManager.loadTexture(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Getters
	public int getId() {
		return this.id;
	}

	public int getX() {
		return this.cellX * CELL_SIZE;
	}

	public int getY() {
		return this.cellY * CELL_SIZE;
	}

	public int getWidth() {
		return this.cellWidth * CELL_SIZE;
	}

	public int getHeight() {
		return this.cellHeight * CELL_SIZE;
	}

	public boolean getPassable() {
		return this.passable;
	}

	public int getCellWidth() {
		return this.cellWidth;
	}

	public int getCellHeight() {
		return this.cellHeight;
	}

	public int getCellX() {
		return cellX;
	}

	public int getCellY() {
		return cellY;
	}

	protected BufferedImage getSprite() {
		return this.sprite;
	}

	// Render

	/**
	 * Renders the GameObject. This method needs to be implemented by subclasses.
	 */
	public abstract void render();

	/**
	 * Renders the sprite with a given scale.
	 *
	 * @param scale The scale factor to apply to the sprite.
	 */
	protected void renderSprite(int scale) {
		if (sprite != null) {
			int scaledWidth = sprite.getWidth() * scale;
			int scaledHeight = sprite.getHeight() * scale;

			// Create a buffer to hold the pixel data of the sprite
			ByteBuffer buffer = createTextureBuffer(sprite);

			// Create a texture ID and bind the texture
			int textureID = createTextureID(buffer, sprite.getWidth(), sprite.getHeight());

			// Render the textured quad with the scaled dimensions
			renderTexturedQuad(textureID, scaledWidth, scaledHeight);

			// Cleanup the texture resources
			cleanupTexture(textureID);
		}
	}

	/**
	 * Creates a ByteBuffer containing the pixel data of the image.
	 *
	 * @param image The image to extract pixel data from.
	 * @return The ByteBuffer containing the pixel data.
	 */
	private ByteBuffer createTextureBuffer(BufferedImage image) {
		// Get the pixel data from the image
		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

		// Create a ByteBuffer to hold the pixel data
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		// Iterate over the pixels and extract the color components
		for (int y = image.getHeight() - 1; y >= 0; y--) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component
			}
		}

		buffer.flip();
		return buffer;
	}

	/**
	 * Creates a texture ID, loads the pixel data into the texture, and sets texture
	 * parameters.
	 *
	 * @param buffer The ByteBuffer containing the pixel data.
	 * @param width  The width of the texture.
	 * @param height The height of the texture.
	 * @return The generated texture ID.
	 */
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

	/**
	 * Renders a textured quad with the given texture ID and dimensions.
	 *
	 * @param textureID The ID of the texture to be rendered.
	 * @param width     The width of the quad.
	 * @param height    The height of the quad.
	 */
	private void renderTexturedQuad(int textureID, int width, int height) {
		// Begin rendering the quad
		glBegin(GL_QUADS);

		// Reset color to white before rendering the textured quad
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		// Specify the texture coordinates and vertices of the quad
		glTexCoord2f(0, 0);
		glVertex2f(getX(), getY());
		glTexCoord2f(1, 0);
		glVertex2f(getX() + width, getY());
		glTexCoord2f(1, 1);
		glVertex2f(getX() + width, getY() + height);
		glTexCoord2f(0, 1);
		glVertex2f(getX(), getY() + height);

		// End rendering the quad
		glEnd();
	}

	/**
	 * Cleans up the texture resources.
	 *
	 * @param textureID The ID of the texture to be cleaned up.
	 */
	private void cleanupTexture(int textureID) {
		// Disable 2D texturing and blending
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);

		// Delete the texture
		glDeleteTextures(textureID);
	}
}
