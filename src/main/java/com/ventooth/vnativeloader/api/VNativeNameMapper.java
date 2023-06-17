package com.ventooth.vnativeloader.api;

import org.jetbrains.annotations.NotNull;

// TODO: Documentation
@FunctionalInterface
public interface VNativeNameMapper {
    String mapNativeToPlatformName(@NotNull String nativeName);
}
