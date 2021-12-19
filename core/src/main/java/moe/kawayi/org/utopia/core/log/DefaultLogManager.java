//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DefaultLogManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 默认的日志管理器
 */
public class DefaultLogManager implements LogManager{
    @Override
    @NotNull
    public Logger getLogger() {
        return new DefaultLogger();
    }

    @Override
    @NotNull
    public Logger getLogger(@NotNull String name) {
        return new DefaultLogger();
    }

    @Override
    @NotNull
    public Logger getLogger(@NotNull Class<?> clazz) {
        return new DefaultLogger();
    }
}
