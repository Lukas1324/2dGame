package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public  boolean upPressed, downPressed, leftPressed, rightPressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_KP_DOWN){
            downPressed = true;
        }if (code == KeyEvent.VK_KP_UP){
            upPressed = true;
        }if (code == KeyEvent.VK_KP_LEFT){
            leftPressed = true;
        }if (code == KeyEvent.VK_KP_RIGHT){
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_KP_DOWN){
            downPressed = false;
        }if (code == KeyEvent.VK_KP_UP){
            upPressed = false;
        }if (code == KeyEvent.VK_KP_LEFT){
            leftPressed = false;
        }if (code == KeyEvent.VK_KP_RIGHT){
            rightPressed = false;
        }
    }
}
