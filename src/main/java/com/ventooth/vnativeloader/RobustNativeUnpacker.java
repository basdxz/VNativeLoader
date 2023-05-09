package com.ventooth.vnativeloader;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class RobustNativeUnpacker implements VNativeUnpacker {
    private static final VNativeUnpacker INSTANCE = new RobustNativeUnpacker();

    private static final Logger LOG = LoggerFactory.getLogger("Native Unpacker");

    public static VNativeUnpacker robustNativeUnpacker() {
        return INSTANCE;
    }

    @Override
    public Path unpackNative(String classPathName, Path outputPath) throws IOException {
        val classLoader = VNativeLoader.class.getClassLoader();

        try (val nativeInputStream = classLoader.getResourceAsStream(classPathName)) {
            return unpackNative(nativeInputStream, outputPath);
        }
    }

    @Override
    public Path unpackNative(URL url, Path outputPath) throws IOException {
        try (val nativeInputStream = url.openStream()) {
            return unpackNative(nativeInputStream, outputPath);
        }
    }

    @Override
    public Path unpackNative(InputStream inputStream, Path outputPath) throws IOException {
        val nativeBytes = IOUtils.toByteArray(inputStream);
        return unpackNative(nativeBytes, outputPath);
    }

    @Override
    public Path unpackNative(byte[] bytes, Path outputPath) throws IOException {
        LOG.trace("Native to unpack: {}", outputPath.toAbsolutePath());

        if (Files.isDirectory(outputPath))
            throw new IOException("Output path is a directory, must be empty or existing file.");

        val expectedHash = DigestUtils.sha256Hex(bytes);
        LOG.trace("Generated SHA256 Hash: {} , expected unpacked native.", expectedHash.toUpperCase());

        val existingBytes = PathHelper.readFile(outputPath);
        if (existingBytes.isPresent()) {
            LOG.trace("Found unpacked native.");

            val existingHash = DigestUtils.sha256Hex(existingBytes.get());

            if (existingHash.equalsIgnoreCase(expectedHash)) {
                // improve log
                LOG.trace("Unpacked native is valid, skipping unpack.");

                return outputPath;
            }

            LOG.trace("Unpacked native is invalid, deleting old native.");
            Files.delete(outputPath);
        }

        LOG.trace("Unpacking native.");
        PathHelper.writeFile(bytes, outputPath);
        val writtenBytes = PathHelper.readFile(outputPath);

        if (writtenBytes.isEmpty())
            throw new IOException("Failed to unpack native, file not created.");

        val writtenFileHash = DigestUtils.sha256Hex(writtenBytes.get());

        if (!writtenFileHash.equalsIgnoreCase(expectedHash))
            throw new IOException("Failed to unpack native, hash mismatch.");

        return outputPath;
    }
}
