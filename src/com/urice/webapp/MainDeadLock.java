package com.urice.webapp;

public class MainDeadLock {
    private static final Object LOCK0 = new Object();
    private static final Object LOCK1 = new Object();

    public static void main(String[] args) {
        MainDeadLock mainDeadLock = new MainDeadLock();

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                mainDeadLock.deadLock(getName(), LOCK0, LOCK1);
            }
        };
        thread0.start();
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                mainDeadLock.deadLock(getName(), LOCK1, LOCK0);
            }
        };
        thread1.start();
    }

    public void deadLock(String threadName, Object lock0, Object lock1) {
        synchronized (lock0) {
            System.out.println(threadName + " - hold first synchronized block");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(threadName + " - waiting second synchronized block");
            }
            synchronized (lock1) {
                System.out.println(threadName + " - hold second synchronized block");
            }
        }
    }
}

