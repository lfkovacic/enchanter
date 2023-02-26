package com.game.enchanter.core;

import java.util.ArrayList;
import java.util.List;

public class KeyBindings {

    private final List<KeyInput> inputs = new ArrayList<>();

    public void add(KeyInput input, Runnable action) {
        input.setExecute(() -> action.run());
        inputs.add(input);
    }

    public List<KeyInput> getInputs() {
        return inputs;
    }

}