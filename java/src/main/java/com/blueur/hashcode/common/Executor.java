package com.blueur.hashcode.common;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.zip.ZipOutputStream;

@Slf4j
@Value
public class Executor<I, O> {
    private static final String OUTPUT_FOLDER = "output";
    private static final String SOURCE_ZIP_FILE = "source.zip";

    @NonNull
    Class<?> application;
    @NonNull
    Path problemPath;
    @NonNull
    Function<Path, Parser<I>> parser;
    @NonNull
    Supplier<Solver<I, O>> solver;
    @NonNull
    Function<Path, Writer<O>> writer;
    @NonNull
    Path outputPath;

    @Builder
    public Executor(@NonNull Class<?> application,
                    @NonNull String problemFolder,
                    @NonNull Function<Path, Parser<I>> parser,
                    @NonNull Supplier<Solver<I, O>> solver,
                    @NonNull Function<Path, Writer<O>> writer) throws IOException {
        this.application = application;
        this.problemPath = Path.of(problemFolder).toAbsolutePath().normalize();
        this.parser = parser;
        this.solver = solver;
        this.writer = writer;
        this.outputPath = this.problemPath.resolve(OUTPUT_FOLDER);
        if (Files.notExists(problemPath)) {
            throw new IllegalArgumentException("problem folder not found: " + problemPath);
        }
        Files.createDirectories(outputPath);
        log.info("output folder: {}", outputPath);
    }

    private I parse(String inputFile) throws IOException {
        final Path inputPath = problemPath.resolve(inputFile);
        log.info("executing input {}", inputPath);
        return parser.apply(inputPath).parse();
    }

    private void write(O output, String name) throws IOException {
        writer.apply(outputPath.resolve(name)).write(output);
    }

    public void execute(String... inputFiles) throws IOException {
        for (String inputFile : inputFiles) {
            final I input = parse(inputFile);
            final O output = solver.get().solve(input);
            write(output, inputFile);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void executeAll() throws Throwable {
        execute(problemPath.toFile().list((dir, name) -> name.endsWith(".txt") || name.endsWith(".in")));
    }

    public void testParse(String... inputFiles) throws IOException {
        for (String inputFile : inputFiles) {
            log.info(parse(inputFile).toString());
        }
    }

    public void testWrite(O output) throws IOException {
        write(output, "test.txt");
    }

    public void zipSource() throws IOException {
        final Path rootPath = Path.of(".");
        final Path sourcePath = Path.of("src", "main", "java");
        final Path zipPath = outputPath.resolve(SOURCE_ZIP_FILE);
        final File zipFile = zipPath.toFile();
        try (final ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            Zipper.builder()
                    .zipOutputStream(zipOutputStream)
                    .rootPath(rootPath)
                    .directoryPath(sourcePath.resolve(getPath(getClass())))
                    .directoryPath(sourcePath.resolve(getPath(application)))
                    .filename("*.java")
                    .filename("*.gradle")
                    .filename("*.properties")
                    .build()
                    .zipFile(rootPath.toFile());
        }
        log.info("source zipped at {}", zipPath);
    }

    private Path getPath(Class<?> type) {
        return Path.of(String.join("/", List.of(type.getCanonicalName().split("\\.")).init()));
    }
}
