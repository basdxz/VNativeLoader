package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.VNativeNameMapper;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class DefaultNativeNameMapper implements VNativeNameMapper {
    private static final Logger LOG = LoggerFactory.getLogger("NativeMapper");

    @Override
    public String mapNativeToPlatformName(@NonNull String nativeName) {
        // logging
        return System.mapLibraryName(nativeName);
    }
}
