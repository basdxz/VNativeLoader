package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.api.VNativeLinker;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

// TODO: Documentation
@NoArgsConstructor
public final class Linker implements VNativeLinker {
    private static final Logger LOG = LoggerFactory.getLogger("VNativeLoader|Linker");

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
