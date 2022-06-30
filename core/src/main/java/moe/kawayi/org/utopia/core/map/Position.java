// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Position.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.map;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 坐标
 */
public final class Position {

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
     * 构造一个坐标
     *
     * @param x x轴
     * @param y y轴
     * @param z z轴
     */
    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * 降级到{@link FlatPosition}，忽略z轴
     *
     * @return 降级的FlatPosition
     */
    @NotNull
    public FlatPosition downgrade() {
        return new FlatPosition(x, y);
    }

    /**
     * 转换到字符串
     *
     * @return 人类可读字符串
     */
    @Override
    @NotNull
    public String toString() {
        return String.format("Position{ x:%s y:%s z:%s }", x, y, z);
    }

    /**
     * 获取对象hash值
     *
     * @return 对象hash值
     */
    @Override
    public int hashCode() {
        return (z << 16) ^ ((x + y));
    }

    /**
     * 判断两个Position是否相等
     *
     * @param obj 要判断的对象
     * @return 如果判断的对象不是 {@link Position}，或者坐标不相等，返回false。否则返回true。如果obj参数为空，始终返回false。
     */
    @Override
    public boolean equals(@NotNull Object obj) {
        if (obj == null) return false;

        if (obj instanceof Position pos) {
            return pos.x == this.x && pos.y == this.y && pos.z == this.z;
        }

        return false;
    }
}
