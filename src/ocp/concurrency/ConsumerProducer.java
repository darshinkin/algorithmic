package ocp.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class ConsumerProducer {

    private static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
    private static Queue<Integer> queueNotThreadSafe = new LinkedList<>();

    public static void main(String[] args) {
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        Object lock = new Object();
        ProducerSynch producerSynch = new ProducerSynch(queueNotThreadSafe, lock);
        ConsumerSynch consumerSynch = new ConsumerSynch(queueNotThreadSafe, lock);

        ExecutorService serviceProducer = null;
        ExecutorService service = null;
        try {
            serviceProducer = Executors.newFixedThreadPool(10);
            serviceProducer.submit(producer);
//                serviceProducer.submit(producerSynch);

            service = Executors.newFixedThreadPool(1);
            service.submit(consumer);
//                service.submit(consumerSynch);
        } finally {
            if (serviceProducer != null) {
                serviceProducer.shutdown();
            }
            if (service != null) {
                service.shutdown();
            }
        }

    }
}

class ConsumerSynch implements Runnable {
    private final Queue<Integer> queue;
    private final Object lock;

    public ConsumerSynch(Queue<Integer> queue, Object lock) {
        this.queue = queue;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    if (queue.isEmpty()) {
                        System.out.println("Queue is empty");
                        lock.wait();
                    } else {
                        int i = queue.poll();
                        System.out.println("pool: " + i);
                        TimeUnit.SECONDS.sleep(1);
                        lock.notifyAll();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Error during consumer waiting");
                }
            }
        }
    }
}

class ProducerSynch implements Runnable {
    private final Queue<Integer> queue;
    private final Object lock;

    public ProducerSynch(Queue<Integer> queue, Object lock) {
        this.queue = queue;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    if (queue.size() < 2) {
                        int i = ThreadLocalRandom.current().nextInt();
                        queue.offer(i);
                        System.out.println("offer: " + i);
                        TimeUnit.SECONDS.sleep(1);
                        lock.notifyAll();
                    } else {
                        System.out.println("Queue is full");
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Exception during producer waiting: " + e.getMessage());
                }
            }
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer i = queue.poll(10, TimeUnit.SECONDS);
                System.out.println("poll: " + i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Error occured during polling from queue.");
            }
        }
    }
}

class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;

    Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int i = ThreadLocalRandom.current().nextInt();
                queue.offer(i, 1, TimeUnit.MICROSECONDS);
                System.out.println("offer: " + i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Error occured during polling from queue.");
            }
        }
    }
}