// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The World.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import java.util.Optional;

import moe.kawayi.org.utopia.core.map.Position;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 世界接口
 */
public interface World {

    /**
     * 获取方块
     *
     * @param position 方块位置
     * @return 获取到的方块。如果方块未加载或者超出范围，返回空的Optional。
     */
    @NotNull
    Optional<Block> getBlock(@NotNull Position position);

    /**
     * 获取世界id
     *
     * @return 世界id
     */
    long getWorldId();

    /**
     * 获取X轴最大值
     * @return 返回X轴最大值
     */
    int getMaxXSize();

    /**
     * 获取X轴最小值
     * @return 返回X轴最小值
     */
    int getMinXSize();

    /**
     * 获取Y轴最大值
     * @return 返回Y轴最大值
     */
    int getMaxYSize();

    /**
     * 获取Y轴最小值
     * @return 返回Y轴最小值
     */
    int getMinYSize();

    /**
     * 检查坐标是否在地图的范围内
     * @param position 坐标
     * @return 如果在范围内,返回true,否则返回false.
     */
    boolean isIn(@NotNull Position position);
}
