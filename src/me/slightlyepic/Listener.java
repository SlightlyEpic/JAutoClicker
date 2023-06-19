package me.slightlyepic;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Callable;

public class Listener implements NativeKeyListener, NativeMouseListener {
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private final int altKeyCode = 56;
    private final int mKeyCode = 50;
    private boolean isActive;
    private boolean altPressed;
    private int mousedownCount;
    private int mouseupCount;
    private Clicker clicker;

    private Callable<Integer> onActivatedCallback;

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
                setIsActive(false);
                System.out.println("Deactivated!");
            } else {
                setIsActive(true);
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

    public void setIsActive(boolean isActive) {
        boolean oldValue = this.isActive;
        this.isActive = isActive;
        propertyChangeSupport.firePropertyChange("isActive", oldValue, isActive);
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setCps(int cps) {
        this.clicker.setDelayMs(1000 / cps);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
