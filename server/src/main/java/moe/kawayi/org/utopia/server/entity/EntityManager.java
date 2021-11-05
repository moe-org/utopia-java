//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EntityManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.entity;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体管理器
 */
public final class EntityManager {

    /**
     * 实体列表
     */
    private static final ConcurrentHashMap<String, EntityFactory> ENTITIES_FACTORY = new ConcurrentHashMap<>();

    /**
     * 获取所有实体
     *
     * @return 所有实体的列表
     */
    @NotNull
    public static Collection<EntityFactory> getEntities() {
        return ENTITIES_FACTORY.values();
    }


    /**
     * 注册实体
     *
     * @param entityFactory 要注册的实体工厂
     * @return 如果注册成功，返回true，否则false
     */
    public static boolean addEntity(@NotNull EntityFactory entityFactory) {
        // null check
        Objects.requireNonNull(entityFactory, "entityFactory must not be null");

        var result = ENTITIES_FACTORY.putIfAbsent(entityFactory.getEntityId(), entityFactory);
        // 如果成功映射，则返回null
        return result == null;
    }

    /**
     * 删除实体
     *
     * @param entityId 要删除的实体的id
     */
    public static void removeEntity(@NotNull String entityId) {
        // null check
        Objects.requireNonNull(entityId, "entityId must not be null");

        ENTITIES_FACTORY.remove(entityId);
    }

    /**
     * 获取实体
     *
     * @param entityId 实体Id
     * @return 如果实体已经注册，返回实体的工厂，否则返回空{@link Optional#empty()}
     */
    @NotNull
    public static Optional<EntityFactory> getEntity(@NotNull String entityId) {
        // null check
        Objects.requireNonNull(entityId, "entityId must not be null");

        return Optional.ofNullable(ENTITIES_FACTORY.get(entityId));
    }
}
