package ocp.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrentCollections {
    public static void main(String[] args) {
        blockingQueue();
    }

    private static void blockingQueue() {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(32);
        try {
            queue.offer(2, 1, TimeUnit.MICROSECONDS);

            System.out.println(queue.poll());
            System.out.println(queue.poll(2, TimeUnit.MICROSECONDS));
        } catch (InterruptedException e) {
            System.out.println("Error occured during queue working.");
        }
    }
}
