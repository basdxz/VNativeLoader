package com.ventooth.vnativeloader;

import lombok.experimental.*;
import lombok.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.StringJoiner;

@UtilityClass
public final class PathHelper {
    private static final Logger LOG = LoggerFactory.getLogger("Path Helper");

    public static Optional<byte[]> readFile(Path filePath) throws IOException {
        if (Files.isDirectory(filePath))
            throw new IOException("File path is a directory.");

        if (!Files.isRegularFile(filePath)) {
            // log
            return Optional.empty();
        }

        val bytes = FileUtils.readFileToByteArray(filePath.toFile());
        // log
        return Optional.of(bytes);
    }

    public static void writeFile(byte[] bytes, Path filePath) throws IOException {
        if (Files.isDirectory(filePath))
            throw new IOException("Output path is a directory.");

        if (Files.deleteIfExists(filePath))
            LOG.trace("Found and deleted existing file: {}", filePath.toAbsolutePath());

        FileUtils.writeByteArrayToFile(filePath.toFile(), bytes);
        LOG.trace("Wrote bytes to file: {}", filePath.toAbsolutePath());
    }

    public static void appendDirectoryToProperty(String propertyKey, Path directoryPath) {
        if (!Files.isDirectory(directoryPath))
            throw new IllegalArgumentException();

        val newProperty = new StringJoiner(File.pathSeparator);

        val oldProperty = System.getProperty(propertyKey);
        if (oldProperty != null) {
            newProperty.add(oldProperty);
            // log
        } else {
            // log
        }

        newProperty.add(directoryPath.toAbsolutePath().toString());

        System.setProperty(propertyKey, newProperty.toString());
        // log
    }
}