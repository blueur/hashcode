package com.blueur.hashcode.dummy;

import com.blueur.hashcode.common.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RawParser extends Parser<List<String>> {

    @Override
    public List<String> parse(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
