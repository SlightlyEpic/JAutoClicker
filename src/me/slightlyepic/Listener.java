package me.slightlyepic;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class Listener implements NativeKeyListener, NativeMouseListener {
    private final int altKeyCode = 56;
    private final int mKeyCode = 50;
    private boolean isActive;
    private boolean altPressed;
    private int mousedownCount;
    private int mouseupCount;
    private Clicker clicker;

    public Listener(int cps) {
        isActive = false;
        altPressed = false;
        mousedownCount = 0;
        mouseupCount = 0;
        clicker = new Clicker(cps);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == altKeyCode) altPressed = true;
        else if(altPressed && keyCode == mKeyCode) {
            if(isActive) {
                isActive = false;
                System.out.println("Deactivated!");
            } else {
                isActive = true;
                System.out.println("Activated");
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == altKeyCode) altPressed = false;
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        mousedownCount++;
        if(isActive && !clicker.isClicking) {
            System.out.println("Start clicking...");
            clicker.startClicking();
        }
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        mouseupCount++;
        if(isActive && mousedownCount == mouseupCount) {
            System.out.println("Stop clicking...");
            clicker.stopClicking();
        }
    }

    public void setIsActive(boolean value) {
        isActive = value;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
