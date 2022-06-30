// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The BlockFloor.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import java.util.Optional;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 方块层
 * <p>
 * 线程安全
 */
public interface BlockFloor {

    /**
     * 获取方块
     *
     * @param position 位置
     *                 <p>
     *                 位置范围: [0 ..=([X|Y]_SIZE-1)]；
     * @return 如果在范围内，返回获取到的方块，否则返回Empty
     * @see WorldInfo#BLOCK_FLOOR_X_SIZE
     * @see WorldInfo#BLOCK_FLOOR_Y_SIZE
     */
    @NotNull
    Optional<Block> getBlock(@NotNull FlatPosition position);
}
