package com.blueur.hashcode.common;

import io.vavr.CheckedFunction1;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Value
@Builder
public class Executor<I, O> {
    private static final String OUTPUT_FOLDER = "output";

    @NonNull
    String folder;
    @NonNull
    CheckedFunction1<Path, Parser<I>> parser;
    @NonNull
    Solver<I, O> solver;
    @NonNull
    Writer<O> writer;

    public void execute(String... inputs) throws Throwable {
        final Path outputFolder = Path.of(folder, OUTPUT_FOLDER);
        Files.createDirectories(outputFolder);
        for (String input : inputs) {
            final Path inputPath = Path.of(folder, input).toAbsolutePath().normalize();
            log.info(inputPath.toString());
            final I i = parser.apply(inputPath).parse();
            final O o = solver.solve(i);
            writer.write(Path.of(outputFolder.toString(), input), o);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void executeAll() throws Throwable {
        final File file = new File(folder);
        execute(file.list((dir, name) -> name.endsWith(".txt")));
    }
}
