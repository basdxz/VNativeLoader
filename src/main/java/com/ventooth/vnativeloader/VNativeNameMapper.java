package com.ventooth.vnativeloader;

import lombok.*;

@FunctionalInterface
public interface VNativeNameMapper {
    /**
     * Remaps the name of a native library to it's platform specific name.
     *  TODO: Example
     *
     * @param nativeName Name of the native to map.
     * @return Platform specific native name.
     */
    String mapNativeToPlatformName(@NonNull String nativeName);
}
