package com.ventooth.vnativeloader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

// TODO: Testing
class NativeUnpackerTest {
    static VNativeUnpacker NATIVE_UNPACKER;

    @BeforeAll
    static void init() {
        NATIVE_UNPACKER = VNativeLoaderAPI.nativeUnpacker();
    }

    @AfterAll
    static void cleanup() {
        NATIVE_UNPACKER = null;
    }
}
