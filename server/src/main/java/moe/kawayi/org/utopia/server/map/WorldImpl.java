// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The WorldImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.map.Position;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 世界类
 */
public final class WorldImpl implements World {

    /**
     * 世界id
     */
    private final long worldId;

    /**
     * x轴最大大小
     * 单位Area
     */
    private final int maxXAreaCount;

    /**
     * x轴最小大小
     * 单位Area
     */
    private final int minXAreaCount;

    /**
     * Y轴最大大小
     * 单位Area
     */
    private final int maxYAreaCount;

    /**
     * Y轴最小大小
     * 单位Area
     */
    private final int minYAreaCount;

    /**
     * area数组 [x][y]
     */
    private final AtomicReference<Area>[][] areas;

    /**
     * 构造一个新世界
     *
     * @param worldId      世界id
     * @param quadrantSize 象限大小。单位Area
     * @throws IllegalArgumentException 如果参数quadrantSize的任意值(x或y)为负数，则抛出。
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public WorldImpl(long worldId, @NotNull FlatPosition quadrantSize) throws IllegalArgumentException {
        // null check
        Objects.requireNonNull(quadrantSize, "quadrantSize must not be null");

        if (quadrantSize.x <= 0 || quadrantSize.y <= 0) {
            throw new IllegalArgumentException("参数quadrantSize的x , y 值为负数.");
        }

        this.worldId = worldId;

        // 设置世界范围
        maxXAreaCount = quadrantSize.x;
        minXAreaCount = -quadrantSize.x;
        maxYAreaCount = quadrantSize.y;
        minYAreaCount = -quadrantSize.y;

        // 设置世界索引范围
        int xAreaLength = Math.abs(maxXAreaCount) + Math.abs(minXAreaCount);
        int yAreaLength = Math.abs(maxYAreaCount) + Math.abs(minYAreaCount);

        // 初始化世界索引
        areas = (AtomicReference<Area>[][]) new AtomicReference[xAreaLength][];

        int xIndex = getMinXSize();
        int yIndex = getMinYSize();

        for (int xPtr = 0; xPtr != areas.length; xPtr++) {
            areas[xPtr] = (AtomicReference<Area>[]) new AtomicReference[yAreaLength];

            for (int yPtr = 0; yPtr != areas.length; yPtr++) {
                areas[xPtr][yPtr] = new AtomicReference<>(new AreaImpl(new FlatPosition(xIndex, yIndex)));
                yIndex += WorldInfo.BLOCK_FLOOR_Y_SIZE;
            }
            yIndex = getMinYSize();
            xIndex += WorldInfo.BLOCK_FLOOR_X_SIZE;
        }
    }

    /**
     * 获取世界id
     *
     * @return 世界id
     */
    @Override
    public long getWorldId() {
        return worldId;
    }

    /**
     * @return 返回X轴最大值
     */
    @Override
    public int getMaxXSize() {
        return maxXAreaCount * WorldInfo.BLOCK_FLOOR_X_SIZE - 1;
    }

    /**
     * @return 返回X轴最小值
     */
    @Override
    public int getMinXSize() {
        return minXAreaCount * WorldInfo.BLOCK_FLOOR_X_SIZE;
    }

    /**
     * @return 返回Y轴最大值
     */
    @Override
    public int getMaxYSize() {
        return maxYAreaCount * WorldInfo.BLOCK_FLOOR_Y_SIZE - 1;
    }

    /**
     * @return 返回Y轴最小值
     */
    @Override
    public int getMinYSize() {
        return minYAreaCount * WorldInfo.BLOCK_FLOOR_Y_SIZE;
    }

    /**
     * 获取方块
     *
     * @param position 方块位置
     * @return 获取到的方块。如果方块未加载或者超出范围，返回空的Optional。
     */
    @Override
    @NotNull
    public Optional<Block> getBlock(@NotNull Position position) {
        // null check
        Objects.requireNonNull(position, "position must not be null");

        // 检查范围
        if (position.x < getMinXSize()
                || position.x > getMaxXSize()
                || position.y < getMinYSize()
                || position.y > getMaxYSize()) return Optional.empty();

        // 获取area坐标
        var area = areas[Math.floorDiv(position.x + Math.abs(getMinXSize()), WorldInfo.BLOCK_FLOOR_X_SIZE)][
                Math.floorDiv((position.y + Math.abs(getMinYSize())), WorldInfo.BLOCK_FLOOR_Y_SIZE)]
                .get();

        if (area == null) return Optional.empty();

        return area.getBlock(position);
    }
}
