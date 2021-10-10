//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EntityFactory.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.entity;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 实体工厂
 */
public interface EntityFactory {
    /**
     * 获取生产的实体的唯一标识符
     * @return  生产的实体的唯一标识符
     */
    @NotNull
    String getEntityId();

    /**
     * 生产实体
     * @return  生产的实体
     */
    @NotNull
    Entity produce();
}
