package com.blueur.hashcode.dummy;

import com.blueur.hashcode.common.Writer;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

public class RawWriter extends Writer<List<String>> {
    public RawWriter(Path path) {
        super(path);
    }

    @Override
    protected void write(PrintWriter writer, List<String> output) {
        output.forEach(writer::println);
    }
}
