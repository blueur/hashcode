package com.blueur.hashcode.dummy;

import com.blueur.hashcode.common.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RawParser extends Parser<List<String>> {

    public RawParser(Path path) throws IOException {
        super(path);
    }

    @Override
    public List<String> parse() throws IOException {
        return Files.readAllLines(this.path);
    }
}
