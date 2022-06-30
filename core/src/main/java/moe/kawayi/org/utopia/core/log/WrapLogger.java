// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The WrapLogger.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * {@link Logger}的包装
 */
public class WrapLogger implements Logger {

    private final AtomicReference<Logger> logger = new AtomicReference<>(null);

    /**
     * 构造一个Logger包装器
     * @param logger 要包装的Logger，不为null
     */
    public WrapLogger(@NotNull Logger logger) {
        this.logger.set(Objects.requireNonNull(logger));
    }

    /**
     * 设置包装器的Logger到新的Logger。此函数线程安全。
     * @param logger 新的Logger，不为null。
     */
    public void switchLogger(@NotNull Logger logger) {
        this.logger.set(Objects.requireNonNull(logger));
    }

    @Override
    @NotNull
    public String getName() {
        return logger.get().getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.get().isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.get().isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.get().isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.get().isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.get().isErrorEnabled();
    }

    @Override
    public void trace(@NotNull String msg) {
        logger.get().trace(msg);
    }

    @Override
    public void trace(@NotNull String msg, @NotNull Object... objects) {
        logger.get().trace(msg, objects);
    }

    @Override
    public void trace(@NotNull String msg, @NotNull Throwable exception) {
        logger.get().trace(msg, exception);
    }

    @Override
    public void debug(@NotNull String msg) {
        logger.get().debug(msg);
    }

    @Override
    public void debug(@NotNull String msg, @NotNull Object... objects) {
        logger.get().debug(msg, objects);
    }

    @Override
    public void debug(@NotNull String msg, @NotNull Throwable exception) {
        logger.get().debug(msg, exception);
    }

    @Override
    public void info(@NotNull String msg) {
        logger.get().info(msg);
    }

    @Override
    public void info(@NotNull String msg, @NotNull Object... objects) {
        logger.get().info(msg, objects);
    }

    @Override
    public void info(@NotNull String msg, @NotNull Throwable exception) {
        logger.get().info(msg, exception);
    }

    @Override
    public void warn(@NotNull String msg) {
        logger.get().warn(msg);
    }

    @Override
    public void warn(@NotNull String msg, @NotNull Object... objects) {
        logger.get().warn(msg, objects);
    }

    @Override
    public void warn(@NotNull String msg, @NotNull Throwable exception) {
        logger.get().warn(msg, exception);
    }

    @Override
    public void error(@NotNull String msg) {
        logger.get().error(msg);
    }

    @Override
    public void error(@NotNull String msg, @NotNull Object... objects) {
        logger.get().error(msg, objects);
    }

    @Override
    public void error(@NotNull String msg, @NotNull Throwable exception) {
        logger.get().error(msg, exception);
    }
}
