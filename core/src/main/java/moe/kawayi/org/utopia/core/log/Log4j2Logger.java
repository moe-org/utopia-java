// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Log4j2Logger.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import java.util.Objects;

import moe.kawayi.org.utopia.core.util.NotNull;

import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;

/**
 * Log4j2的logger
 */
public class Log4j2Logger implements Logger, InternalLogger {

    private final org.apache.logging.log4j.Logger logger;

    /**
     * 使用Log4j2的日志器进行初始化
     * @param logger 要使用的日志器
     */
    public Log4j2Logger(@NotNull org.apache.logging.log4j.Logger logger) {
        Objects.requireNonNull(logger);
        this.logger = logger;
    }

    @Override
    @NotNull
    public String getName() {
        return logger.getName();
    }

    @Override
    @NotNull
    public String name() {
        return getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    // let log4j2 process null

    @Override
    public void trace(/*no-null-mark*/ String msg) {
        logger.trace(msg);
    }

    @Override
    public void trace(/*no-null-mark*/ String format, /*no-null-mark*/ Object arg) {
        logger.trace(format, arg);
    }

    @Override
    public void trace(/*no-null-mark*/ String format, /*no-null-mark*/ Object argA, /*no-null-mark*/ Object argB) {
        logger.trace(format, argA, argB);
    }

    @Override
    public void trace(/*no-null-mark*/ String msg, /*no-null-mark*/ Object... objects) {
        logger.trace(msg, objects);
    }

    @Override
    public void trace(/*no-null-mark*/ Throwable t) {
        logger.trace(t);
    }

    @Override
    public void trace(/*no-null-mark*/ String msg, /*no-null-mark*/ Throwable exception) {
        logger.trace(msg, exception);
    }

    @Override
    public void debug(/*no-null-mark*/ String msg) {
        logger.debug(msg);
    }

    @Override
    public void debug(/*no-null-mark*/ String format, /*no-null-mark*/ Object arg) {
        logger.debug(format, arg);
    }

    @Override
    public void debug(/*no-null-mark*/ String format, /*no-null-mark*/ Object argA, /*no-null-mark*/ Object argB) {
        logger.debug(format, argA, argB);
    }

    @Override
    public void debug(/*no-null-mark*/ String msg, /*no-null-mark*/ Object... objects) {
        logger.debug(msg, objects);
    }

    @Override
    public void debug(/*no-null-mark*/ Throwable t) {
        logger.debug(t);
    }

    @Override
    public void debug(/*no-null-mark*/ String msg, /*no-null-mark*/ Throwable exception) {
        logger.debug(msg, exception);
    }

    @Override
    public void info(/*no-null-mark*/ String msg) {
        logger.info(msg);
    }

    @Override
    public void info(/*no-null-mark*/ String format, /*no-null-mark*/ Object arg) {
        logger.info(format, arg);
    }

    @Override
    public void info(/*no-null-mark*/ String format, /*no-null-mark*/ Object argA, /*no-null-mark*/ Object argB) {
        logger.info(format, argA, argB);
    }

    @Override
    public void info(/*no-null-mark*/ String msg, /*no-null-mark*/ Object... objects) {
        logger.info(msg, objects);
    }

    @Override
    public void info(/*no-null-mark*/ Throwable t) {
        logger.info(t);
    }

    @Override
    public void info(/*no-null-mark*/ String msg, /*no-null-mark*/ Throwable exception) {
        logger.info(msg, exception);
    }

    @Override
    public void warn(/*no-null-mark*/ String msg) {
        logger.warn(msg);
    }

    @Override
    public void warn(/*no-null-mark*/ String format, /*no-null-mark*/ Object arg) {
        logger.warn(format, arg);
    }

    @Override
    public void warn(/*no-null-mark*/ String msg, /*no-null-mark*/ Object... objects) {
        logger.warn(msg, objects);
    }

    @Override
    public void warn(/*no-null-mark*/ String format, /*no-null-mark*/ Object argA, /*no-null-mark*/ Object argB) {
        logger.warn(format, argA, argB);
    }

    @Override
    public void warn(/*no-null-mark*/ Throwable t) {
        logger.warn(t);
    }

    @Override
    public void warn(/*no-null-mark*/ String msg, /*no-null-mark*/ Throwable exception) {
        logger.warn(msg, exception);
    }

    @Override
    public void error(/*no-null-mark*/ String msg) {
        logger.error(msg);
    }

    @Override
    public void error(/*no-null-mark*/ String format, /*no-null-mark*/ Object arg) {
        logger.error(format, arg);
    }

    @Override
    public void error(/*no-null-mark*/ String format, /*no-null-mark*/ Object argA, /*no-null-mark*/ Object argB) {
        logger.error(format, argA, argB);
    }

    @Override
    public void error(/*no-null-mark*/ String msg, /*no-null-mark*/ Object... objects) {
        logger.error(msg, objects);
    }

    @Override
    public void error(/*no-null-mark*/ Throwable t) {
        logger.error(t);
    }

    @Override
    public void error(/*no-null-mark*/ String msg, /*no-null-mark*/ Throwable exception) {
        logger.error(msg, exception);
    }

    @Override
    public boolean isEnabled(@NotNull InternalLogLevel level) {
        return switch (Objects.requireNonNull(level)) {
            case TRACE -> isTraceEnabled();
            case DEBUG -> isDebugEnabled();
            case INFO -> isInfoEnabled();
            case WARN -> isWarnEnabled();
            case ERROR -> isErrorEnabled();
        };
    }

    @Override
    public void log(@NotNull InternalLogLevel level, /*no-null-mark*/ String msg) {
        switch (Objects.requireNonNull(level)) {
            case TRACE -> logger.trace(msg);
            case DEBUG -> logger.debug(msg);
            case INFO -> logger.info(msg);
            case WARN -> logger.warn(msg);
            case ERROR -> logger.error(msg);
        }
    }

    @Override
    public void log(@NotNull InternalLogLevel level, /*no-null-mark*/ String format, /*no-null-mark*/ Object arg) {
        log(level, format, new Object[] {arg});
    }

    @Override
    public void log(
            @NotNull InternalLogLevel level,
            /*no-null-mark*/ String format,
            /*no-null-mark*/ Object argA,
            /*no-null-mark*/ Object argB) {
        log(level, format, new Object[] {argA, argB});
    }

    @Override
    public void log(
            @NotNull InternalLogLevel level, /*no-null-mark*/ String format, /*no-null-mark*/ Object... arguments) {
        switch (Objects.requireNonNull(level)) {
            case TRACE -> logger.trace(format, arguments);
            case DEBUG -> logger.debug(format, arguments);
            case INFO -> logger.info(format, arguments);
            case WARN -> logger.warn(format, arguments);
            case ERROR -> logger.error(format, arguments);
        }
    }

    @Override
    public void log(@NotNull InternalLogLevel level, /*no-null-mark*/ String msg, /*no-null-mark*/ Throwable t) {
        switch (Objects.requireNonNull(level)) {
            case TRACE -> logger.trace(msg, t);
            case DEBUG -> logger.debug(msg, t);
            case INFO -> logger.info(msg, t);
            case WARN -> logger.warn(msg, t);
            case ERROR -> logger.error(msg, t);
        }
    }

    @Override
    public void log(@NotNull InternalLogLevel level, /*no-null-mark*/ Throwable t) {
        switch (Objects.requireNonNull(level)) {
            case TRACE -> logger.trace(t);
            case DEBUG -> logger.debug(t);
            case INFO -> logger.info(t);
            case WARN -> logger.warn(t);
            case ERROR -> logger.error(t);
        }
    }
}
