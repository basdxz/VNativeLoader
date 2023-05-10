package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.VNativeLinker;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

// TODO: Documentation
@NoArgsConstructor
public class DefaultNativeLinker implements VNativeLinker {
    private static final Logger LOG = LoggerFactory.getLogger("VNativeLinker");

    @Override
    public void linkNative(@NonNull Path nativeFilePath) throws UnsatisfiedLinkError {
        val fullPath = nativeFilePath.toAbsolutePath().toString();
        try {
            System.load(fullPath);
            LOG.trace("Linked native using JNI: {}", fullPath);
        } catch (UnsatisfiedLinkError e) {
            throw new UnsatisfiedLinkError("Failed to link native: %s".formatted(fullPath));
        }
    }
}
