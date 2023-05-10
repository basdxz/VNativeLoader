package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.VNativeNameMapper;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class DefaultNativeNameMapper implements VNativeNameMapper {
    private static final Logger LOG = LoggerFactory.getLogger("VNativeNameMapper");

    @Override
    public String mapNativeToPlatformName(@NonNull String nativeName) {
        val platformNativeName = System.mapLibraryName(nativeName);
        LOG.trace("Mapped native name: {} to platform native name: {}", nativeName, platformNativeName);
        return platformNativeName;
    }
}
