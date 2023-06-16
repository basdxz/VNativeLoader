package com.ventooth.vnativeloader.internal;

import com.ventooth.vnativeloader.api.VNativeNameMapper;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Documentation
@NoArgsConstructor
public class NameMapper implements VNativeNameMapper {
    private static final Logger LOG = LoggerFactory.getLogger("VNativeLoader|NameMapper");

    @Override
    public String mapNativeToPlatformName(@NonNull String nativeName) {
        val platformNativeName = System.mapLibraryName(nativeName);
        LOG.trace("Mapped native name: {} to platform native name: {}", nativeName, platformNativeName);
        return platformNativeName;
    }
}
