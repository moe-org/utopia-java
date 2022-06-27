//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LogStteam.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 日志流
 */
public class LogStream extends OutputStream {

    /**
     * 使用的logger
     */
    private final AtomicReference<Logger> logger = new AtomicReference<>();

    /**
     * 日志器等级
     */
    private final AtomicReference<Level> level = new AtomicReference<>();

    /**
     * 构造一个log stream
     * @param defaultLogger 默认使用的logger
     * @param defaultLevel 默认用来输出的日志等级
     */
    public LogStream(@NotNull Logger defaultLogger, @NotNull Level defaultLevel){
        logger.set(Objects.requireNonNull(defaultLogger));
        level.set(Objects.requireNonNull(defaultLevel));
    }

    /**
     * 切换到新的日志记录器。线程安全的
     * @param logger 日志记录器
     */
    public void setLogger(@NotNull Logger logger){
        this.logger.set(Objects.requireNonNull(logger));
    }

    /**
     * 设置默认日志等级。线程安全的
     * @param level 输出日志等级
     */
    public void setLevel(@NotNull Level level){
        this.level.set(Objects.requireNonNull(level));
    }

    /**
     * 获取输出等级
     * @return 输出等级
     */
    public Level getLevel(){
        return level.get();
    }

    /**
     * 获取日志器
     * @return 日志器
     */
    public Logger getLogger(){
        return logger.get();
    }

    @Override
    public void write(int b) throws IOException {
        logger.get().log(level.get(),String.valueOf((char)b));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        var str = new String(b,off,len);
        logger.get().log(level.get(),str);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }
}
