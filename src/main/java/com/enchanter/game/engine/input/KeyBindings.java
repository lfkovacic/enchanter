package com.enchanter.game.engine.input;

import java.util.ArrayList;
import java.util.List;

public class KeyBindings {

    private final List<KeyInput> inputs = new ArrayList<>();

    public void add(KeyInput input,  Runnable runnable) {
        input.setExecute(() -> runnable.run());
        inputs.add(input);
    }

    public List<KeyInput> getInputs() {
        return inputs;
    }

}