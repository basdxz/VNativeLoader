package com.ventooth.vnativeloader;

import lombok.*;

import java.nio.file.Path;

// TODO: Documentation
@FunctionalInterface
public interface VNativeLinker {
    void linkNative(@NonNull Path nativeFilePath) throws UnsatisfiedLinkError;
}
