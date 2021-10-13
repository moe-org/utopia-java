//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Version.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.Manifest;

/**
 * 获取版本号
 *
 * 从JAR的utopia-version.properties中读取Utopia-Version条目
 */
public final class UtopiaVersion {

    private static final AtomicReference<String> version = new AtomicReference<>(null);

    private static final Logger LOGGER = LogManager.getLogger(UtopiaVersion.class);

    /**
     * 读取版本号的properties文件路径（基于此类所在的jar）
     */
    public static final String PROPERTIES_FILE_PATH = "/utopia-version.properties";

    /**
     * 读取版本号文件所使用的properties key
     */
    public static final String VERSION_PROPERTIES_KEY = "UtopiaVersion";

    /**
     * 无法获取到正确版本号所使用的默认版本号
     */
    public static final String DEFAULT_VERSION = "0.0.0";

    /**
     * 获取版本号
     * @return  获取到的版本号。如果获取失败返回0.0.0
     */
    @NotNull
    public static String getUtopiaVersion() throws java.io.IOException{
        if(version.get() == null){
            Properties properties = new Properties();

            properties.load(UtopiaVersion.class.getResourceAsStream(PROPERTIES_FILE_PATH));

            var ver = properties.getProperty(VERSION_PROPERTIES_KEY,DEFAULT_VERSION);

            version.set(ver);

            return ver;
        }
        else{
            return version.get();
        }
    }

}
