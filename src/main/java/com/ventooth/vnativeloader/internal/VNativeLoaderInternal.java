package com.ventooth.vnativeloader.internal;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public final class VNativeLoaderInternal {
    private static final Loader LOADER = createLoader();

    public static Loader loader() {
        return LOADER;
    }

    public static Loader createLoader() {
        val mapper = createNameMapper();
        val unpacker = createUnpacker();
        val linker = createLinker();
        val nativesDirectory = nativesDirectory();

        return new Loader(mapper, unpacker, linker, nativesDirectory);
    }

    public static NameMapper createNameMapper() {
        return new NameMapper();
    }

    public static Unpacker createUnpacker() {
        return new Unpacker();
    }

    public static Linker createLinker() {
        return new Linker();
    }

    public static Path nativesDirectory() {
        return Paths.get(System.getProperty("user.dir"), "natives");
    }
}
