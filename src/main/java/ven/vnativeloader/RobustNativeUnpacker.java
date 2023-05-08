package ven.vnativeloader;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class RobustNativeUnpacker implements VNativeUnpacker {
    private static final VNativeUnpacker INSTANCE = new RobustNativeUnpacker();

    private static final Logger LOG = LoggerFactory.getLogger("Native Unpacker");

    public static VNativeUnpacker robustNativeUnpacker() {
        return INSTANCE;
    }

    @Override
    public void unpackNative(String nativeName, Path outputPath) throws IOException {
        val classLoader = VNativeLoader.class.getClassLoader();

        try (val nativeInputStream = classLoader.getResourceAsStream(nativeName)) {
            unpackNative(nativeInputStream, outputPath);
        }
    }

    @Override
    public void unpackNative(URL nativeURL, Path outputPath) throws IOException {
        try (val nativeInputStream = nativeURL.openStream()) {
            unpackNative(nativeInputStream, outputPath);
        }
    }

    @Override
    public void unpackNative(InputStream nativeInputStream, Path outputPath) throws IOException {
        val nativeBytes = IOUtils.toByteArray(nativeInputStream);
        unpackNative(nativeBytes, outputPath);
    }

    @Override
    public void unpackNative(byte[] nativeBytes, Path outputPath) throws IOException {
        LOG.debug("Native to unpack: {}", outputPath.toAbsolutePath());

        if (Files.isDirectory(outputPath))
            throw new IOException("Output path is a directory, must be a file.");

        val expectedHash = DigestUtils.sha256Hex(nativeBytes);

        val existingFileHash = sha256Hex(outputPath);
        if (existingFileHash.isPresent()) {
            LOG.debug("Found unpacked native.");

            if (existingFileHash.get().equals(expectedHash)) {
                LOG.debug("Unpacked native is valid, skipping unpack.");

                return;
            }

            LOG.debug("Unpacked native is invalid, deleting old native.");
            Files.delete(outputPath);
        }

        LOG.debug("Unpacking native.");
        FileUtils.writeByteArrayToFile(outputPath.toFile(), nativeBytes);

        val writtenFileHash = sha256Hex(outputPath);
        if (writtenFileHash.isEmpty())
            throw new IOException("Failed to unpack native, file not created.");

        if (!writtenFileHash.get().equals(expectedHash))
            throw new IOException("Failed to unpack native, hash mismatch.");
    }

    private static Optional<String> sha256Hex(Path filePath) throws IOException {
        if (!Files.isRegularFile(filePath))
            return Optional.empty();

        try (val fileInputStream = Files.newInputStream(filePath)) {
            return DigestUtils.sha256Hex(fileInputStream).describeConstable();
        }
    }
}
