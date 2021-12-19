//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DefaultLogManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

/**
 * 默认的日志管理器
 */
public class DefaultLogManager implements LogManager{
    @Override
    public Logger getLogger() {
        return new DefaultLogger();
    }

    @Override
    public Logger getLogger(String name) {
        return new DefaultLogger();
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new DefaultLogger();
    }
}
