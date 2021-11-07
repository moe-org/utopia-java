//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The WorldPosition.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.map;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 世界坐标
 */
public final class WorldPosition {
    /**
     * 世界Id
     */
    public long worldId;

    /**
     * z轴
     */
    public int z;

    /**
     * x轴
     */
    public int x;

    /**
     * y轴
     */
    public int y;

    /**
     * 构造一个世界坐标
     *
     * @param x       x轴
     * @param y       y轴
     * @param z       z轴
     * @param worldId 世界id
     */
    public WorldPosition(int x, int y, int z, long worldId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldId = worldId;
    }

    /**
     * 降级到{@link Position}，忽略世界id
     *
     * @return 降级的Position
     */
    @NotNull
    public Position downgrade() {
        return new Position(x, y, z);
    }

    /**
     * 转换到字符串
     *
     * @return 人类可读字符串
     */
    @Override
    @NotNull
    public String toString() {
        return String.format("WorldPosition{ x:%s y:%s z:%s worldId:%s }", x, y, z, worldId);
    }

    /**
     * 获取对象hash值
     *
     * @return 对象hash值
     */
    @Override
    public int hashCode() {
        return (this.x + this.y + this.z) ^ (((int) (worldId >> 32))) ^ ((int) worldId);
    }

    /**
     * 判断两个WorldPosition是否相等
     *
     * @param obj 要判断的对象
     * @return 如果判断的对象不是 {@link WorldPosition}，或者坐标不相等，返回false。否则返回true
     */
    @Override
    public boolean equals(@NotNull Object obj) {
        if(obj == null)
            return false;

        if (obj instanceof WorldPosition pos) {
            return pos.x == this.x &&
                    pos.y == this.y &&
                    pos.z == this.z &&
                    pos.worldId == this.worldId;
        }

        return false;
    }
}
