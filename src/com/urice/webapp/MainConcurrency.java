package com.urice.webapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    //    private static final Object LOCK = new Object();
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    private static final Lock READ_LOCK = reentrantReadWriteLock.readLock();
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyy HH.mm.ss");


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);

//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() ->
//            Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    LocalDateTime localDateTime = LocalDateTime.now();
                    System.out.println(dateTimeFormatter.format(localDateTime));
                }
                latch.countDown();
                return 5;
            });

//            thread.start();
//            threads.add(thread);
        }

//        threads.forEach(thread -> {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(mainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());
    }

    private void inc() {
        atomicCounter.incrementAndGet();
//        double a = Math.sin(15.);
//        synchronized (MainConcurrency.class{
//        synchronized (this){
//        WRITE_LOCK.lock();
//        try {
//            counter++;
//        } finally {
//            WRITE_LOCK.unlock();
//        }


//        }

    }
}
