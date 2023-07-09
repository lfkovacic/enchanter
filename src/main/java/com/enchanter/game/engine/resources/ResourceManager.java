package com.enchanter.game.engine.resources;

import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.enchanter.game.engine.entities.GameObject;
import com.enchanter.game.engine.scene.Scene;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.json.JSONObject;

public class ResourceManager {
    private Map<String, Integer> resourceInventory;
    private static Dimension screenResolution;

    public static Dimension getScreenResolution() {
        if (screenResolution == null) {
            // Retrieve the screen size using Toolkit
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            screenResolution = toolkit.getScreenSize();
        }
        return screenResolution;
    }

    public ResourceManager() {
        resourceInventory = new HashMap<>();
    }

    public void addResource(String resourceName, int quantity) {
        resourceInventory.put(resourceName, quantity);
    }

    public void removeResource(String resourceName) {
        resourceInventory.remove(resourceName);
    }

    public int getResourceQuantity(String resourceName) {
        Integer quantity = resourceInventory.get(resourceName);
        return quantity != null ? quantity : 0;
    }

    public boolean hasSufficientResources(Map<String, Integer> requiredResources) {
        for (Map.Entry<String, Integer> entry : requiredResources.entrySet()) {
            String resourceName = entry.getKey();
            int requiredQuantity = entry.getValue();
            int availableQuantity = getResourceQuantity(resourceName);
            if (requiredQuantity > availableQuantity) {
                return false;
            }
        }
        return true;
    }

    public void allocateResources(Map<String, Integer> requiredResources) {
        for (Map.Entry<String, Integer> entry : requiredResources.entrySet()) {
            String resourceName = entry.getKey();
            int requiredQuantity = entry.getValue();
            int availableQuantity = getResourceQuantity(resourceName);
            int remainingQuantity = availableQuantity - requiredQuantity;
            if (remainingQuantity >= 0) {
                resourceInventory.put(resourceName, remainingQuantity);
            }
        }
    }

    public void releaseResources(Map<String, Integer> releasedResources) {
        for (Map.Entry<String, Integer> entry : releasedResources.entrySet()) {
            String resourceName = entry.getKey();
            int releasedQuantity = entry.getValue();
            int currentQuantity = getResourceQuantity(resourceName);
            int newQuantity = currentQuantity + releasedQuantity;
            resourceInventory.put(resourceName, newQuantity);
        }
    }

    public void printResourceInventory() {
        System.out.println("Resource Inventory:");
        for (Map.Entry<String, Integer> entry : resourceInventory.entrySet()) {
            String resourceName = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(resourceName + ": " + quantity);
        }
    }

    public static BufferedImage loadTexture(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));
        return image;
    }

    public static Clip loadSound(String filename) throws Exception {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(filename)));
        return clip;
    }

    public static Scene loadScene(String filename) throws IOException {
        JSONObject jo = loadJson(filename);
        Scene scene = new Scene(jo);
        return scene;
    }

    public static GameObject loadGameObject(String filename) {
        return null;
        // TODO: Load game object from json file
    }

    public static JSONObject loadJson(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filename));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();

        String jsonContent = new String(buffer);
        JSONObject jo = new JSONObject(jsonContent);

        return jo;
    }
}
