package com.ventooth.vnativeloader.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

// TODO: Documentation
public interface VNativeUnpacker {
    Path unpackNative(@NotNull String classPathName, @NotNull Path nativeFilePath) throws IOException;

    Path unpackNative(@NotNull URI uri, @NotNull Path nativeFilePath) throws IOException;

    Path unpackNative(@NotNull InputStream inputStream, @NotNull Path nativeFilePath) throws IOException;

    Path unpackNative(byte @NotNull [] bytes, @NotNull Path nativeFilePath) throws IOException;
}
