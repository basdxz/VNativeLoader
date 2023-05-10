package com.ventooth.vnativeloader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

// TODO: Testing
class NativeLoaderTest {
    static VNativeLoader<?> NATIVE_LOADER;

    @BeforeAll
    static void init() {
        NATIVE_LOADER = VNativeLoaderAPI.createNativeLoader();
    }

    @AfterAll
    static void cleanup() {
        NATIVE_LOADER = null;
    }
}
