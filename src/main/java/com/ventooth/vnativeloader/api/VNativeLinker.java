package com.ventooth.vnativeloader.api;

import lombok.NonNull;

import java.nio.file.Path;

// TODO: Documentation
@FunctionalInterface
public interface VNativeLinker {
    void linkNative(@NonNull Path nativeFilePath) throws UnsatisfiedLinkError;
}
