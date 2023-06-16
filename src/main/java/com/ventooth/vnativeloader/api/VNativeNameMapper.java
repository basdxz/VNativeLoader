package com.ventooth.vnativeloader.api;

import lombok.NonNull;

// TODO: Documentation
@FunctionalInterface
public interface VNativeNameMapper {
    String mapNativeToPlatformName(@NonNull String nativeName);
}
