//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Logger.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 日志器接口
 *
 * 信息所使用的格式依赖于具体实现
 */
public interface Logger extends System.Logger{
    /**
     * 获取日志的名称
     * @return 名称
     */
    @NotNull
    String getName();

    /**
     * 检查日志等级是否开启
     * @return 如果开启返回true，否则返回false
     */
    boolean isTraceEnabled();

    /**
     * 检查日志等级是否开启
     * @return 如果开启返回true，否则返回false
     */
    boolean isDebugEnabled();

    /**
     * 检查日志等级是否开启
     * @return 如果开启返回true，否则返回false
     */
    boolean isInfoEnabled();

    /**
     * 检查日志等级是否开启
     * @return 如果开启返回true，否则返回false
     */
    boolean isWarnEnabled();

    /**
     * 检查日志等级是否开启
     * @return 如果开启返回true，否则返回false
     */
    boolean isErrorEnabled();

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
    void trace(@NotNull String msg, @NotNull Throwable exception);


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
    void debug(@NotNull String msg, @NotNull Throwable exception);


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
    void info(@NotNull String msg, @NotNull Throwable exception);


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
    void warn(@NotNull String msg, @NotNull Throwable exception);


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
    void error(@NotNull String msg, @NotNull Throwable exception);

    @Override
    default boolean isLoggable(@NotNull Level level){
        Objects.requireNonNull(level);

        return switch(level){
            // 我们将ALL重定向到ERROR
            case ALL -> isErrorEnabled();
            // 我们将OFF重定向到TRACE
            case OFF -> isTraceEnabled();
            // 照常对应
            case TRACE -> isTraceEnabled();
            case DEBUG -> isDebugEnabled();
            case INFO -> isInfoEnabled();
            case WARNING -> isWarnEnabled();
            case ERROR -> isErrorEnabled();
        };
    }

    @Override
    default void log(@NotNull Level level,@NotNull  String msg) {
        log(level, (ResourceBundle) null, msg, (Object[]) null);
    }

    @Override
    default void log(@NotNull Level level, @NotNull Supplier<String> msgSupplier) {
        Objects.requireNonNull(msgSupplier);
        log(level, (ResourceBundle) null, msgSupplier.get(), (Object[]) null);
    }

    @Override
    default void log(@NotNull Level level,@NotNull  Object obj) {
        Objects.requireNonNull(obj);
        this.log(level, (ResourceBundle) null, obj.toString(), (Object[]) null);
    }

    @Override
    default void log(@NotNull Level level,@NotNull  String msg,@Nullable Throwable thrown) {
        this.log(level, null, msg, thrown);
    }

    @Override
    default void log(@NotNull Level level, @NotNull Supplier<String> msgSupplier, @Nullable Throwable thrown) {
        Objects.requireNonNull(msgSupplier);
        this.log(level, null, msgSupplier.get(), thrown);
    }

    @Override
    default void log(@NotNull Level level, @NotNull String format, @NotNull Object... params) {
        this.log(level, null, format, params);
    }

    @Override
    default void log(
            @NotNull Level level,
            @Nullable  ResourceBundle bundle,
            @NotNull  String msg,
            @Nullable Throwable thrown){
        Objects.requireNonNull(level);
        Objects.requireNonNull(msg);

        BiConsumer<Consumer<String>,BiConsumer<String,Throwable>> log =
                log = (Consumer<String> logFunc,BiConsumer<String,Throwable> throwFunc) -> {
                    if(thrown == null)
                        logFunc.accept(msg);
                    else
                        throwFunc.accept(msg,thrown);
                };

        if(bundle != null){
            throw new IllegalStateException("try to use no supported parameter(bundle(ResourceBundle))");
        }

        switch(level){
            // map OFF to TRACE
            case OFF, TRACE -> {
                if(isLoggable(level)){
                    log.accept(this::trace,this::trace);
                }
            }
            // map ALL to ERROR
            case ALL, ERROR -> {
                if(isLoggable(level)){
                    log.accept(this::error,this::error);
                }
            }
            case DEBUG -> {
                if(isLoggable(level)){
                    log.accept(this::debug,this::debug);
                }
            }
            case INFO -> {
                if(isLoggable(level)){
                    log.accept(this::info,this::info);
                }
            }
            case WARNING -> {
                if(isLoggable(level)){
                    log.accept(this::warn,this::warn);
                }
            }
        }
    }

    @Override
    default void log(@NotNull Level level,
                     @Nullable ResourceBundle bundle,
                     @NotNull String format,
                     @NotNull  Object... params){
        Objects.requireNonNull(level);
        Objects.requireNonNull(format);

        if(bundle != null){
            throw new IllegalStateException("try to use no supported parameter(bundle(ResourceBundle))");
        }

        switch(level){
            // map OFF to TRACE
            case OFF, TRACE -> {
                if(isLoggable(level)){
                    trace(format,params);
                }
            }
            // map ALL to ERROR
            case ALL, ERROR -> {
                if(isLoggable(level)){
                    error(format,params);
                }
            }
            case DEBUG -> {
                if(isLoggable(level)){
                    debug(format,params);
                }
            }
            case INFO -> {
                if(isLoggable(level)){
                    info(format,params);
                }
            }
            case WARNING -> {
                if(isLoggable(level)){
                    warn(format,params);
                }
            }
        }
    }
}
