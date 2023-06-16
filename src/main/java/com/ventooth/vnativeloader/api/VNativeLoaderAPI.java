package com.ventooth.vnativeloader.api;


import com.ventooth.vnativeloader.internal.Linker;
import com.ventooth.vnativeloader.internal.Loader;
import com.ventooth.vnativeloader.internal.NameMapper;
import com.ventooth.vnativeloader.internal.Unpacker;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

// TODO: Documentation
@UtilityClass
public final class VNativeLoaderAPI {
    private static final NameMapper NATIVE_NAME_MAPPER;
    private static final Unpacker NATIVE_UNPACKER;
    private static final Linker NATIVE_LINKER;
    private static final Loader NATIVE_LOADER;

    static {
        NATIVE_NAME_MAPPER = new NameMapper();
        NATIVE_UNPACKER = new Unpacker();
        NATIVE_LINKER = new Linker();
        NATIVE_LOADER = new Loader(NATIVE_NAME_MAPPER,
                                   NATIVE_UNPACKER,
                                   NATIVE_LINKER,
                                   defaultNativesDirectory());
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
        return new Loader(NATIVE_NAME_MAPPER,
                          NATIVE_UNPACKER,
                          NATIVE_LINKER,
                          defaultNativesDirectory());
    }

    public static Path defaultNativesDirectory() {
        return Path.of(System.getProperty("user.dir"), "natives");
    }
}
