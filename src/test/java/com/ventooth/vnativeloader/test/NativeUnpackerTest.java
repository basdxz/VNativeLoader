package com.ventooth.vnativeloader.test;

import com.ventooth.vnativeloader.api.VNativeLoaderAPI;
import com.ventooth.vnativeloader.api.VNativeUnpacker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

// TODO: Testing
class NativeUnpackerTest {
    static VNativeUnpacker NATIVE_UNPACKER;

    @BeforeAll
    static void init() {
        NATIVE_UNPACKER = VNativeLoaderAPI.createUnpacker();
    }

    @AfterAll
    static void cleanup() {
        NATIVE_UNPACKER = null;
    }
}
