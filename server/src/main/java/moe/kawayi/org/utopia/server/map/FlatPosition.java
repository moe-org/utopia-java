//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FlatPosition.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import java.util.Objects;

/**
 * 平面坐标
 */
public final class FlatPosition {
    /**
     * x轴
     */
    public int x;

    /**
     * y轴
     */
    public int y;

    /**
     * 构造一个平面坐标
     *
     * @param x x轴
     * @param y y轴
     */
    public FlatPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 转换到字符串
     *
     * @return 人类可读字符串
     */
    @Override
    public String toString() {
        return String.format("FlatPosition{ x:%s y:%s }", x, y);
    }


    /**
     * 获取对象hash值
     *
     * @return 对象hash值
     */
    @Override
    public int hashCode() {
        return (x << 16) ^ y;
    }

    /**
     * 判断两个Position是否相等
     *
     * @param obj 要判断的对象
     * @return 如果判断的对象不是 {@link FlatPosition}，或者坐标不相等，返回false。否则返回true
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if (obj instanceof FlatPosition pos) {
            return pos.x == this.x &&
                    pos.y == this.y;
        }

        return false;
    }
}
