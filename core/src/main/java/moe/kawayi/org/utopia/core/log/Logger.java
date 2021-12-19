//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Logger.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Objects;

/**
 * 日志器接口
 *
 * 信息所使用的格式依赖于具体实现
 */
public interface Logger {
    /**
     * 获取日志的名称
     * @return 名称
     */
    @NotNull
    String getName();

    /**
     * 打印日志
     * @param msg 信息
     */
    void trace(@NotNull String msg);

    /**
     * 打印日志
     * @param msg 信息
     * @param objects 日志对象
     */
    void trace(@NotNull String msg, @NotNull Object... objects);

    /**
     * 打印日志
     * @param msg 信息
     * @param exception 日志异常
     */
    void trace(@NotNull String msg, @NotNull Exception exception);


    /**
     * 打印日志
     * @param msg 信息
     */
    void debug(@NotNull String msg);

    /**
     * 打印日志
     * @param msg 信息
     * @param objects 日志对象
     */
    void debug(@NotNull String msg, @NotNull Object... objects);

    /**
     * 打印日志
     * @param msg 信息
     * @param exception 日志异常
     */
    void debug(@NotNull String msg, @NotNull Exception exception);


    /**
     * 打印日志
     * @param msg 信息
     */
    void info(@NotNull String msg);

    /**
     * 打印日志
     * @param msg 信息
     * @param objects 日志对象
     */
    void info(@NotNull String msg, @NotNull Object... objects);

    /**
     * 打印日志
     * @param msg 信息
     * @param exception 日志异常
     */
    void info(@NotNull String msg, @NotNull Exception exception);


    /**
     * 打印日志
     * @param msg 信息
     */
    void warn(@NotNull String msg);

    /**
     * 打印日志
     * @param msg 信息
     * @param objects 日志对象
     */
    void warn(@NotNull String msg, @NotNull Object... objects);

    /**
     * 打印日志
     * @param msg 信息
     * @param exception 日志异常
     */
    void warn(@NotNull String msg, @NotNull Exception exception);


    /**
     * 打印日志
     * @param msg 信息
     */
    void error(@NotNull String msg);

    /**
     * 打印日志
     * @param msg 信息
     * @param objects 日志对象
     */
    void error(@NotNull String msg, @NotNull Object... objects);

    /**
     * 打印日志
     * @param msg 信息
     * @param exception 日志异常
     */
    void error(@NotNull String msg, @NotNull Exception exception);
}
