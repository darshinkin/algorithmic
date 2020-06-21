package ocp.lambdas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.*;

public class FunctionInterfaces {
    public static void main(String[] args) {
//        supplyInterface();
//        consumerInterface();
//        predicateInterface();
//        functionInterface();
        unaryAndBinaryOperator();
    }

    private static void unaryAndBinaryOperator() {
        UnaryOperator<String> u1 = String::toUpperCase;
        UnaryOperator<String> u2 = str -> str.toUpperCase();
        System.out.println(u1.apply("check"));
        System.out.println(u2.apply("price"));

        BinaryOperator<String> b1 = String::concat;
        BinaryOperator<String> b2 = (s1, s2) -> s1.concat(s2);
        System.out.println(b1.apply("baby", "chick"));
        System.out.println(b1.apply("baby", "chick"));
    }

    private static void functionInterface() {
        Function<String, Integer> f1 = String::length;
        Function<String, Integer> f2 = str -> str.length();
        System.out.println(f1.apply("check"));
        System.out.println(f1.apply("cluck"));

        BiFunction<String, String, String> bf1 = String::concat;
        BiFunction<String, String, String> bf2 = (s1, s2) -> s1.concat(s2);
        System.out.println(bf1.apply("baby", "chack"));
        System.out.println(bf1.apply("baby", "click"));
    }

    private static void predicateInterface() {
        Predicate<String> p1 = String::isEmpty;
        Predicate<String> p2 = str -> str.isEmpty();
        System.out.println(p1.test(""));
        System.out.println(p1.test(" "));

        BiPredicate<String, String> bp1 = String::startsWith;
        BiPredicate<String, String> bp2 = (string, prefix) -> string.startsWith(prefix);
        System.out.println(bp1.test("chicken", "chick"));
        System.out.println(bp2.test("chicken", "unchick"));

        Predicate<String> egg = s -> s.contains("egg");
        Predicate<String> brown = s -> s.contains("brown");
        Predicate<String> brownEggs = egg.and(brown);
        Predicate<String> otherEggs = egg.and(brown.negate());
    }

    private static void consumerInterface() {
        Consumer<User> c1 = System.out::println;
        Consumer<String> c2 = x -> System.out.println(x);

        c1.accept(new User("Ivan", "Ivanov"));
        c2.accept("Annie");

        Consumer<User> c3 = user -> user.setLastName(user.lastName + "a");
        Consumer<User> userConsumer = c3.andThen(c1);
        userConsumer.accept(new User("Ivan", "Ivanov"));

        Map<String, Integer> map = new HashMap<>();
        BiConsumer<String, Integer> b1 = map::put;
        BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);
        b1.accept("chicken", 7);
        b2.accept("chick", 1);
        System.out.println(map);
    }

    private static void supplyInterface() {
        Supplier<LocalDate> s1 = LocalDate::now;
        Supplier<LocalDate> s2 = () -> LocalDate.now();

        LocalDate d1 = s1.get();
        LocalDate d2 = s2.get();

        System.out.println(d1);
        System.out.println(d2);

        Supplier<StringBuffer> sb1 = StringBuffer::new;
        Supplier<StringBuilder> sb2 = () -> new StringBuilder();

        Supplier<ArrayList<String>> arList = ArrayList::new;
        ArrayList<String> strings = arList.get();
        System.out.println("Arrays: " + strings);
        System.out.println("Lambda of array of strings: " + arList);
    }
}

class User {
    String firstName;
    String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}