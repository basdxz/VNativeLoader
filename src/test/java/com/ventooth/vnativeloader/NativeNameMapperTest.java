package com.ventooth.vnativeloader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


// TODO: Testing
class NativeNameMapperTest {
    static VNativeNameMapper NATIVE_NAME_MAPPER;

    @BeforeAll
    static void init() {
        NATIVE_NAME_MAPPER = VNativeLoaderAPI.nativeNameMapper();
    }

    @AfterAll
    static void cleanup() {
        NATIVE_NAME_MAPPER = null;
    }
}
