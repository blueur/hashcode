package com.blueur.hashcode.common;


import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.collection.SortedMap;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public abstract class Parser<I> {
    protected final Path path;
    protected final Function1<Iterator<String>, Function1<Integer, String>> string = iterator -> index -> iterator.next();

    protected Parser(Path path) {
        this.path = path;
    }

    public abstract I parseIterator(Iterator<String> fileIterator);

    public I parse() throws IOException {
        try (final Stream<String> stream = Files.lines(path)) {
            return parseIterator(stream.iterator());
        }
    }

    protected static Function1<String, String> STRING = Function1.identity();
    protected static Function1<String, Integer> INTEGER = Integer::parseInt;

    @SafeVarargs
    protected static <T> Function1<Iterator<String>, T> object(Supplier<T> constructor, BiConsumer<T, Iterator<String>>... consumers) {
        return iterator -> {
            final T t = constructor.get();
            for (BiConsumer<T, Iterator<String>> consumer : consumers) {
                consumer.accept(t, iterator);
            }
            return t;
        };
    }

    private static Iterator<String> lineIterator(Iterator<String> iterator) {
        return Arrays.stream(iterator.next().split(" ")).iterator();
    }

    @SafeVarargs
    protected static <T> BiConsumer<T, Iterator<String>> line(BiConsumer<T, Iterator<String>>... consumers) {
        return (t, iterator) -> {
            final Iterator<String> lineIterator = lineIterator(iterator);
            for (BiConsumer<T, Iterator<String>> consumer : consumers) {
                consumer.accept(t, lineIterator);
            }
        };
    }

    protected static <T, U> BiConsumer<T, Iterator<String>> list(Function1<T, Integer> count, BiConsumer<T, List<U>> setter, Function1<Iterator<String>, U> function) {
        return (t, iterator) -> {
            final List<U> list = List.range(0, count.apply(t))
                    .map(index -> function.apply(iterator));
            setter.accept(t, list);
        };
    }

    protected static <T1, T2> Function1<Iterator<String>, Tuple2<T1, T2>> lineTuple2(Function1<String, T1> consumer1, Function1<String, T2> consumer2) {
        return iterator -> {
            final Iterator<String> lineIterator = lineIterator(iterator);
            return Tuple.of(consumer1.apply(next(lineIterator)), consumer2.apply(next(lineIterator)));
        };
    }

    protected static <T> BiConsumer<T, Iterator<String>> string(BiConsumer<T, String> setter) {
        return (t, iterator) -> {
            setter.accept(t, next(iterator));
        };
    }

    private static String next(Iterator<String> iterator) {
        return iterator.next();
    }

    protected static <T> BiConsumer<T, Iterator<String>> integer(BiConsumer<T, Integer> setter) {
        return string((t, s) -> setter.accept(t, INTEGER.apply(s)));
    }

    @Deprecated
    protected static <T, U> BiConsumer<T, Iterator<String>> idList(Function1<T, Integer> count, BiConsumer<T, List<U>> setter, Function1<T, Function1<Iterator<String>, Function1<Integer, U>>> function) {
        return (t, iterator) -> {
            final List<U> list = List.range(0, count.apply(t))
                    .map(index -> function.apply(t).apply(iterator).apply(index));
            setter.accept(t, list);
        };
    }

    @Deprecated
    @SafeVarargs
    protected static <T> Function1<Iterator<String>, Function1<Integer, T>> idObject(Function1<Integer, T> constructor, BiConsumer<T, Iterator<String>>... consumers) {
        return iterator -> index -> {
            final T t = constructor.apply(index);
            for (BiConsumer<T, Iterator<String>> consumer : consumers) {
                consumer.accept(t, iterator);
            }
            return t;
        };
    }

    @Deprecated
    protected static <T, U> BiConsumer<T, Iterator<String>> set(Function1<T, Integer> count, BiConsumer<T, Set<U>> setter, Function1<T, Function1<Iterator<String>, Function1<Integer, U>>> function) {
        return idList(count, (t, list) -> setter.accept(t, list.toSortedSet()), function);
    }

    @Deprecated
    protected static <T, K extends Comparable<? super K>, V> BiConsumer<T, Iterator<String>> sortedMap(Function1<T, Integer> count, BiConsumer<T, SortedMap<K, V>> setter, Function1<V, K> keyMapper, Function1<T, Function1<Iterator<String>, V>> function) {
        return (t, iterator) -> {
            final SortedMap<K, V> map = List.range(0, count.apply(t))
                    .map(integer -> function.apply(t).apply(iterator))
                    .toSortedMap(keyMapper, Function.identity());
            setter.accept(t, map);
        };
    }
}
