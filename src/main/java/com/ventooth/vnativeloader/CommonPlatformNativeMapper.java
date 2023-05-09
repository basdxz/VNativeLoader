package com.ventooth.vnativeloader;

public final class CommonPlatformNativeMapper implements VPlatformNativeMapper {
    private static final VPlatformNativeMapper INSTANCE = new CommonPlatformNativeMapper();

    public static VPlatformNativeMapper commonPlatformNameMapper() {
        return INSTANCE;
    }

    @Override
    public String mapToPlatformName(String nativeName) {
        return switch (Platform.current()) {
            case WINDOWS -> nativeName + ".dll";
            case LINUX -> "lib" + nativeName.toLowerCase() + ".so";
            case MAC_OS -> nativeName.toLowerCase() + ".dylib";
            default -> throw new IllegalStateException("Unknown Platform.");
        };
    }
}
