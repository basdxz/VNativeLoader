package ven.vnativeloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

public interface VNativeUnpacker {
    void unpackNative(String nativeName, Path outputPath) throws IOException;

    void unpackNative(URL nativeURL, Path outputPath) throws IOException;

    void unpackNative(InputStream nativeInputStream, Path outputPath) throws IOException;

    void unpackNative(byte[] nativeBytes, Path outputPath) throws IOException;
}
