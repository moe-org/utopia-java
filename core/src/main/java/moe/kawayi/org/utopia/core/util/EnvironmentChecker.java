// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EnvironmentChecker.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.Logger;

/**
 * 一个简单的，输入jvm信息到debug log的类
 */
public class EnvironmentChecker {

    /**
     * private
     */
    private EnvironmentChecker() {}

    private static final Logger LOGGER = LogManagers.getLogger(EnvironmentChecker.class);

    /**
     * 输出jvm信息
     * @param printFunc 打印输出函数
     */
    public static synchronized void print(@NotNull Consumer<String> printFunc) {
        Objects.requireNonNull(printFunc);

        BiConsumer<String, String> matchFunc = (String msg, String param) -> {
            printFunc.accept(msg.replace("{}", param));
        };

        // java info
        matchFunc.accept("runtime name:{}", System.getProperty("java.vm.name"));
        matchFunc.accept("runtime version:{}", System.getProperty("java.vm.version"));
        matchFunc.accept("runtime vendor:{}", System.getProperty("java.vendor"));
        matchFunc.accept("java home:{}", System.getProperty("java.home"));
        matchFunc.accept("java version:{}", System.getProperty("java.version"));

        // os info
        matchFunc.accept("os name:{}", System.getProperty("os.name"));
        matchFunc.accept("os version:{}", System.getProperty("os.version"));
        matchFunc.accept("os arch:{}", System.getProperty("os.arch"));

        // user info
        matchFunc.accept("user dir:{}", System.getProperty("user.dir"));
        matchFunc.accept("user home:{}", System.getProperty("user.home"));
        matchFunc.accept("user name:{}", System.getProperty("user.name"));
        matchFunc.accept("user country:{}", System.getProperty("user.country"));

        // perm info
        var runtime = Runtime.getRuntime();

        // before print memory info
        // we gc :-)
        runtime.gc();
        matchFunc.accept("total memory:{} mb", String.valueOf(runtime.totalMemory() / 1024 / 1024));
        matchFunc.accept("free memory:{} mb", String.valueOf(runtime.freeMemory() / 1024 / 1024));
        matchFunc.accept("processors:{}", String.valueOf(runtime.availableProcessors()));
        matchFunc.accept("pid:{}", String.valueOf(ProcessHandle.current().pid()));

        // locale info
        matchFunc.accept("encoding:{}", System.getProperty("file.encoding"));
        matchFunc.accept("default locale:{}", Locale.getDefault().toString());
    }

    /**
     * 做一些环境检查
     *
     * @return 如果检查通过返回true，否则false
     */
    public static synchronized boolean check() {
        // 最重要的——检查编码
        var charset = Charset.defaultCharset();

        if (!charset.name().equalsIgnoreCase("utf-8")) {
            LOGGER.error("file.encoding isn't utf-8");
            return false;
        }

        return true;
    }
}
