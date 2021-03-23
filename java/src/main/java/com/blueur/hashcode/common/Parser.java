package com.blueur.hashcode.common;


import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.collection.SortedMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Parser<I> {
    protected final Path path;

    protected Parser(Path path) {
        this.path = path;
    }

    public abstract I parseIterator(Iterator<String> fileIterator);

    public I parse() throws IOException {
        try (final Stream<String> stream = Files.lines(path)) {
            return parseIterator(stream.iterator());
        }
    }

    protected static <T> BiConsumer<T, String> integer(BiConsumer<T, Integer> setter) {
        return (t, s) -> setter.accept(t, Integer.parseInt(s));
    }

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

    @SafeVarargs
    protected static <T> Function1<Iterator<String>, Function1<Integer, T>> object(Function1<Integer, T> constructor, BiConsumer<T, Iterator<String>>... consumers) {
        return iterator -> index -> {
            final T t = constructor.apply(index);
            for (BiConsumer<T, Iterator<String>> consumer : consumers) {
                consumer.accept(t, iterator);
            }
            return t;
        };
    }

    @SafeVarargs
    protected static <T> BiConsumer<T, Iterator<String>> line(BiConsumer<T, Iterator<String>>... consumers) {
        return (t, iterator) -> {
            final Iterator<String> lineIterator = Arrays.stream(iterator.next().split(" ")).iterator();
            for (BiConsumer<T, Iterator<String>> consumer : consumers) {
                consumer.accept(t, lineIterator);
            }
        };
    }

    protected static <T> BiConsumer<T, Iterator<String>> field(BiConsumer<T, String> consumer) {
        return (t, iterator) -> consumer.accept(t, iterator.next());
    }

    protected static <T, U> BiConsumer<T, Iterator<String>> list(Function1<T, Integer> count, BiConsumer<T, List<U>> setter, Function1<T, Function1<Iterator<String>, Function1<Integer, U>>> function) {
        return (t, iterator) -> {
            final List<U> list = List.range(0, count.apply(t))
                    .map(index -> function.apply(t).apply(iterator).apply(index));
            setter.accept(t, list);
        };
    }

    protected static <T, K extends Comparable<? super K>, V> BiConsumer<T, Iterator<String>> sortedMap(Function1<T, Integer> count, BiConsumer<T, SortedMap<K, V>> setter, Function1<V, K> keyMapper, Function1<T, Function1<Iterator<String>, V>> function) {
        return (t, iterator) -> {
            final SortedMap<K, V> map = List.range(0, count.apply(t))
                    .map(integer -> function.apply(t).apply(iterator))
                    .toSortedMap(keyMapper, Function.identity());
            setter.accept(t, map);
        };
    }
}
