package com.urice.webapp;

public class MainDeadLock {
    int counter = 0;

    public static void main(String[] args) {
        MainDeadLock mainDeadLock0 = new MainDeadLock();
        MainDeadLock mainDeadLock1 = new MainDeadLock();

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    System.out.print(getName() + " - ");
                    mainDeadLock0.deadLock(mainDeadLock0, mainDeadLock1);
                }

            }
        };
        thread0.start();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    System.out.print(getName() + " - ");
                    mainDeadLock1.deadLock(mainDeadLock1, mainDeadLock0);
                }

            }
        };
        thread1.start();
    }

    public void deadLock(MainDeadLock from, MainDeadLock to) {
        synchronized (from) {
            synchronized (to) {
                System.out.println(counter);
                counter++;
            }
        }
    }
}
