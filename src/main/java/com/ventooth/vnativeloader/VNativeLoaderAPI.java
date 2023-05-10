package com.ventooth.vnativeloader;


import com.ventooth.vnativeloader.internal.DefaultNativeLoader;
import com.ventooth.vnativeloader.internal.DefaultNativeNameMapper;
import com.ventooth.vnativeloader.internal.DefaultNativeUnpacker;
import lombok.*;
import lombok.experimental.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;


@UtilityClass
public final class VNativeLoaderAPI {
    private static final DefaultNativeNameMapper NATIVE_NAME_MAPPER;
    private static final DefaultNativeUnpacker NATIVE_UNPACKER;
    private static final DefaultNativeLoader NATIVE_LOADER;

    static {
        NATIVE_NAME_MAPPER = new DefaultNativeNameMapper();
        NATIVE_UNPACKER = new DefaultNativeUnpacker();
        NATIVE_LOADER = new DefaultNativeLoader(NATIVE_NAME_MAPPER, NATIVE_UNPACKER, defaultNativesDirectory());
    }

    public static void loadNative(@NonNull String nativeName)
            throws IOException, UnsatisfiedLinkError {
        NATIVE_LOADER.loadNative(nativeName);
    }

    public static void loadNative(@NonNull String nativeName, @NonNull String classPathName)
            throws IOException, UnsatisfiedLinkError {
        NATIVE_LOADER.loadNative(nativeName, classPathName);
    }

    public static void loadNative(@NonNull String nativeName, @NonNull URL url)
            throws IOException, UnsatisfiedLinkError {
        NATIVE_LOADER.loadNative(nativeName, url);
    }

    public static void loadNative(@NonNull String nativeName, @NonNull InputStream inputStream)
            throws IOException, UnsatisfiedLinkError {
        NATIVE_LOADER.loadNative(nativeName, inputStream);
    }

    public static void loadNative(@NonNull String nativeName, byte @NonNull [] bytes)
            throws IOException, UnsatisfiedLinkError {
        NATIVE_LOADER.loadNative(nativeName, bytes);
    }

    public static VNativeNameMapper nativeNameMapper() {
        return NATIVE_NAME_MAPPER;
    }

    public static VNativeUnpacker nativeUnpacker() {
        return NATIVE_UNPACKER;
    }

    public static VNativeLoader<?> createNativeLoader() {
        return new DefaultNativeLoader(NATIVE_NAME_MAPPER, NATIVE_UNPACKER, defaultNativesDirectory());
    }

    public static Path defaultNativesDirectory() {
        return Path.of(System.getProperty("user.dir"), "natives");
    }
}
