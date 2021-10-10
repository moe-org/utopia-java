//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EntityDefault.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.entity;

import moe.kawayi.org.utopia.server.map.WorldPosition;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * 实体类的一个基本抽象实现
 */
public abstract class AbstractEntityDefault implements Entity {

    @Nullable
    private WorldPosition worldPosition = null;

    /**
     * 获取位置
     *
     * @return 实体位置
     */
    @Override
    @NotNull
    public Optional<WorldPosition> getPosition() {
        return Optional.ofNullable(worldPosition);
    }

    /**
     * 设置实体位置
     *
     * @param newPosition 新的实体位置
     */
    @Override
    public void setPosition(@NotNull WorldPosition newPosition) {
        Objects.requireNonNull(newPosition, "newPosition must not be null");
        this.worldPosition = newPosition;
    }

    /**
     * 检查是否需要update
     *
     * @return 如果返回true，则认为需要进行update。否则跳过update调用。
     */
    @Override
    public boolean needUpdate() {
        return true;
    }
}
