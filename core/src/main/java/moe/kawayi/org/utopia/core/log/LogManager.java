//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LogManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 日志管理器接口
 */
public interface LogManager {
    /**
     * 使用默认名称获取一个日志器
     * @return 日志器
     */
    @NotNull
    Logger getLogger();

    /**
     * 获取一个日志器
     * @param name 日志器的名称
     * @return 日志器
     */
    @NotNull
    Logger getLogger(@NotNull String name);

    /**
     * 使用类名获取一个日志器
     * @param clazz 类名
     * @return 日志器
     */
    @NotNull
    Logger getLogger(@NotNull Class<?> clazz);
}
