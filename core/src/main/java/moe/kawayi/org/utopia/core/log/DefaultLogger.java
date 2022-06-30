// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DefaultLogger.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import java.util.Objects;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 默认日志器
 */
public class DefaultLogger implements Logger {

    private final String name;

    /**
     * 构造一个默认日志器
     * @param name 日志器名称
     */
    public DefaultLogger(@NotNull String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    @Override
    @NotNull
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    @NotNull
    public void trace(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void trace(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void trace(@NotNull String msg, @NotNull Throwable exception) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void debug(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void debug(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void debug(@NotNull String msg, @NotNull Throwable exception) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void info(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void info(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void info(@NotNull String msg, @NotNull Throwable exception) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void warn(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void warn(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void warn(@NotNull String msg, @NotNull Throwable exception) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void error(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void error(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void error(@NotNull String msg, @NotNull Throwable exception) {
        System.out.println(msg);
    }
}
