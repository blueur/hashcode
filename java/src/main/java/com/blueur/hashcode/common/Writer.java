package com.blueur.hashcode.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Writer<O> {
    protected final Path path;

    protected Writer(Path path) {
        this.path = path;
    }

    protected abstract void write(PrintWriter writer, O output);

    public void write(O output) throws IOException {
        try (final PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(path))) {
            write(printWriter, output);
        }
    }
}
