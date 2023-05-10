package com.ventooth.vnativeloader;


import com.ventooth.vnativeloader.internal.DefaultNativeLoader;
import com.ventooth.vnativeloader.internal.DefaultNativeNameMapper;
import com.ventooth.vnativeloader.internal.DefaultNativeUnpacker;
import lombok.experimental.*;

import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
        loadNative("lwjgl64");
    }

    public static void loadNative(String nativeName) throws IOException {
        NATIVE_LOADER.loadNative(nativeName);
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
