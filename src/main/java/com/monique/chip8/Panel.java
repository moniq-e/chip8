package com.monique.chip8;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel {
    private Display display;
    private final int pWidth;
    private final int pHeight;
    private float size;
    
    public Panel(Display display, int pWidth, int pHeight) {
        setSize(display.getSize());
        setLayout(null);
        setOpaque(true);
        setBackground(Color.BLACK);

        this.display = display;
        this.pWidth = pWidth;
        this.pHeight = pHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        size = (float) getWidth() / pWidth;

        for (int y = 0; y < pHeight; y++) {
            for (int x = 0; x < pWidth; x++) {
                var pixel = display.getPixels()[y][x];
                if (pixel) {
                    g.setColor(Color.WHITE);
                    g.fillRect((int) (x * size), (int) ((y * size) + (getHeight() - pHeight * size) / 2), (int) size, (int) size);
                }
            }
        }
    }

    public void update() {
        revalidate();
        repaint();
    }
}
