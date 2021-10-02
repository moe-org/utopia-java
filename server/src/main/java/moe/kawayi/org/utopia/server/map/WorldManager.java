//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The WorldManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import moe.kawayi.org.utopia.server.util.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 世界管理器
 */
public final class WorldManager {

    /**
     * 世界列表
     */
    private static final ConcurrentHashMap<Long, World> worlds = new ConcurrentHashMap<>();

    /**
     * 获取所有世界
     *
     * @return 所有世界的列表
     */
    @NotNull
    public static Collection<World> getEntities() {
        return worlds.values();
    }


    /**
     * 注册世界
     *
     * @param world 要注册的世界
     * @return 如果注册成功，返回true，否则false
     */
    public static boolean addEntity(@NotNull World world) {
        // null check
        Objects.requireNonNull(world, "world must not be null");

        var result = worlds.putIfAbsent(world.getWorldId(), world);
        // 如果成功映射，则返回null
        return result == null;
    }

    /**
     * 删除世界
     *
     * @param worldId 要删除的世界的id
     */
    public static void removeEntity(long worldId) {
        worlds.remove(worldId);
    }

    /**
     * 获取世界
     *
     * @param worldId 世界id
     * @return 如果实际已经注册，返回世界的引用，否则返回空:{@link Optional#empty()}
     */
    public static Optional<World> getEntity(long worldId) {
        return Optional.ofNullable(worlds.get(worldId));
    }
}
