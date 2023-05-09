package com.ventooth.vnativeloader;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Robust Native Unpacker")
class RobustNativeUnpackerTest {
    static final String TEST_NATIVE_NAME = "lwjgl.dll";
    static final String TEST_NATIVE_HASH = "fff711369747e9bb3656d4c5bdee7051bbc13f30abd634418bf40706a25f365c";

    @Test
    @DisplayName("Unpack Native")
    void unpackNative() {
        val unpacker = RobustNativeUnpacker.robustNativeUnpacker();

        val path = new File("test/lwjgl.dll").toPath();

        try {
            unpacker.unpackNative("lwjgl.dll", new File("test/lwjgl.dll").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (val fileInputStream = Files.newInputStream(path.toFile().toPath())) {

            val hash = DigestUtils.sha256Hex(fileInputStream).toLowerCase();
            assertEquals(TEST_NATIVE_HASH, hash, "Extracted native hash does not match.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        val testDir = new File("test");
        if (testDir.exists()) {
            try {
                FileUtils.deleteDirectory(testDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
