package com.monique.chip8;

public class Key {
    private int keyCode;
    private boolean pressed;

    public Key(int keyCode, boolean pressed) {
        this.keyCode = keyCode;
        this.pressed = pressed;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isPressed() {
        return pressed;
    }
}
