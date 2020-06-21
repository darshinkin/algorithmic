package lessons.streams;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ParallelStream {
    public static void main(String[] args) {
        int capacity = 10_000_000;
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            list.add(i);
            list.add(capacity - i);
        }
        calculateStream(list);
        calculateParallelStream(list);
    }

    private static void calculateParallelStream(List<Integer> list) {
        System.out.print("Parallel stream: ");
        Date start = new Date();
        list.parallelStream().filter(x -> x%2 == 0).map(Double::new).forEach(Object::toString);
        System.out.println(new Date().getTime() - start.getTime() );
    }

    private static void calculateStream(List<Integer> list) {
        System.out.print("Single stream: ");
        Date start = new Date();
        list.stream().filter(x -> x%2 == 0).map(Double::new).forEach(Object::toString);
        System.out.println(new Date().getTime() - start.getTime() );
    }
}
