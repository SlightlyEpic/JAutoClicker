package me.slightlyepic;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class ClickerThread extends Thread {
    private Robot robot;
    private int delayMs;
    private Random rd;
    private final Mutex mutex;

    public ClickerThread(Robot robot, int delayMs) {
        this.robot = robot;
        this.delayMs = delayMs;
        this.rd = new Random();
        this.mutex = new Mutex(false);
    }

    public Mutex getMutex() {
        return this.mutex;
    }

    public void setDelayMs(int delayMs) {
        this.delayMs = delayMs;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            mutex.step();

            // do your code
            // System.out.println("isClicking: " + "isClicking");
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            /*
            try {
                Thread.sleep(delayMs);
            } catch(InterruptedException ex) {
                System.err.println("Interrupted!");
            }
             */
            robot.delay((int) (delayMs * (rd.nextFloat() / 5 + 0.9)));      // +- 10% of delay

            // System.out.println("Hello world");
        }
    }
}
