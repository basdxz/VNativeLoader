package com.ventooth.vnativeloader;

public interface VPlatformNativeMapper {
    /**
     * Remaps the name of a native library to it's platform specific name.
     *  TODO: Example
     *
     * @param nativeName Name of the native to map.
     * @return Platform specific native name.
     */
    String mapToPlatformName(String nativeName);
}
