package com.monique.chip8;

import javax.swing.JFrame;

public class Display extends JFrame{
    private Panel panel;
    private boolean[][] pixels;
    private KeyHandler keys = new KeyHandler();

    public Display() {
        super("MNQ-8");

        pixels = new boolean[32][64];
        panel = new Panel(this, 64, 32);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        add(panel);
        addKeyListener(keys);
    }

    public void setPixel(boolean state, int x, int y) {
        pixels[y][x] = state;
    }

    public void clean() {
        pixels = new boolean[32][64];
    }

    public void update() {
        panel.update();
    }

    public boolean[][] getPixels() {
        return pixels;
    }

    public boolean togglePixel(int x, int y) {
        pixels[y][x] = !pixels[y][x];

        return !pixels[y][x];
    }

    public boolean getPressedKey(byte keyCode) {
        return keys.pressedKeys[keyCode];
    }

    public Key getAnyPressedKey() {
        for (int i = 0; i < keys.pressedKeys.length; i++) {
            if (keys.pressedKeys[i]) return new Key(i, true);
        }
        return new Key(-1, false);
    }
}
