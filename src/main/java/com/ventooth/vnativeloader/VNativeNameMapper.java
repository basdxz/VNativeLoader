package com.ventooth.vnativeloader;

import lombok.*;

// TODO: Documentation
@FunctionalInterface
public interface VNativeNameMapper {
    String mapNativeToPlatformName(@NonNull String nativeName);
}
