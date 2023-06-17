package com.ventooth.vnativeloader.test;

import com.ventooth.vnativeloader.api.VNativeLoaderAPI;
import com.ventooth.vnativeloader.api.VNativeNameMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


// TODO: Testing
class NativeNameMapperTest {
    static VNativeNameMapper NATIVE_NAME_MAPPER;

    @BeforeAll
    static void init() {
        NATIVE_NAME_MAPPER = VNativeLoaderAPI.createNameMapper();
    }

    @AfterAll
    static void cleanup() {
        NATIVE_NAME_MAPPER = null;
    }
}
