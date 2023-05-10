package com.ventooth.vnativeloader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lwjgl.opengl.*;

class LWJGL2NativesTest {
    @Test
    void loadLWJGLNatives() {
        Assertions.assertDoesNotThrow(() -> VNativeLoaderAPI.loadNative("lwjgl64"));
        System.setProperty("org.lwjgl.librarypath", VNativeLoaderAPI.defaultNativesDirectory().toAbsolutePath().toString());

        Assertions.assertDoesNotThrow(() -> Display.setDisplayMode(new DisplayMode(500, 500)));
    }
}
