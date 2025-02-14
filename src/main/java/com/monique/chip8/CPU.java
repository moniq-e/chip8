package com.monique.chip8;

import java.io.InputStream;
import java.util.Scanner;

public class CPU {
    private InputStream rom;
    private byte[] ram;
    private short pc;
    private short i;
    private short[] iStack;
    private byte[] variables;
    private byte delay;
    private byte sound;

    public CPU(InputStream rom) {
        ram = new byte[4096];
        pc = 0x200;
        i = -1;
        iStack = new short[16];
        delay = 0;
        sound = 0;
        variables = new byte[16];

        this.rom = rom;
        loadRom();
        loadFont();
    }

    public void tick() {

    }

    private void loadRom() {
        try {
            int data;

            while ((data = rom.read()) != -1) {
                ram[pc++] = (byte) data;
            }
            pc = 0x200;
            rom.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFont() {
        var scan = new Scanner(getClass().getResourceAsStream("/font.txt"), "UTF-8").useDelimiter("\\A");
        var font = "";
        while (scan.hasNext()) {
            font += scan.next();
        }
        scan.close();

        font = font.replaceAll("\n", " ").trim();
        var fonts = font.split(", ");

        int i = 0x50;
        int temp;
        for (String f : fonts) {
            temp = Integer.decode(f);
            ram[i++] = (byte) temp;
        }
    }
}
