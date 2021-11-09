//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The JvmInfo.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 一个简单的，输入jvm信息到debug log的类
 */
public class JvmInfo {

    private static final Logger LOGGER = LogManager.getLogger(JvmInfo.class);

    /**
     * 输出jvm信息到log(debug级别)
     */
    public static synchronized void print(){
        var jvmName = System.getProperty("java.vm.name");
        var jvmVersion = System.getProperty("java.vm.version");
        var jvmVendor = System.getProperty("java.vendor");
        var javaVersion =  System.getProperty("java.version");
        var javaHome = System.getProperty("java.home");

        var runtime = Runtime.getRuntime();
        var maxMemory = runtime.maxMemory();

        LOGGER.debug("runtime name:{}",jvmName);
        LOGGER.debug("runtime version:{}",jvmVersion);
        LOGGER.debug("runtime vendor:{}",jvmVendor);
        LOGGER.debug("java home:{}",javaHome);
        LOGGER.debug("java version:{}",javaVersion);
        LOGGER.debug("max memory(bytes):{}",maxMemory);
    }

}
