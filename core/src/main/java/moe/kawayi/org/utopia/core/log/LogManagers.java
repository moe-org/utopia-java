//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LogManagers.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 整合日志管理器
 */
public class LogManagers extends System.LoggerFinder {

    /**
     * 默认构造函数
     */
    public LogManagers(){}

    private static final AtomicReference<LogManager> GLOBAL_LOG_MANAGER = new AtomicReference<>(
            // 配置日志
            LogUtil.configureLog()
    );

    /**
     * 日志器列表
     */
    private static final LinkedList<WrapLogger> ALL_LOGGERS = new LinkedList<>();

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
        GLOBAL_LOG_MANAGER.set(Objects.requireNonNull(logManager));
    }

    /**
     * 通过LogManager更新所有通过{@link LogManagers#getLogger(String)},
     * {@link LogManagers#getLogger(Class)},
     * {@link LogManagers#getLogger(String,Module)}获取的日志器。
     * <br/>
     * 一般用于{@link LogManagers#setLogManager(LogManager)}后更新之前获取的日志器。
     */
    public static void updateLogger(){
        // get all loggers
        WrapLogger[] loggers = null;
        synchronized (ALL_LOGGERS){
            loggers = new WrapLogger[ALL_LOGGERS.size()];
            ALL_LOGGERS.toArray(loggers);
        }

        // update
        Arrays.stream(loggers).forEach((item) -> {
            item.switchLogger(
                    // 根据原来日志器的名称构造新的日志器
                getLogger(item.getName()));
            });
    }


    /**
     * 使用全局日志管理器获取一个日志器
     * @param clazz 日志器所使用的类。见{@link LogManager#getLogger(Class)}
     * @return 日志器。非空
     */
    @NotNull
    public static Logger getLogger(@NotNull Class<?> clazz){
        var logger = new WrapLogger(GLOBAL_LOG_MANAGER.get().getLogger(clazz));
        synchronized (ALL_LOGGERS){
            ALL_LOGGERS.add(logger);
        }
        return logger;
    }


    /**
     * 使用全局日志管理器获取一个日志器
     * @param name 日志器名称
     * @return 日志器。非空
     */
    @NotNull
    public static Logger getLogger(@NotNull String name){
        var logger = new WrapLogger(GLOBAL_LOG_MANAGER.get().getLogger(name));
        synchronized (ALL_LOGGERS){
            ALL_LOGGERS.add(logger);
        }
        return logger;
    }

    @Override
    @NotNull
    public System.Logger getLogger(@NotNull String name,@NotNull Module module) {
        Objects.requireNonNull(module);
        Objects.requireNonNull(name);

        return getLogger(name);
    }
}
