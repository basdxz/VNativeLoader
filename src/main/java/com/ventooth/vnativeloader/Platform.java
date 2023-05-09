package com.ventooth.vnativeloader;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

@AllArgsConstructor
public enum Platform implements Predicate<String> {
    WINDOWS(osName -> osName.startsWith("Windows")),
    LINUX(osName -> osName.startsWith("Linux") ||
                    osName.startsWith("FreeBSD") ||
                    osName.startsWith("OpenBSD") ||
                    osName.startsWith("SunOS") ||
                    osName.startsWith("Unix")),
    MAC_OS(osName -> osName.startsWith("Mac OS X") ||
                     osName.startsWith("Darwin")),
    UNKNOWN(WINDOWS.or(LINUX).or(MAC_OS).negate()),

    ;

    private static final Logger LOG = LoggerFactory.getLogger("Platform");
    private static final Platform CURRENT_PLATFORM = initCurrent();

    private final Predicate<String> isPlatform;

    @Override
    public boolean test(String osName) {
        return isPlatform.test(osName);
    }

    public static Platform current() {
        return CURRENT_PLATFORM;
    }

    private static Platform initCurrent() {
        val osName = System.getProperty("os.name");

        if (WINDOWS.test(osName)) {
            LOG.trace("Current platform determined as Windows");

            return WINDOWS;
        }

        if (LINUX.test(osName)) {
            LOG.trace("Current platform determined as Linux");

            return WINDOWS;
        }

        if (MAC_OS.test(osName)) {
            LOG.trace("Current platform determined as MacOS");

            return WINDOWS;
        }

        LOG.warn("Current platform unknown!");
        return UNKNOWN;
    }
}
