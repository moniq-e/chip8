package com.monique.chip8;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {
    public boolean[] pressedKeys;

    public KeyHandler() {
        pressedKeys = new boolean[0x10]; //16
    }

    @Override
    public void keyPressed(KeyEvent e) {
        var key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_1:
                pressedKeys[0x1] = true;
                break;
            case KeyEvent.VK_2:
                pressedKeys[0x2] = true;
                break;
            case KeyEvent.VK_3:
                pressedKeys[0x3] = true;
                break;
            case KeyEvent.VK_4:
                pressedKeys[0xC] = true;
                break;
            case KeyEvent.VK_Q:
                pressedKeys[0x4] = true;
                break;
            case KeyEvent.VK_W:
                pressedKeys[0x5] = true;
                break;
            case KeyEvent.VK_E:
                pressedKeys[0x6] = true;
                break;
            case KeyEvent.VK_R:
                pressedKeys[0xD] = true;
                break;
            case KeyEvent.VK_A:
                pressedKeys[0x7] = true;
                break;
            case KeyEvent.VK_S:
                pressedKeys[0x8] = true;
                break;
            case KeyEvent.VK_D:
                pressedKeys[0x9] = true;
                break;
            case KeyEvent.VK_F:
                pressedKeys[0xE] = true;
                break;
            case KeyEvent.VK_Z:
                pressedKeys[0xA] = true;
                break;
            case KeyEvent.VK_X:
                pressedKeys[0x0] = true;
                break;
            case KeyEvent.VK_C:
                pressedKeys[0xB] = true;
                break;
            case KeyEvent.VK_V:
                pressedKeys[0xF] = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        var key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_1:
                pressedKeys[0x1] = false;
                break;
            case KeyEvent.VK_2:
                pressedKeys[0x2] = false;
                break;
            case KeyEvent.VK_3:
                pressedKeys[0x3] = false;
                break;
            case KeyEvent.VK_4:
                pressedKeys[0xC] = false;
                break;
            case KeyEvent.VK_Q:
                pressedKeys[0x4] = false;
                break;
            case KeyEvent.VK_W:
                pressedKeys[0x5] = false;
                break;
            case KeyEvent.VK_E:
                pressedKeys[0x6] = false;
                break;
            case KeyEvent.VK_R:
                pressedKeys[0xD] = false;
                break;
            case KeyEvent.VK_A:
                pressedKeys[0x7] = false;
                break;
            case KeyEvent.VK_S:
                pressedKeys[0x8] = false;
                break;
            case KeyEvent.VK_D:
                pressedKeys[0x9] = false;
                break;
            case KeyEvent.VK_F:
                pressedKeys[0xE] = false;
                break;
            case KeyEvent.VK_Z:
                pressedKeys[0xA] = false;
                break;
            case KeyEvent.VK_X:
                pressedKeys[0x0] = false;
                break;
            case KeyEvent.VK_C:
                pressedKeys[0xB] = false;
                break;
            case KeyEvent.VK_V:
                pressedKeys[0xF] = false;
                break;
            default:
                break;
        }
    }
}
