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
     * 适配函数
     */
    @FunctionalInterface
    private interface PrintFuncConversion{
        void print(@NotNull String msg,@NotNull String param);
    }

    /**
     * 输出jvm信息到log(debug级别)
     */
    public static synchronized void print(@NotNull PrintFunc printFunc){
        Objects.requireNonNull(printFunc);

        PrintFuncConversion matchFunc = (String msg,String param) -> {
            printFunc.print(msg.replace("{}",param));
        };

        // java info
        matchFunc.print("runtime name:{}",System.getProperty("java.vm.name"));
        matchFunc.print("runtime version:{}",System.getProperty("java.vm.version"));
        matchFunc.print("runtime vendor:{}",System.getProperty("java.vendor"));
        matchFunc.print("java home:{}",System.getProperty("java.version"));
        matchFunc.print("java version:{}",System.getProperty("java.home"));

        // os info
        matchFunc.print("os name:{}",System.getProperty("os.name"));
        matchFunc.print("os version:{}",System.getProperty("os.version"));
        matchFunc.print("os arch:{}",System.getProperty("os.arch"));

        // user info
        matchFunc.print("user dir:{}",System.getProperty("user.dir"));
        matchFunc.print("user home:{}",System.getProperty("user.home"));
        matchFunc.print("user name:{}",System.getProperty("user.name"));
        matchFunc.print("user country:{}",System.getProperty("user.country"));

        // perm info
        var runtime = Runtime.getRuntime();

        matchFunc.print("max memory:{} mb", String.valueOf(runtime.maxMemory() / 1024));
        matchFunc.print("free memory:{} mb", String.valueOf(runtime.freeMemory() / 1024));
        matchFunc.print("processors:{}",String.valueOf( runtime.availableProcessors()));
        matchFunc.print("pid:{}",String.valueOf(ProcessHandle.current().pid()));

        // locale info
        matchFunc.print("encoding:{}",System.getProperty("file.encoding"));
        matchFunc.print("default locale:{}",Locale.getDefault().toString());
    }

}
