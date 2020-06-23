package ocp.concurrency;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizingDataAccess {
    public static void main(String[] args) {
//        raceConditions();
//        atomicInteger();
        synchronizedObject();
//        mapWithBoxing();
    }

    private static void synchronizedObject() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            SheepManagerThreaSafeWithSynchronized manager = new SheepManagerThreaSafeWithSynchronized();
            for (int i = 0; i < 10; i++) {
                service.submit(manager::incrementAndReport);
            }
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        System.out.println();
    }

    private static void atomicInteger() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            SheepManagerThreadSafe sheepManagerThreadSafe = new SheepManagerThreadSafe();
            for (int i = 0; i < 10; i++) {
                service.submit(sheepManagerThreadSafe::incrementAndReport);
            }
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        System.out.println();
    }

    private static void mapWithBoxing() {
        Object o = new Object();
        Map map = Map.of(1, 2, o, 3);
        System.out.println(map.get(o));
    }

    private static void raceConditions() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            SheepManager sheepManager = new SheepManager();
            for (int i = 0; i < 10; i++) {
                service.submit(sheepManager::incrementAndReport);
            }
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        System.out.println();
    }

    private static void printDaysWork() {
        synchronized (SheepManager.class) {
            System.out.println("Finished work");
        }
    }
}

class SheepManager {
    private int sheepCount = 0;
    public void incrementAndReport() {
        System.out.print((++sheepCount) + " ");
    }
}

class SheepManagerThreadSafe {
    private AtomicInteger sheepCount = new AtomicInteger(0);
    public void incrementAndReport() {
        System.out.print((sheepCount.incrementAndGet()) + " ");
    }
}

class SheepManagerThreaSafeWithSynchronized {
    private int sheepCount = 0;
    public void incrementAndReport() {
        synchronized (this) {
            System.out.print((++sheepCount) + " ");
        }
    }
}

