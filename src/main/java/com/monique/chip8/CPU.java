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
    private Display display;

    public CPU(InputStream rom, Display display) {
        ram = new byte[4096];
        pc = 0x200;
        i = -1;
        iStack = new short[16];
        delay = 0;
        sound = 0;
        variables = new byte[16];

        this.rom = rom;
        this.display = display;
        loadRom();
        loadFont();
    }

    public void tick() {
        short opcode = (short) ((ram[pc] & 0xFF) << 8 | (ram[pc+1] & 0xFF));

        opcode &= 0xFFFF;
        pc += 2;
        
        executeOpcode(opcode);
        
        delay -= (delay > 0) ? 1 : 0;
        sound -= (sound > 0) ? 1 : 0;
    }

    public void executeOpcode(short opcode) {
        int x = (opcode & 0x0F00) >> 8;
        int y = (opcode & 0x00F0) >> 4;

        switch (opcode & 0xF000) {
            case 0x0000:
                switch (opcode) {
                    case 0x00E0:
                        display.clean();
                        break;
                    case 0x00EE:
                        pc = iStack[--i];
                        break;
                }
                break;
            case 0x1000:
                pc = (short) (opcode & 0xFFF);
                break;
            case 0x6000:
                variables[x] = (byte) (opcode & 0xFF);
                break;
            case 0x7000:
                variables[x] += (byte) (opcode & 0xFF);
                break;
            case 0xA000:
                i = (short) (opcode & 0xFFF);
                break;
            case 0xD000:
                byte width = 8;
                byte height = (byte) (opcode & 0xF);

                variables[0xF] = 0;

                for (int index = 0; index < height; index++) {
                    byte sprite = ram[i + index];

                    for (int j = 0; j < width; j++) {
                        if ((sprite & 0x80) > 0) {
                            if (display.togglePixel(variables[x] + j, variables[y] + index)) {
                                variables[0xF] = 1;
                            }
                        }

                        sprite <<= 1;
                    }
                }
                break;
            default:
                break;
        }
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

        int index = 0x50;
        int temp;
        for (String f : fonts) {
            temp = Integer.decode(f);
            ram[index++] = (byte) temp;
        }
    }
}
