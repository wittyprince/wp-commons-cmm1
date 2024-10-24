package io.github.wittyprince.commons.cmm1.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

/**
 * list util
 *
 * @author WangChen
 * Created on 2022/11/14
 * @since 1.0
 */
public class ListUtil {

    public static <R, T> List<R> toList(List<T> list, Function<T, R> function) {
        return stream(list).map(function).collect(Collectors.toList());
    }

    public static <R, T> List<R> toList(T[] array, Function<T, R> function) {
        return Arrays.stream(array).map(function).collect(Collectors.toList());
    }

    public static <T, K> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper) {
        return stream(list).collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    public static <T, K, V> Map<K, V> toMap(List<T> list, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return stream(list).collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public static <K, T> Map<K, List<T>> group(List<T> list, Function<T, K> function) {
        return stream(list).collect(Collectors.groupingBy(function));
    }

    public static <K, T, R> Map<K, List<R>> group(List<T> list, Function<T, K> function, Function<T, R> mapper) {
        if (function == null) {
            throw new RuntimeException("function must not be null!");
        }
        if (mapper == null) {
            throw new RuntimeException("mapper must not be null!");
        }
        return stream(list).collect(groupingBy(function, mapping(mapper, Collectors.toList())));
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return stream(list).filter(predicate).collect(Collectors.toList());
    }

    public static <T, R> List<R> filter(List<T> list, Predicate<T> predicate, Function<T, R> function) {
        return stream(list).filter(predicate).map(function).collect(Collectors.toList());
    }

    public static <T> void forEach(List<T> list, Consumer<? super T> action) {
        stream(list).forEach(action);
    }

    @SafeVarargs
    public static <T> List<T> merge(List<T>... list) {
        return Stream.of(list).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        return stream(list1).filter(list2::contains).distinct().collect(Collectors.toList());
    }

    private static <T> Stream<T> stream(List<T> list) {
        return list == null ? Stream.empty() : list.stream();
    }
}
