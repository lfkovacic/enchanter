package com.game.enchanter.scene;

import java.util.ArrayList;
import java.util.List;

public class Layer<T> {

    private List<T> renderables;

    public Layer() {
        renderables = new ArrayList<>();
    }       

    public void removeRenderable(T renderable) {
        renderables.remove(renderable);
    }

    public List<T> getRenderables() {
        return renderables;
    }

    public void addRenderable(T renderable) {
        renderables.add(renderable);
    }
}


