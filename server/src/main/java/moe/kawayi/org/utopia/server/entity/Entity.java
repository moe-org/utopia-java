//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Entity.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.entity;

import moe.kawayi.org.utopia.server.map.WorldPosition;
import moe.kawayi.org.utopia.server.util.NotNull;

import java.util.Optional;

/**
 * 实体接口
 */
public interface Entity {

    /**
     * 获取位置
     *
     * @return 实体位置；如果未设置则返回空Optional.
     */
    @NotNull
    Optional<WorldPosition> getPosition();

    /**
     * 设置实体位置
     *
     * @param newPosition 新的实体位置
     */
    void setPosition(@NotNull WorldPosition newPosition);


    /**
     * 获取实体的唯一标识符
     *
     * @return 实体的唯一标识符
     */
    @NotNull
    String getEntityId();

    /**
     * 比较实体
     *
     * @param other 要比较的实体
     * @return 如果实体等价，返回true，否则false
     */
    boolean equalEntity(@NotNull Entity other);

    /**
     * 实体是否可以通过
     *
     * @return 如果可以，返回true，否则false
     */
    boolean isPassable();

    /**
     * 实体是否可碰撞
     *
     * @return 如果可以，返回true，否则false
     */
    boolean isCollideable();

    /**
     * 检查是否需要update
     *
     * @return 如果返回true，则认为需要进行update。否则跳过update调用。
     */
    boolean needUpdate();

    /**
     * 每tick调用
     */
    void update();


}
