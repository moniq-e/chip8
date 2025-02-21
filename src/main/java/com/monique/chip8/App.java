package com.monique.chip8;

public class App {

    private App() {
        var rom = getClass().getResourceAsStream("/pong.ch8");

        var display = new Display();
        var cpu = new CPU(rom, display);
        display.setVisible(true);

        while (true) {
            try {
                Thread.sleep(2);

                cpu.tick();
                display.update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
