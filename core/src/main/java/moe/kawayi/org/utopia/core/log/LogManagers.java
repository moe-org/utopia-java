//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LogManagers.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 整合日志管理器
 */
public class LogManagers extends System.LoggerFinder {

    private static final AtomicReference<LogManager> GLOBAL_LOG_MANAGER = new AtomicReference<>(
            new DefaultLogManager()
    );

    /**
     * 获取全局日志管理器
     * @return 日志管理器
     */
    @NotNull
    public static LogManager getLogManager(){
        return GLOBAL_LOG_MANAGER.get();
    }

    /**
     * 设置全局日志管理器
     * @param logManager 日志管理器
     */
    public static void setLogManager(
            @NotNull LogManager logManager
    ){
        Objects.requireNonNull(logManager);
        GLOBAL_LOG_MANAGER.set(logManager);
    }

    /**
     * 使用全局日志管理器获取一个日志器
     * @param clazz 日志器所使用的类。见{@link LogManager#getLogger(Class)}
     * @return 日志器。非空
     */
    @NotNull
    public static Logger getLogger(@NotNull Class<?> clazz){
        return GLOBAL_LOG_MANAGER.get().getLogger(clazz);
    }


    /**
     * 使用全局日志管理器获取一个日志器
     * @param name 日志器名称
     * @return 日志器。非空
     */
    @NotNull
    public static Logger getLogger(@NotNull String name){
        return GLOBAL_LOG_MANAGER.get().getLogger(name);
    }

    @Override
    @NotNull
    public System.Logger getLogger(@NotNull String name,@NotNull Module module) {
        Objects.requireNonNull(module);
        Objects.requireNonNull(name);

        return getLogger(name);
    }
}
