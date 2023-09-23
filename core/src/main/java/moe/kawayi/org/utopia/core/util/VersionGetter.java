// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Version.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.log.Logger;

/**
 * 获取版本号
 * <p>
 * 从JAR的utopia-version.properties中读取Utopia-Version条目
 */
public final class VersionGetter {
    /**
     * should not be called
     */
    private VersionGetter() {}

    private static final AtomicReference<Version> VERSION = new AtomicReference<>(null);

    private static final Logger LOGGER = GlobalLogManager.getLogger(VersionGetter.class);

    /**
     * 读取版本号的properties文件路径（基于此类所在的jar）
     */
    public static final String PROPERTIES_FILE_PATH = "/utopia-version.properties";

    /**
     * 读取版本号文件所使用的key
     */
    public static final String VERSION_MAJOR_KEY = "UtopiaVersionMajor";

    public static final String VERSION_MINOR_KEY = "UtopiaVersionMinor";

    public static final String VERSION_PATCH_KEY = "UtopiaVersionPatch";

    public static final String VERSION_EXTRA_KEY = "UtopiaVersionExtra";

    /**
     * 无法获取到正确版本号所使用的默认版本号
     */
    public static final String DEFAULT_VERSION_NUMBER = "0";

    public static final Version DEFAULT_VERSION = new Version(0, 0, 0, null);

    /**
     * 获取版本号
     *
     * @return 获取到的版本号。如果获取失败返回0.0.0
     * @throws java.io.IOException IO错误
     */
    @NotNull
    public static Version getUtopiaVersion() throws java.io.IOException {
        if (VERSION.get() == null) {
            Properties properties = new Properties();

            var is = VersionGetter.class.getResourceAsStream(PROPERTIES_FILE_PATH);

            if (is == null) {
                VERSION.set(DEFAULT_VERSION);
                LOGGER.warn("could not read utopia version");
                return DEFAULT_VERSION;
            }

            try (is) {

                properties.load(is);

                var major = properties.getProperty(VERSION_MAJOR_KEY, DEFAULT_VERSION_NUMBER);
                var minor = properties.getProperty(VERSION_MINOR_KEY, DEFAULT_VERSION_NUMBER);
                var patch = properties.getProperty(VERSION_PATCH_KEY, DEFAULT_VERSION_NUMBER);
                var extra = properties.getProperty(VERSION_EXTRA_KEY, DEFAULT_VERSION_NUMBER);

                VERSION.set(
                        new Version(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), extra));

                return VERSION.get();
            }
        } else {
            return VERSION.get();
        }
    }
}
