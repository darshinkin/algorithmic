package lessons.concurrency;

import java.util.Date;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinFramework {
    static long numOfOperations = 10_000_000_000L;

    static int numOfThread = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        System.out.println(numOfThread);
        long start = new Date().getTime();
        ForkJoinPool pool = new ForkJoinPool(numOfThread);
        System.out.println(pool.invoke(new MyFork(0, numOfOperations)));
//        long j = 0;
//        for (long i = 0; i < numOfOperations; ++i) {
//            j += i;
//        }
//        System.out.println(j);
        System.out.println(new Date().getTime() - start);
    }

    static class MyFork extends RecursiveTask<Long> {

        long from, to;

        public MyFork(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            if ((to - from) <= numOfOperations/numOfThread) {
                long j = 0;
                for (long i = from; i < to; i++) {
                    j += i;
                }
                return j;
            } else {
                long middle = (to + from) / 2;
                MyFork firstHalf = new MyFork(from, middle);
                firstHalf.fork();
                MyFork secondHalf = new MyFork(middle+1, to);
                Long secondValue = secondHalf.compute();
                return firstHalf.join() + secondValue;
            }
        }
    }
}
