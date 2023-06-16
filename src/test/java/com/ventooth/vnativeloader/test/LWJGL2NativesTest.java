package com.ventooth.vnativeloader.test;

import com.ventooth.vnativeloader.api.VNativeLoaderAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

class LWJGL2NativesTest {
    @Test
    @Disabled("To be run manually as it will fail on CI.")
    void loadLWJGLNatives() {
        Assertions.assertDoesNotThrow(() -> VNativeLoaderAPI.loadNative("lwjgl64"));
        System.setProperty("org.lwjgl.librarypath", VNativeLoaderAPI.defaultNativesDirectory().toAbsolutePath().toString());

        Assertions.assertDoesNotThrow(() -> Display.setDisplayMode(new DisplayMode(500, 500)));
    }
}
