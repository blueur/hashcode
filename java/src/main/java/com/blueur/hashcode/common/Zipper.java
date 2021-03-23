package com.blueur.hashcode.common;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Value
public class Zipper {
    ZipOutputStream zipOutputStream;
    Path rootPath;
    List<Path> directoryPaths;
    FileFilter childrenFilter;

    @Builder
    public Zipper(ZipOutputStream zipOutputStream,
                  Path rootPath,
                  @Singular List<Path> directoryPaths,
                  @Singular List<String> filenames) {
        this.zipOutputStream = zipOutputStream;
        this.rootPath = rootPath;
        this.directoryPaths = directoryPaths;
        final IOFileFilter filenameFilter = new OrFileFilter(filenames.stream().map(WildcardFileFilter::new).collect(Collectors.toList()));
        this.childrenFilter = new OrFileFilter(DirectoryFileFilter.DIRECTORY, filenameFilter);
    }

    public void zipFile(File file) throws IOException {
        if (!file.isHidden()) {
            final Path relativePath = rootPath.relativize(file.toPath());
            final String filename = relativePath.toString();
            if (file.isDirectory()) {
                if (filename.isEmpty() || directoryPaths.stream().anyMatch(path -> path.startsWith(relativePath) || relativePath.startsWith(path))) {
                    zipOutputStream.putNextEntry(new ZipEntry(filename + '/'));
                    zipOutputStream.closeEntry();
                    final File[] childFiles = file.listFiles(childrenFilter);
                    assert childFiles != null;
                    for (File childFile : childFiles) {
                        zipFile(childFile);
                    }
                }
            } else {
                log.debug("zipping {}", filename);
                try (final FileInputStream fileInputStream = new FileInputStream(file)) {
                    zipOutputStream.putNextEntry(new ZipEntry(filename));
                    IOUtils.copy(fileInputStream, zipOutputStream);
                }
            }
        }
    }
}
