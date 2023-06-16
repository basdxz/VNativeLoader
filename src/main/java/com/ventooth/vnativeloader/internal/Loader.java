package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.api.VNativeLinker;
import com.ventooth.vnativeloader.api.VNativeLoader;
import com.ventooth.vnativeloader.api.VNativeNameMapper;
import com.ventooth.vnativeloader.api.VNativeUnpacker;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.StringJoiner;

// TODO: Documentation
@Setter
@Getter
@AllArgsConstructor
public class Loader implements VNativeLoader<Loader> {
    private static final Logger LOG = LoggerFactory.getLogger("VNativeLoader");

    @NonNull
    private VNativeNameMapper nameMapper;
    @NonNull
    private VNativeUnpacker unpacker;
    @NonNull
    private VNativeLinker linker;
    @NonNull
    private Path nativeDirectoryPath;

    @Override
    public void loadNative(@NonNull String nativeName)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);

        val unpackedNativeFilePath = unpacker.unpackNative(actualNativeName, nativeFilePath);
        linker.linkNative(unpackedNativeFilePath);

        LOG.debug("Loaded native: {}", nativeName);
    }

    @Override
    public void loadNative(@NonNull String nativeName, @NonNull String classPathName)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);
        val actualNativeClassPathName = nativeClassPathPlatformName(classPathName);

        val unpackedNativeFilePath = unpacker.unpackNative(actualNativeClassPathName, nativeFilePath);
        linker.linkNative(unpackedNativeFilePath);

        LOG.debug("Loaded native: {} from class path: {}", nativeName, classPathName);
    }

    @Override
    public void loadNative(@NonNull String nativeName, @NonNull URL url)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);

        val unpackedNativeFilePath = unpacker.unpackNative(url, nativeFilePath);
        linker.linkNative(unpackedNativeFilePath);

        LOG.debug("Loaded native: {} from url: {}", nativeName, url);
    }

    @Override
    public void loadNative(@NonNull String nativeName, @NonNull InputStream inputStream)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);

        val unpackedNativeFilePath = unpacker.unpackNative(inputStream, nativeFilePath);
        linker.linkNative(unpackedNativeFilePath);

        LOG.debug("Loaded native: {} from input stream", nativeName);
    }

    @Override
    public void loadNative(@NonNull String nativeName, byte @NonNull [] bytes)
            throws IOException, UnsatisfiedLinkError {
        val actualNativeName = nameMapper.mapNativeToPlatformName(nativeName);
        val nativeFilePath = nativeDirectoryPath.resolve(actualNativeName);

        val unpackedNativeFilePath = unpacker.unpackNative(bytes, nativeFilePath);
        linker.linkNative(unpackedNativeFilePath);

        LOG.debug("Loaded native: {} from bytes", nativeName);
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
}