package com.ventooth.vnativeloader.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

// TODO: Documentation
@SuppressWarnings("UnusedReturnValue")
public interface VNativeLoader<SELF extends VNativeLoader<? extends SELF>> {
    SELF loadNative(@NotNull String nativeName)
            throws IOException, UnsatisfiedLinkError;

    SELF loadNative(@NotNull String nativeName, @NotNull String classPathName)
            throws IOException, UnsatisfiedLinkError;

    SELF loadNative(@NotNull String nativeName, @NotNull URI uri)
            throws IOException, UnsatisfiedLinkError;

    SELF loadNative(@NotNull String nativeName, @NotNull InputStream inputStream)
            throws IOException, UnsatisfiedLinkError;

    SELF loadNative(@NotNull String nativeName, byte @NotNull [] bytes)
            throws IOException, UnsatisfiedLinkError;

    SELF nameMapper(@NotNull VNativeNameMapper nameMapper);

    VNativeNameMapper nameMapper();

    SELF unpacker(@NotNull VNativeUnpacker unpacker);

    VNativeUnpacker unpacker();

    SELF linker(@NotNull VNativeLinker linker);

    VNativeLinker linker();

    Path nativeDirectory();

    SELF nativeDirectory(@NotNull Path nativeDirectoryPath);
}
