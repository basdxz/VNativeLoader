package com.ventooth.vnativeloader.api;

import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

// TODO: Documentation
public interface VNativeLoader<SELF extends VNativeLoader<? extends SELF>> {
    SELF nameMapper(@NonNull VNativeNameMapper nameMapper);

    VNativeNameMapper nameMapper();

    SELF unpacker(@NonNull VNativeUnpacker unpacker);

    VNativeUnpacker unpacker();

    SELF linker(@NonNull VNativeLinker linker);

    VNativeLinker linker();

    Path nativeDirectoryPath();

    SELF nativeDirectoryPath(@NonNull Path nativeDirectoryPath);

    void loadNative(@NonNull String nativeName)
            throws IOException, UnsatisfiedLinkError;

    void loadNative(@NonNull String nativeName, @NonNull String classPathName)
            throws IOException, UnsatisfiedLinkError;

    void loadNative(@NonNull String nativeName, @NonNull URL url)
            throws IOException, UnsatisfiedLinkError;

    void loadNative(@NonNull String nativeName, @NonNull InputStream inputStream)
            throws IOException, UnsatisfiedLinkError;

    void loadNative(@NonNull String nativeName, byte @NonNull [] bytes)
            throws IOException, UnsatisfiedLinkError;
}
