package ocp.optional;

import java.util.Optional;

public class Optionals {
    public static void main(String[] args) {
//        examples();
        Optional<Double> average = averrage();
        System.out.println(average.orElse(Double.NaN));
        System.out.println(average.orElseGet(() -> Math.random()));
        System.out.println(average.orElseThrow(() -> new IllegalStateException("Scores don't exist")));
    }

    private static void examples() {
        String value = "test";
        Optional<String> o1 = Optional.ofNullable(value);
        System.out.println(o1.get());
        Optional<String> o2 = Optional.ofNullable(null);
        o2.ifPresent(str -> System.out.println(str));
        o2.ifPresent(System.out::println);
        System.out.println(o2.orElse("default"));
        System.out.println(o2.orElseGet(() -> "From supplier"));
        System.out.println(o2.orElseThrow(() -> new RuntimeException("There is not value!!!")));
    }

    private static Optional<Double> averrage(int... scores) {
        if (scores.length == 0) {
            return Optional.empty();
        } else {
            int sum = 0;
            for (int i : scores) {
                sum += i;
            }
            return Optional.of((double) sum/scores.length);
        }
    }
}
