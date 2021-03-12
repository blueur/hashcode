package com.blueur.hashcode.dummy;

import com.blueur.hashcode.common.Parser;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RawParser extends Parser<List<String>> {

    public RawParser(Path path) {
        super(path);
    }

    @Override
    public List<String> parseIterator(Iterator<String> fileIterator) {
        final Iterable<String> iterable = () -> fileIterator;
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
