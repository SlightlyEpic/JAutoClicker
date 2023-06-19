package me.slightlyepic;

import java.awt.*;

public class Clicker {
    public boolean isClicking;
    private int delayMs;
    private Robot robot;

    private ClickerThread runnerThread;

    public Clicker(int cps) {
        isClicking = false;
        delayMs = 1000 / cps;
        try {
            robot = new Robot();
        } catch(AWTException ex) {
            System.err.println("Failed to create robot!");
            return;
        }

        runnerThread = new ClickerThread(robot, delayMs);
        runnerThread.getMutex().lock();
        runnerThread.setPriority(Thread.MAX_PRIORITY);
        runnerThread.start();
    }

    public void startClicking() {
        isClicking = true;
        runnerThread.getMutex().unlock();
    }

    public void stopClicking() {
        isClicking = false;
        runnerThread.getMutex().lock();
    }

    public boolean getIsClicking() {
        return isClicking;
    }
}
