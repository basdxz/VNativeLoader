package com.ventooth.vnativeloader;

import lombok.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

// TODO: Documentation
public interface VNativeUnpacker {
    Path unpackNative(@NonNull String classPathName, @NonNull Path nativeFilePath) throws IOException;

    Path unpackNative(@NonNull URL url, @NonNull Path nativeFilePath) throws IOException;

    Path unpackNative(@NonNull InputStream inputStream, @NonNull Path nativeFilePath) throws IOException;

    Path unpackNative(byte @NonNull [] bytes, @NonNull Path nativeFilePath) throws IOException;
}
