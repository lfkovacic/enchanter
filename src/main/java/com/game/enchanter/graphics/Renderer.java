package com.game.enchanter.graphics;

import com.game.enchanter.entities.interfaces.Renderable;
import com.game.enchanter.scene.Layer;
import java.util.ArrayList;
import java.util.List;

public class Renderer<T extends Renderable> {

    private final List<Layer<T>> renderLayers;
    
    public Renderer(int numLayers) {
        this.renderLayers = new ArrayList<>(numLayers);
        for (int i = 0; i < numLayers; i++) {
            this.renderLayers.add(new Layer<>());
        }
    }
    
    public void addRenderable(T renderable, int layerIndex) {
        renderLayers.get(layerIndex).addRenderable(renderable);
    }
    
    public void addLayer(Layer<T> layer, int layerIndex) {
        this.renderLayers.set(layerIndex, layer);
    }
    
    public void render() {
        // Set up projection matrix and any other OpenGL settings here
        // ...
        
        // Loop through all layers and render objects in each layer
        for (Layer<T> layer : this.renderLayers) {
            for (T renderable : layer.getRenderables()) {
                renderable.render();
            }
        }
        
        // Reset any OpenGL settings here if necessary
        // ...
    }
    
}