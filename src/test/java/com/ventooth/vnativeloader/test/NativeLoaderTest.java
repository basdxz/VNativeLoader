package com.ventooth.vnativeloader.test;

import com.ventooth.vnativeloader.api.VNativeLoader;
import com.ventooth.vnativeloader.api.VNativeLoaderAPI;
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
