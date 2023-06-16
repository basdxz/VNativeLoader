package com.ventooth.vnativeloader.internal;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

// TODO: Documentation
@UtilityClass
public final class PathHelper {
    private static final Logger LOG = LoggerFactory.getLogger("VNativeLoader|PathHelper");

    public static Optional<byte[]> readFile(Path filePath) throws IOException {
        if (Files.isDirectory(filePath))
            throw new IOException("File path: %s is a directory".formatted(filePath.toAbsolutePath()));

        if (!Files.isRegularFile(filePath)) {
            LOG.trace("File: {} not found", filePath.toAbsolutePath());
            return Optional.empty();
        }

        val bytes = FileUtils.readFileToByteArray(filePath.toFile());
        LOG.trace("Read: {} bytes from file: {}", bytes.length, filePath.toAbsolutePath());

        return Optional.of(bytes);
    }

    public static void writeFile(byte[] bytes, Path filePath) throws IOException {
        if (Files.isDirectory(filePath))
            throw new IOException("File path: %s is a directory".formatted(filePath.toAbsolutePath()));

        if (Files.deleteIfExists(filePath))
            LOG.trace("Found and deleted existing file: {}", filePath.toAbsolutePath());

        FileUtils.writeByteArrayToFile(filePath.toFile(), bytes);
        LOG.trace("Wrote: {} bytes to file: {}", bytes.length, filePath.toAbsolutePath());
    }
}
