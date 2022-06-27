//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Block.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.server.entity.Entity;

import java.util.Optional;

/**
 * 方块接口
 */
public interface Block {

    /**
     * 添加实体
     * 注:如果实体重复会导致添加失败
     *
     * @param entity 要添加的实体，不为null
     * @return 如果添加成功，返回true，否则返回false
     */
    boolean addEntity(@NotNull Entity entity);

    /**
     * 删除实体
     *
     * @param entityId 要删除的实体的ID
     */
    void removeEntity(@NotNull String entityId);

    /**
     * 删除实体
     *
     * @param other 要删除的实体的引用
     */
    void removeEntity(@NotNull Entity other);

    /**
     * 此方块是否能通过
     *
     * @return 如果返回true，则可以通过，否则false
     */
    boolean canPass();

    /**
     * 此方块是否能碰撞
     *
     * @return 如果返回true，则可以碰撞，否则false
     */
    boolean canCollide();

    /**
     * 获取碰撞体
     *
     * @return 如果此方块拥有碰撞体，则返回，否则返回空Optional
     */
    @NotNull
    Optional<Entity> getCollide();

    /**
     * 获取所有实体
     *
     * @return 所有实体的列表
     */
    @NotNull
    Entity[] getEntities();

    /**
     * 查找实体
     *
     * @param entityId 要查找的实体的ID
     * @return 查找结果
     */
    @NotNull
    public Entity[] findEntities(@NotNull String entityId);

    /**
     * 查找实体是否存在
     *
     * @param entityId 要查找的实体的ID
     * @return 查找结果，如果存在返回true，否则false
     */
    boolean contain(@NotNull String entityId);

    /**
     * 查找实体是否存在
     *
     * @param entity 要查找的实体的引用
     * @return 查找结果，如果存在返回true，否则false
     */
    boolean contain(@NotNull Entity entity);
}
