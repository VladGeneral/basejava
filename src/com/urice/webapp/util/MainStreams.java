package com.urice.webapp.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreams {
    public static void main(String[] args) {
        MainStreams mainStreams = new MainStreams();

        int[] array = new int[]{1, 2, 3, 3, 2, 3};
//        int[] array = new int[]{9,8};
        List<Integer> list = Arrays.asList(10, 20, 51, 19);
        System.out.println(mainStreams.minValue(array));
        System.out.println("--------");
        list = mainStreams.oddOrEven(list);
        list.forEach(System.out::println);

    }

    int minValue(int[] values) {
        int result = 0;
        return IntStream.of(values).distinct().sorted().reduce(0,(a,b) -> a*10 +b);

    }

    List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, Integer::sum);
        if (sum % 2 != 0) {
            return setPredicate(integers, x -> x % 2 == 0);
        } else {
            return setPredicate(integers, x -> x % 2 != 0);
        }
    }

    List<Integer> setPredicate(List<Integer> integers, Predicate<Integer> predicate) {
        return integers.stream().filter(predicate).collect(Collectors.toList());
    }
}
