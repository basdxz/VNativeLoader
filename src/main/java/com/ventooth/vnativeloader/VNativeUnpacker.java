package com.ventooth.vnativeloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

public interface VNativeUnpacker {
    Path unpackNative(String classPathName, Path outputPath) throws IOException;

    Path unpackNative(URL url, Path outputPath) throws IOException;

    Path unpackNative(InputStream inputStream, Path outputPath) throws IOException;

    Path unpackNative(byte[] bytes, Path outputPath) throws IOException;
}
