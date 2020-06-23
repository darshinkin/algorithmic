package ocp.concurrency;

import java.util.concurrent.*;

public class ExecutorsSamples {

    private static int counter = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        executeMethod();
//        submitMethod();
//        schedulingTasks();
        scheduledThreadPool();
    }

    private static void scheduledThreadPool() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("availableProcessors = " + availableProcessors);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(availableProcessors);
        service.scheduleAtFixedRate(() -> System.out.println("Scheduled"), 3, 1, TimeUnit.MINUTES);
    }

    private static void schedulingTasks() throws ExecutionException, InterruptedException {
        ScheduledExecutorService service = null;
        try {
            service = Executors.newSingleThreadScheduledExecutor();

            ScheduledFuture<?> res = service.schedule(() -> "Monkey", 3, TimeUnit.SECONDS);
            while(res.getDelay(TimeUnit.SECONDS) > 0) {
                System.out.println("Remain time: " + res.getDelay(TimeUnit.SECONDS));
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Result: " + res.get());

            service.scheduleAtFixedRate(() -> System.out.println("Run task!"), 100, 100, TimeUnit.MICROSECONDS);

        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        if (service != null) {
            service.awaitTermination(1, TimeUnit.MICROSECONDS);
            if (service.isShutdown()) {
                System.out.println("All tasks finished.");
            }
        }
    }

    private static void submitMethod() throws ExecutionException, InterruptedException {
        ExecutorService service = null;
        Future<Integer> result = null;
        try {
            service = Executors.newSingleThreadExecutor();
            result = service.submit(() -> {
                for (int i = 0; i < 5; i++) {
                    ExecutorsSamples.counter++;
                    TimeUnit.MICROSECONDS.sleep(100);
                    boolean interrupted = Thread.currentThread().isInterrupted();
                    if (interrupted) {
                        System.out.println("Task. isInterrupted(): " + interrupted);
                    }
                }
                return counter;
            });
            int count = result.get(10, TimeUnit.SECONDS);
            System.out.println("Reached! : " + count);
        } catch (TimeoutException e) {
            System.out.println("Not reached in time");
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        if(service != null) {
            service.awaitTermination(1, TimeUnit.MICROSECONDS);
            if (service.isTerminated()) {
                System.out.println("All tasks finished");
            } else {
                System.out.println("At least one task is still running.");
                System.out.println("Future. isCanceled: " + result.isCancelled()+", isDone: "+result.isDone());
                if (result != null && !result.isDone()) {
                    result.cancel(true);
                    System.out.println("Future. isCanceled: " + result.isCancelled()+", isDone: "+result.isDone());
                }
                while(result.isDone() || result.isCancelled()) {
                    System.out.println("After cancele. Future. isCanceled: " + result.isCancelled()+", isDone: "+result.isDone());
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        }
    }

    private static void executeMethod() {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            System.out.println("begin");
            service.execute(() -> System.out.println("Printing zoo invetories"));
            service.execute(() -> {
                for (int i = 0; i < 3; ++i) {
                    System.out.println("Printing record " + i);
                }
            });
            service.execute(() -> System.out.println("Printing zoo invetories"));
            System.out.println("end");
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }
}
