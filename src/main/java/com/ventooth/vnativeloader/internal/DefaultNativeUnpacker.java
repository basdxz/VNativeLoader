package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.VNativeLoaderAPI;
import com.ventooth.vnativeloader.VNativeUnpacker;
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

@NoArgsConstructor
public class DefaultNativeUnpacker implements VNativeUnpacker {
    private static final Logger LOG = LoggerFactory.getLogger("NativeUnpacker");

    @Override
    public Path unpackNative(@NonNull String classPathName, @NonNull Path nativeFilePath) throws IOException {
        val classLoader = VNativeLoaderAPI.class.getClassLoader();

        try (val nativeInputStream = classLoader.getResourceAsStream(classPathName)) {
            if (nativeInputStream == null)
                throw new IOException();

            return unpackNative(nativeInputStream, nativeFilePath);
        }
    }

    @Override
    public Path unpackNative(@NonNull URL url, @NonNull Path nativeFilePath) throws IOException {
        try (val nativeInputStream = url.openStream()) {
            if (nativeInputStream == null)
                throw new IOException();

            return unpackNative(nativeInputStream, nativeFilePath);
        }
    }

    @Override
    public Path unpackNative(@NonNull InputStream inputStream, @NonNull Path nativeFilePath) throws IOException {
        val nativeBytes = IOUtils.toByteArray(inputStream);
        return unpackNative(nativeBytes, nativeFilePath);
    }

    @Override
    public Path unpackNative(byte @NonNull [] bytes, @NonNull Path nativeFilePath) throws IOException {
        LOG.trace("Native to unpack: {}", nativeFilePath.toAbsolutePath());

        if (Files.isDirectory(nativeFilePath))
            throw new IOException("Output path is a directory, must be empty or existing file.");

        val expectedHash = DigestUtils.sha256Hex(bytes);
        LOG.trace("Generated SHA256 Hash: {} , expected unpacked native.", expectedHash.toUpperCase());

        val existingBytes = PathHelper.readFile(nativeFilePath);
        if (existingBytes.isPresent()) {
            LOG.trace("Found unpacked native.");

            val existingHash = DigestUtils.sha256Hex(existingBytes.get());

            if (existingHash.equalsIgnoreCase(expectedHash)) {
                // improve log
                LOG.trace("Unpacked native is valid, skipping unpack.");

                return nativeFilePath;
            }

            LOG.trace("Unpacked native is invalid, deleting old native.");
            Files.delete(nativeFilePath);
        }

        LOG.trace("Unpacking native.");
        PathHelper.writeFile(bytes, nativeFilePath);
        val writtenBytes = PathHelper.readFile(nativeFilePath);

        if (writtenBytes.isEmpty())
            throw new IOException("Failed to unpack native, file not created.");

        val writtenFileHash = DigestUtils.sha256Hex(writtenBytes.get());

        if (!writtenFileHash.equalsIgnoreCase(expectedHash))
            throw new IOException("Failed to unpack native, hash mismatch.");

        return nativeFilePath;
    }
}
