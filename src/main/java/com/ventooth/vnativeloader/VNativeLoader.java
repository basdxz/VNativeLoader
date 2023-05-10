package com.ventooth.vnativeloader;

import lombok.*;

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
