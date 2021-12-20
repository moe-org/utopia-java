//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The JvmInfo.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 一个简单的，输入jvm信息到debug log的类
 */
public class JvmInfo {

    /**
     * 打印函数
     */
    @FunctionalInterface
    public interface PrintFunc{
        /**
         * 打印
         * @param msg 要打印的信息
         */
        void print(@NotNull String msg);
    }


    /**
     * 输出jvm信息到log(debug级别)
     */
    public static synchronized void print(@NotNull Consumer<String> printFunc){
        Objects.requireNonNull(printFunc);

        BiConsumer<String,String> matchFunc = (String msg, String param) -> {
            printFunc.accept(msg.replace("{}",param));
        };

        // java info
        matchFunc.accept("runtime name:{}",System.getProperty("java.vm.name"));
        matchFunc.accept("runtime version:{}",System.getProperty("java.vm.version"));
        matchFunc.accept("runtime vendor:{}",System.getProperty("java.vendor"));
        matchFunc.accept("java home:{}",System.getProperty("java.version"));
        matchFunc.accept("java version:{}",System.getProperty("java.home"));

        // os info
        matchFunc.accept("os name:{}",System.getProperty("os.name"));
        matchFunc.accept("os version:{}",System.getProperty("os.version"));
        matchFunc.accept("os arch:{}",System.getProperty("os.arch"));

        // user info
        matchFunc.accept("user dir:{}",System.getProperty("user.dir"));
        matchFunc.accept("user home:{}",System.getProperty("user.home"));
        matchFunc.accept("user name:{}",System.getProperty("user.name"));
        matchFunc.accept("user country:{}",System.getProperty("user.country"));

        // perm info
        var runtime = Runtime.getRuntime();

        matchFunc.accept("max memory:{} mb", String.valueOf(runtime.maxMemory() / 1024));
        matchFunc.accept("free memory:{} mb", String.valueOf(runtime.freeMemory() / 1024));
        matchFunc.accept("processors:{}",String.valueOf( runtime.availableProcessors()));
        matchFunc.accept("pid:{}",String.valueOf(ProcessHandle.current().pid()));

        // locale info
        matchFunc.accept("encoding:{}",System.getProperty("file.encoding"));
        matchFunc.accept("default locale:{}",Locale.getDefault().toString());
    }

}
