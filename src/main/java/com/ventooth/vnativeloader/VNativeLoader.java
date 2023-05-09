package com.ventooth.vnativeloader;


import lombok.*;

import java.io.File;
import java.io.IOException;

public class VNativeLoader {
    public static void main(String[] args) throws IOException {
        val unpacker = RobustNativeUnpacker.robustNativeUnpacker();

        unpacker.unpackNative("lwjgl.dll", new File("test/lwjgl.dll").toPath());
    }

    // Given a name of a native in a jar; unpack it
}
