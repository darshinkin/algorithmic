package ocp.streams;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.BinaryOperator;
import java.util.stream.*;

public class CreaterStreams {
    public static void main(String[] args) {
//        Stream<Integer> fromArray = createSimpleStreams();
//        infiniteStreams(fromArray);
//        streamsMethods();
//        reduce();
//        collect();
//        intermediateOperations();
//        sort();
//        peak();
//        pipeline();
//        primitiveStreams();
//        advancedConcepts();
//        collectors();
        maps();
    }

    private static void maps() {
        Stream<String> stream = Stream.of("lions", "tigers", "bears", "lions");
        Map<Boolean, Set<String>> result = stream
                .collect(Collectors.partitioningBy(str -> str.length() <= 7, Collectors.toSet()));
        System.out.println(result);

        Stream<String> stream2 = Stream.of("lions", "tigers", "bears", "lions");
        TreeMap<Integer, Set<String>> result2 = stream2
                .collect(Collectors.groupingBy(String::length,TreeMap::new, Collectors.toSet()));
        System.out.println(result2);

        Stream<String> stream3 = Stream.of("lions", "tigers", "bears", "lions");
        Map<Integer, Long> result3 = stream3
                .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));
        System.out.println(result3);

        /* todo some problems
        Stream<String> stream4 = Stream.of("lions", "tigers", "bears");
        Map<Integer, Optional<Character>> result4 = stream4.collect(
                Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(s -> s.charAt(0),
                                Collectors.minBy(Comparator.naturalOrder())
                        )
                )
        );
        System.out.println(result4);*/
    }

    private static void collectors() {
        Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
        String result = ohMy.collect(Collectors.joining(", "));
        System.out.println(result);

        Stream<String> ohMy1 = Stream.of("lions", "tigers", "bears");
        Double result2 = ohMy1.collect(Collectors.averagingInt(String::length));
        System.out.println(result2);
    }

    private static void advancedConcepts() {
//        threeDigit(Optional.of(612));
        threeDigit(Optional.empty());
    }

    private static void threeDigit(Optional<Integer> optional) {
        optional.map(n -> "" + n)
                .filter(str -> str.length()==3)
                .ifPresent(System.out::println);
    }

    private static void primitiveStreams() {
        IntStream intStream = IntStream.of(1, 2, 3);
        OptionalDouble avr = intStream.average();
        avr.ifPresent(System.out::println);
        System.out.println(avr.orElseGet(() -> Double.NaN));
        System.out.println(avr.getAsDouble());

        IntStream.range(1, 6).forEach(System.out::print);
        IntStream.rangeClosed(1, 5).forEach(System.out::print);

        LongStream longStream = LongStream.of(5L, 10L);
        long sumRes = longStream.sum();
        System.out.println(sumRes);
        DoubleStream doubleStream = DoubleStream.generate(() -> Math.PI);
        OptionalDouble min = doubleStream.min();
    }

    private static int range(IntStream stream) {
        IntSummaryStatistics statistics = stream.summaryStatistics();
        if (statistics.getCount() == 0) {
            throw new RuntimeException();
        }
        return statistics.getMax() - statistics.getMin();
    }


    private static void pipeline() {
        List<String> list = Arrays.asList("Tody", "Anna", "Leroy", "Alex");
        list.stream()
                .filter(str -> str.length() == 4)
                .sorted()
                .limit(2)
                .forEach(System.out::println);
        Stream<Integer> integerStream = Stream.iterate(1, i -> ++i);
        integerStream.peek(System.out::print).limit(5).peek(System.out::print).filter(i -> i%2==1).forEach(System.out::print);
    }

    private static void peak() {
        Stream<String> stream = Stream.of("black bear", "brown bear", "grizzly");
        long count = stream.filter(str -> str.startsWith("g")).peek(System.out::println).count();
        System.out.println(count);

        List<Integer> numbers = new ArrayList<>();
        List<Character> letters = new ArrayList<>();
        numbers.add(1);
        letters.add('a');
        Stream<List<?>> stream1 = Stream.of(numbers, letters);
        StringBuilder sb = new StringBuilder();
        stream1.peek(list -> sb.append(list)).map(List::size).forEach(System.out::print);
        System.out.println();
        System.out.println(sb);

        Stream<List<?>> stream2 = Stream.of(numbers, letters);
        stream2.peek(list -> list.remove(0)).map(List::size).forEach(System.out::print);
        System.out.println();
    }

    private static void sort() {
        Stream<String> stream = Stream.of("brown_", "bear_");
        stream.sorted().forEach(System.out::print);
        System.out.println();

        Stream<String> stream2 = Stream.of("brown bear-", "grizzly-");
        stream2.sorted(Comparator.reverseOrder()).forEach(System.out::print);
//        stream2.sorted(Comparator::reverseOrder).forEach(System.out::print);
        System.out.println();
    }

    private static void intermediateOperations() {
        // filter
        Stream<String> stream = Stream.of("monkey", "gorilla", "bonobo");
        stream.filter(x -> x.startsWith("m")).forEach(System.out::println);
        // distinct
        Stream<String> distinct = Stream.of("duck", "duck", "duck", "goose");
        distinct.distinct().forEach(System.out::print);
        System.out.println();
        // skip and limit
        Stream<Integer> integerStream = Stream.iterate(1, n -> ++n);
        integerStream.skip(5).limit(2).forEach(System.out::print);
        System.out.println();
        // map
        Stream<String> streamMap = Stream.of("monkey", "gorilla", "bonobo");
        streamMap.map(str -> str.length()).forEach(System.out::print);
        System.out.println();
        // flatMap
        List<String> zero = Arrays.asList();
        List<String> one = Arrays.asList("Bonobo");
        List<String> two = Arrays.asList("Mama gorilla", "Baby gorilla");
        Stream<List<String>> listStream = Stream.of(zero, one, two);
        listStream.flatMap(list -> list.stream()).forEach(System.out::println);
        System.out.println();
    }

    private static void collect() {
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        StringBuilder result = stream.collect(StringBuilder::new, (sb, s) -> sb.append(s), (sb1, sb2) -> sb1.append(sb2));
        System.out.println(result);
        StringBuilder sb1 = new StringBuilder("sb1");
        StringBuilder sb2 = new StringBuilder("sb1");
        System.out.println(sb1.append(sb2));

        Stream<String> stream2 = Stream.of("w", "o", "l", "f");
        TreeSet<String> resultTreeSet = stream2.collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
        System.out.println(resultTreeSet);

        Stream<String> stream3 = Stream.of("w", "o", "l", "f");
//        Set<String> set = stream3.collect(Collectors.toSet());
        Set<String> set = stream3.collect(Collectors.toCollection(ConcurrentSkipListSet::new));
        System.out.println(set);
    }

    private static void reduce() {
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        BinaryOperator<String> stringBinaryOperator = (a, b) -> a + b;
        stream.reduce(stringBinaryOperator).ifPresent(System.out::println);
        Stream<String> stream2 = Stream.of("w", "o", "l", "f");
        String wolf = stream2.reduce("", stringBinaryOperator);
        System.out.println(wolf);
        Stream<String> stream3 = Stream.of("w", "o", "l", "f");
        String s = stream3.reduce("", String::concat);
        System.out.println(s);

        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        int mult = integerStream.reduce(1, (i1, i2) -> i1*i2);
        System.out.println(mult);

        BinaryOperator<Integer> multiply = (a, b) -> a*b;
        Stream<Integer> empty = Stream.empty();
        Stream<Integer> oneElement = Stream.of(1);
        Stream<Integer> threeElements = Stream.of(1, 2, 3);
        empty.reduce(multiply).ifPresent(System.out::println);
        oneElement.reduce(multiply).ifPresent(System.out::println);
        threeElements.reduce(multiply).ifPresent(System.out::println);

        Stream<Integer> st = Stream.of(2, 4, 6);
        Integer reduce = st.parallel().reduce(1, multiply, multiply);
        System.out.println(reduce);
    }

    private static void streamsMethods() {
        Stream<String> s = Stream.of("monkey", "ape", "bonobo");
        Optional<String> min = s.min((s1, s2) -> s1.length()-s2.length());
        min.ifPresent(System.out::println);
        Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
        System.out.println(minEmpty.isPresent());

        Stream<String> s2 = Stream.of("monkey", "gorilla", "bonobo");
        Stream<String> infinite = Stream.generate(() -> "chimp");
        s2.findAny().ifPresent(System.out::println);
        infinite.findAny().ifPresent(System.out::println);
    }

    private static void infiniteStreams(Stream<Integer> fromArray) {
        Stream<Double> randoms = Stream.generate(Math::random);
        Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 1);
        oddNumbers.forEach(System.out::println);

        System.out.println(fromArray.count());
    }

    private static Stream<Integer> createSimpleStreams() {
        Stream<String> empty = Stream.empty();
        Stream<Integer> singleElement = Stream.of(1);
        Stream<Integer> fromArray = Stream.of(1, 2, 3);

        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> fromList = list.stream();
        Stream<String> fromListParallel = list.parallelStream();
        return fromArray;
    }
}
