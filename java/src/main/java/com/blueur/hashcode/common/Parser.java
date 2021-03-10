package com.blueur.hashcode.common;


import io.vavr.collection.List;
import io.vavr.collection.Stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.vavr.API.unchecked;

public abstract class Parser<I> {
    protected final Path path;
    protected final BufferedReader reader;

    protected Parser(Path path) throws IOException {
        this.path = path;
        this.reader = Files.newBufferedReader(path);
    }

    public abstract I parse() throws IOException;

    private static <T> void parseLine(String line, T t, BiConsumer<T, String>[] setters) {
        final String[] splitLine = line.split(" ");
        List.of(setters)
                .zip(List.of(splitLine))
                .forEach(tuple -> tuple._1.accept(t, tuple._2));
    }

    @SafeVarargs
    protected final void readLine(I input, BiConsumer<I, String>... setters) throws IOException {
        parseLine(this.reader.readLine(), input, setters);
    }

    protected <T> void readLines(I input, BiConsumer<I, List<T>> setter, int linesCount, BiFunction<Integer, String, T> parseElement) {
        final List<T> elements = Stream.range(0, linesCount)
                .map(unchecked(i -> parseElement.apply(i, this.reader.readLine())))
                .toList();
        setter.accept(input, elements);
    }

    protected static <T> BiConsumer<T, String> integer(BiConsumer<T, Integer> setter) {
        return (t, s) -> setter.accept(t, Integer.parseInt(s));
    }

    @SafeVarargs
    protected static <T> BiFunction<Integer, String, T> parseElement(Function<Integer, T> creator, BiConsumer<T, String>... setters) {
        return (i, line) -> {
            final T t = creator.apply(i);
            parseLine(line, t, setters);
            return t;
        };
    }

    protected static <T, U> BiFunction<Integer, String, T> parseElementList(Function<Integer, T> creator, BiConsumer<T, List<U>> setter, Function<String, U> mapper) {
        return (i, line) -> {
            final T t = creator.apply(i);
            List<U> us = List.of(line.split(" "))
                    .tail()
                    .map(mapper);
            setter.accept(t, us);
            return t;
        };
    }

    protected <T> void fillList(I input, BiConsumer<I, List<T>> setter, int count, Function<Integer, T> creator) {
        final List<T> ts = Stream.range(0, count)
                .map(creator)
                .toList();
        setter.accept(input, ts);
    }
}
