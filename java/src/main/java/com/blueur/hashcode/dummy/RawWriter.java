package com.blueur.hashcode.dummy;

import com.blueur.hashcode.common.Writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RawWriter extends Writer<List<String>> {
    @Override
    public void write(Path path, List<String> output) throws IOException {
        Files.write(path, output);
    }
}
