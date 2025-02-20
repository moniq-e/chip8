package com.monique.chip8;

import javax.swing.JFrame;

public class Display extends JFrame{
    public static final int WIDTH = 64;
    public static final int HEIGHT = 32;
    private Panel panel;
    private boolean[][] pixels;
    private KeyHandler keys = new KeyHandler();

    public Display() {
        super("MNQ-8");

        pixels = new boolean[HEIGHT][WIDTH];
        panel = new Panel(this, WIDTH, HEIGHT);

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
        pixels = new boolean[HEIGHT][WIDTH];
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

    public boolean getPressedKey(short keyCode) {
        return keys.pressedKeys[keyCode];
    }

    public Key getAnyPressedKey() {
        for (int i = 0; i < keys.pressedKeys.length; i++) {
            if (keys.pressedKeys[i]) return new Key(i, true);
        }
        return new Key(-1, false);
    }
}
