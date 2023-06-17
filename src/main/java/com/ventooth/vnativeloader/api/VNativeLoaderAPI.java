package com.ventooth.vnativeloader.api;


import com.ventooth.vnativeloader.internal.Loader;
import com.ventooth.vnativeloader.internal.VNativeLoaderInternal;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;


// TODO: Documentation
@SuppressWarnings("unused")
@UtilityClass
public final class VNativeLoaderAPI {
    public static void loadNative(@NotNull String nativeName)
            throws IOException, UnsatisfiedLinkError {
        VNativeLoaderInternal.loader().loadNative(nativeName);
    }

    public static void loadNative(@NotNull String nativeName, @NotNull String classPathName)
            throws IOException, UnsatisfiedLinkError {
        VNativeLoaderInternal.loader().loadNative(nativeName, classPathName);
    }

    public static void loadNative(@NotNull String nativeName, @NotNull URI uri)
            throws IOException, UnsatisfiedLinkError {
        VNativeLoaderInternal.loader().loadNative(nativeName, uri);
    }

    public static void loadNative(@NotNull String nativeName, @NotNull InputStream inputStream)
            throws IOException, UnsatisfiedLinkError {
        VNativeLoaderInternal.loader().loadNative(nativeName, inputStream);
    }

    public static void loadNative(@NotNull String nativeName, byte @NotNull [] bytes)
            throws IOException, UnsatisfiedLinkError {
        VNativeLoaderInternal.loader().loadNative(nativeName, bytes);
    }

    public static VNativeLoader<?> createLoader() {
        val mapper = createNameMapper();
        val unpacker = createUnpacker();
        val linker = createLinker();
        val nativesDirectory = nativesDirectory();

        return new Loader(mapper, unpacker, linker, nativesDirectory);
    }

    public static VNativeNameMapper createNameMapper() {
        return VNativeLoaderInternal.createNameMapper();
    }

    public static VNativeUnpacker createUnpacker() {
        return VNativeLoaderInternal.createUnpacker();
    }

    public static VNativeLinker createLinker() {
        return VNativeLoaderInternal.createLinker();
    }

    public static Path nativesDirectory() {
        return VNativeLoaderInternal.nativesDirectory();
    }
}
