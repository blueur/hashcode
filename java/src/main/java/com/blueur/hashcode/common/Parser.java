package com.blueur.hashcode.common;

import java.io.IOException;
import java.nio.file.Path;

public abstract class Parser<I> {
    public abstract I parse(Path path) throws IOException;
}
