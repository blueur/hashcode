package com.blueur.hashcode.common;

import java.io.IOException;
import java.nio.file.Path;

public abstract class Writer<O> {
    public abstract void write(Path path, O output) throws IOException;
}
