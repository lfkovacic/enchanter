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

	protected BufferedImage getSprite(){
		return this.sprite;
	}

	// Render

	public abstract void render();

	

    protected void renderSprite(int scale) {
		if (sprite != null) {
			int scaledWidth = sprite.getWidth() * scale;
			int scaledHeight = sprite.getHeight() * scale;
	
			ByteBuffer buffer = createTextureBuffer(sprite);
			int textureID = createTextureID(buffer, sprite.getWidth(), sprite.getHeight());
			renderTexturedQuad(textureID, scaledWidth, scaledHeight);
			cleanupTexture(textureID);
		}
	}

    private ByteBuffer createTextureBuffer(BufferedImage image) {
        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = image.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));  // Green component
                buffer.put((byte) (pixel & 0xFF));         // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component
            }
        }

        buffer.flip();
        return buffer;
    }

    private int createTextureID(ByteBuffer buffer, int width, int height) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_TEXTURE_2D);
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        return textureID;
    }

    private void renderTexturedQuad(int textureID, int width, int height) {
        glBegin(GL_QUADS);
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // Reset color to white before rendering the textured quad
        glTexCoord2f(0, 0);
        glVertex2f(getX(), getY());
        glTexCoord2f(1, 0);
        glVertex2f(getX() + width, getY());
        glTexCoord2f(1, 1);
        glVertex2f(getX() + width, getY() + height);
        glTexCoord2f(0, 1);
        glVertex2f(getX(), getY() + height);
        glEnd();
    }

    private void cleanupTexture(int textureID) {
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDeleteTextures(textureID);
    }

}
