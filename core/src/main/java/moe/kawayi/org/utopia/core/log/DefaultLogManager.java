// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DefaultLogManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import java.util.concurrent.atomic.AtomicLong;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 默认的日志管理器
 */
public class DefaultLogManager implements LogManager {

    /**
     * 默认构造函数
     */
    public DefaultLogManager() {}

    private final AtomicLong loggerCount = new AtomicLong(0);

    @Override
    @NotNull
    public Logger getLogger() {
        return new DefaultLogger("default logger - " + loggerCount.getAndIncrement());
    }

    @Override
    @NotNull
    public Logger getLogger(@NotNull String name) {
        return this.getLogger();
    }

    @Override
    @NotNull
    public Logger getLogger(@NotNull Class<?> clazz) {
        return this.getLogger();
    }
}
