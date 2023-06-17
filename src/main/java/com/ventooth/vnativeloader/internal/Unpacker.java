package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.api.VNativeUnpacker;
import com.ventooth.vnativeloader.api.VNativeLoaderAPI;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

// TODO: Documentation
@NoArgsConstructor
public final class Unpacker implements VNativeUnpacker {
    private static final Logger LOG = LoggerFactory.getLogger("VNativeLoader|Unpacker");

    @Override
    public Path unpackNative(@NonNull String classPathName, @NonNull Path nativeFilePath) throws IOException {
        val classLoader = VNativeLoaderAPI.class.getClassLoader();

        try (val nativeInputStream = classLoader.getResourceAsStream(classPathName)) {
            if (nativeInputStream == null)
                throw new IOException("Failed to get native from classpath: %s".formatted(classPathName));

            return unpackNative(nativeInputStream, nativeFilePath);
        }
    }

    @Override
    public Path unpackNative(@NonNull URI uri, @NonNull Path nativeFilePath) throws IOException {
        try (val nativeInputStream = uri.toURL().openStream()) {
            if (nativeInputStream == null)
                throw new IOException("Failed to get native from URI: %s ".formatted(uri));

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
        if (Files.isDirectory(nativeFilePath))
            throw new IOException("File path: %s is a directory".formatted(nativeFilePath.toAbsolutePath()));

        val expectedHash = DigestUtils.sha256Hex(bytes);
        LOG.trace("Expected native hash: {}", expectedHash.toUpperCase());

        val existingBytes = PathHelper.readFile(nativeFilePath);
        if (existingBytes.isPresent()) {
            LOG.trace("Found unpacked native: {}", nativeFilePath);

            val existingHash = DigestUtils.sha256Hex(existingBytes.get());

            if (existingHash.equalsIgnoreCase(expectedHash)) {
                LOG.trace("Unpacked native: {} is valid, skipping unpack", nativeFilePath);
                return nativeFilePath;
            }

            LOG.trace("Unpacked native is invalid, deleting old native: {}", nativeFilePath);
            Files.delete(nativeFilePath);
        }

        LOG.trace("Unpacking native to: {}", nativeFilePath);
        PathHelper.writeFile(bytes, nativeFilePath);

        val writtenBytes = PathHelper.readFile(nativeFilePath);
        if (writtenBytes.isEmpty())
            throw new IOException("Failed to unpack native, expected file not created: %s".formatted(nativeFilePath));

        val writtenFileHash = DigestUtils.sha256Hex(writtenBytes.get());
        if (!writtenFileHash.equalsIgnoreCase(expectedHash))
            throw new IOException("Hash mismatch on unpacked native; found: %s expected: %s"
                                          .formatted(writtenFileHash.toUpperCase(), expectedHash.toUpperCase()));

        return nativeFilePath;
    }
}
