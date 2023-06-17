package com.ventooth.vnativeloader.api;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

// TODO: Documentation
@FunctionalInterface
public interface VNativeLinker {
    void linkNative(@NotNull Path nativeFilePath) throws UnsatisfiedLinkError;
}
