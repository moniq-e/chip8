package com.monique.chip8;

import javax.swing.JFrame;

public class Display extends JFrame{
    private Panel panel;
    private boolean[][] pixels;

    public Display() {
        super("MNQ-8");

        pixels = new boolean[32][64];
        panel = new Panel(this, 64, 32);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        add(panel);
        pack();
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
}
