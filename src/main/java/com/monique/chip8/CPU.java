package com.monique.chip8;

import java.io.InputStream;
import java.util.Scanner;

public class CPU {
    private InputStream rom;
    private byte[] ram;
    private short pc;
    private short si;
    private short ir;
    private short[] stack;
    private byte[] variables;
    private byte delay;
    private byte sound;
    private Display display;

    public CPU(InputStream rom, Display display) {
        ram = new byte[4096];
        pc = 0x200; //512
        si = 0;
        stack = new short[0x10]; //16
        delay = 0;
        sound = 0;
        ir = 0;
        variables = new byte[0x10]; //16

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
                        pc = stack[--si];
                        break;
                }
                break;
            case 0x1000:
                pc = (short) (opcode & 0x0FFF);
                break;
            case 0x2000:
                stack[si++] = pc;
                pc = (short) (opcode & 0x0FFF);
                break;
            case 0x3000:
                if (variables[x] == (opcode & 0x00FF)) pc += 2;
                break;
            case 0x4000:
                if (variables[x] != (opcode & 0x00FF)) pc += 2;
                break;
            case 0x5000:
                if (variables[x] == variables[y]) pc += 2;
                break;
            case 0x6000:
                variables[x] = (byte) (opcode & 0x00FF);
                break;
            case 0x7000:
                variables[x] += (byte) (opcode & 0x00FF);
                break;
            case 0x8000:
                switch (opcode & 0x000F) {
                    case 0x0:
                        variables[x] = variables[y];
                        break;
                    case 0x1:
                        variables[x] |= variables[y];
                        break;
                    case 0x2:
                        variables[x] &= variables[y];
                        break;
                    case 0x3:
                        variables[x] ^= variables[y];
                        break;
                    case 0x4:
                        variables[x] += variables[y];
                        break;
                    case 0x5:
                        variables[0xF] = (byte) ((variables[x] > variables[y]) ? 1 : 0);
                        variables[x] -= variables[y];
                        break;
                    case 0x6:
                        var shifted = (byte) (variables[x] & 0x1);
                        variables[x] >>= 1;
                        variables[0xF] = shifted;
                        break;
                    case 0x7:
                        variables[0xF] = (byte) ((variables[y] > variables[x]) ? 1 : 0);
                        variables[x] = (byte) (variables[y] - variables[x]);
                        break;
                    case 0xE:
                        shifted = (byte) (variables[x] & 0x80);
                        variables[x] <<= 1;
                        variables[0xF] = shifted;
                        break;
                    default:
                        break;
                }
                break;
            case 0x9000:
                if (variables[x] != variables[y]) pc += 2;
                break;
            case 0xA000:
                ir = (short) (opcode & 0x0FFF);
                break;
            case 0xB000:
                pc = (short) (opcode & 0xFFF + variables[0]);
                break;
            case 0xC000:
                variables[x] = (byte) (Math.round(Math.random() * 0xFF) & (opcode & 0xFF));
                break;
            case 0xD000:
                byte width = 8;
                byte height = (byte) (opcode & 0x000F);

                var xCord = variables[x] & 63;
                var yCord = variables[y] & 31;

                variables[0xF] = 0;

                for (int i = 0; i < height; i++) {
                    if (yCord + i > 31) break;

                    byte sprite = ram[ir + i];

                    for (int j = 0; j < width; j++) {
                        if (xCord + j > 63) break;

                        if ((sprite & 0x80) > 0) {
                            if (display.togglePixel(xCord + j, yCord + i)) {
                                variables[0xF] = 1;
                            }
                        }
                        sprite <<= 1;
                    }
                }
                break;
            case 0xE000:
                switch (opcode & 0x00FF) {
                    case 0x9E:
                        if (display.getPressedKey(variables[x])) pc += 2;
                        break;
                    case 0xA1:
                        if (!display.getPressedKey(variables[x])) pc += 2;
                        break;
                    default:
                        break;
                }
                break;
            case 0xF000:
                switch (opcode & 0x00FF) {
                    case 0x07:
                        variables[x] = delay;
                        break;
                    case 0x15:
                        delay = variables[x];
                        break;
                    case 0x18:
                        sound = variables[x];
                        break;
                    case 0x1E:
                        ir += variables[x];
                        break;
                    case 0x0A:
                        var key = display.getAnyPressedKey();
                        if (key.isPressed()) {
                            variables[x] = (byte) key.getKeyCode();
                        } else {
                            pc -= 2; 
                        }
                        break;
                    case 0x29:
                        ir = (short) (0x50 + variables[x] * 5);
                        break;
                    case 0x33:
                        var value = String.valueOf(Byte.toUnsignedInt(variables[x]));
                        for (int i = 0; i < 3; i++) {
                            ram[ir + i] = Byte.valueOf(value.charAt(2 - i)); //TODO
                        }
                        break;
                    default:
                        break;
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

        font = font.replaceAll(";", ", ");
        var fonts = font.split(", ");

        int i = 0x50;
        for (String f : fonts) {
            ram[i++] = (byte) Integer.decode(f).intValue();
        }
    }
}
