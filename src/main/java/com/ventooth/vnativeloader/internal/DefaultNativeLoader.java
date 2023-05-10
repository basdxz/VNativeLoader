package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.VNativeLoader;
import com.ventooth.vnativeloader.VNativeNameMapper;
import com.ventooth.vnativeloader.VNativeUnpacker;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.StringJoiner;

@Setter
@Getter
@AllArgsConstructor
public class DefaultNativeLoader implements VNativeLoader<DefaultNativeLoader> {
    private static final Logger LOG = LoggerFactory.getLogger("NativeLoader");

    @NonNull
    private VNativeNameMapper nameMapper;
    @NonNull
    private VNativeUnpacker unpacker;
    @NonNull
    private Path nativeDirectoryPath;

    @Override
    public void loadNative(@NonNull String nativeName)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);

        val unpackedNativeFilePath = unpacker.unpackNative(actualNativeName, nativeFilePath);
        loadUsingJNI(unpackedNativeFilePath);
    }

    @Override
    public void loadNative(@NonNull String nativeName, @NonNull String classPathName)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);
        val actualNativeClassPathName = nativeClassPathPlatformName(classPathName);

        val unpackedNativeFilePath = unpacker.unpackNative(actualNativeClassPathName, nativeFilePath);
        loadUsingJNI(unpackedNativeFilePath);
    }

    @Override
    public void loadNative(@NonNull String nativeName, @NonNull URL url)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);

        val unpackedNativeFilePath = unpacker.unpackNative(url, nativeFilePath);
        loadUsingJNI(unpackedNativeFilePath);
    }

    @Override
    public void loadNative(@NonNull String nativeName, @NonNull InputStream inputStream)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);

        val unpackedNativeFilePath = unpacker.unpackNative(inputStream, nativeFilePath);
        loadUsingJNI(unpackedNativeFilePath);
    }

    @Override
    public void loadNative(@NonNull String nativeName, byte @NonNull [] bytes)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);

        val unpackedNativeFilePath = unpacker.unpackNative(bytes, nativeFilePath);
        loadUsingJNI(unpackedNativeFilePath);
    }

    private String nativeClassPathPlatformName(String classPathName) {
        val nativeClassPathPlatformName = new StringJoiner("/");

        val pathElements = classPathName.split("/");

        val nativeNameIndex = pathElements.length - 1;
        val lastElement = pathElements[nativeNameIndex];

        val nativePlatformName = nameMapper.mapNativeToPlatformName(lastElement);
        pathElements[nativeNameIndex] = nativePlatformName;

        Arrays.stream(pathElements)
              .forEach(nativeClassPathPlatformName::add);
        return nativeClassPathPlatformName.toString();
    }

    private static void loadUsingJNI(Path nativeFilePath) {
        System.load(nativeFilePath.toAbsolutePath().toString());
    }
}
