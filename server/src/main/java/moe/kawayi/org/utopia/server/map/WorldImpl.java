//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The WorldImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import moe.kawayi.org.utopia.server.util.NotNull;
import moe.kawayi.org.utopia.server.util.Nullable;

import java.util.Objects;
import java.util.Optional;

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
     * Area包装
     */
    private static class AreaWrapper {
        /**
         * 锁
         */
        @NotNull
        private final Object wrapperLocker = new byte[0];

        /**
         * 内部值
         */
        @Nullable
        private AreaImpl innerValue = null;

        /**
         * 获取此区域加载情况
         *
         * @return 如果此区域被加载，返回true，否则false
         */
        boolean isLoaded() {
            synchronized (wrapperLocker) {
                return innerValue != null;
            }
        }

        /**
         * 获取此区域
         *
         * @return 如果此区域被加载，返回带值的Optional，否则返回空Optional
         */
        Optional<AreaImpl> get() {
            synchronized (wrapperLocker) {
                return Optional.ofNullable(innerValue);
            }
        }

        /**
         * 设置区域
         *
         * @param value 要设置成的值
         */
        void set(@NotNull AreaImpl value) {
            synchronized (wrapperLocker) {
                innerValue = value;
            }
        }

        /**
         * 如果取余为空，则设置
         *
         * @param value 要设置到的值
         * @return 如果设置成功，返回true，否则false
         */
        boolean setIfEmpty(@NotNull AreaImpl value) {
            synchronized (wrapperLocker) {
                if (innerValue == null) {
                    innerValue = value;
                    return true;
                } else {
                    return false;
                }
            }
        }

    }

    /**
     * area数组
     */
    private final AreaWrapper[] areas;


    /**
     * 构造一个新世界
     *
     * @param worldId      世界id
     * @param quadrantSize 象限大小。单位Area
     * @throws WorldConstructionException 如果参数quadrantSize的任意值(x或y)为负数，则抛出。
     */
    public WorldImpl(long worldId, @NotNull FlatPosition quadrantSize) throws WorldConstructionException {
        // null check
        Objects.requireNonNull(quadrantSize, "quadrantSize must not be null");

        if (quadrantSize.x <= 0 || quadrantSize.y <= 0) {
            throw new WorldConstructionException("参数quadrantSize的x , y 值为负数.");
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

        int worldMaxIndex = xAreaLength * yAreaLength;

        // 初始化世界索引
        areas = new AreaWrapper[worldMaxIndex];

        for (int ptr = 0; ptr != worldMaxIndex; ptr++) {
            areas[ptr] = new AreaWrapper();
            areas[ptr].set(new AreaImpl());
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
    public Optional<Block> getBlock(Position position) {
        // null check
        Objects.requireNonNull(position, "position must not be null");

        // 检查范围
        if (position.x > getMaxXSize() || position.x < getMinXSize()
                ||
                position.y > getMaxYSize() || position.y < getMinYSize())
            return Optional.empty();

        // 获取索引
        int xArea = Math.floorDiv(position.x, WorldInfo.BLOCK_FLOOR_X_SIZE);
        int yArea = Math.floorDiv(position.y, WorldInfo.BLOCK_FLOOR_Y_SIZE);

        int index = convertPosToAreaIndex(xArea, yArea);

        var got = areas[index].get();

        if (got.isEmpty())
            return Optional.empty();

        // 获取area内偏移
        // 取余运算; 结果符号同被取余数。
        int xPos = Math.abs(position.x) % WorldInfo.BLOCK_FLOOR_X_SIZE;
        int yPos = Math.abs(position.y) % WorldInfo.BLOCK_FLOOR_Y_SIZE;

        if (xPos != 0 && position.x < 0) {
            xPos = WorldInfo.BLOCK_FLOOR_X_SIZE - xPos;
        }

        if (yPos != 0 && position.y < 0) {
            yPos = WorldInfo.BLOCK_FLOOR_Y_SIZE - yPos;
        }

        return got.get().getBlock(new Position(xPos, yPos, position.z));
    }


    /**
     * 转换坐标到数组索引
     *
     * @param xArea area的x轴索引
     * @param yArea area的y轴索引
     * @return 索引
     */
    private int convertPosToAreaIndex(int xArea, int yArea) {
        int x = Math.abs(minXAreaCount) + xArea;
        int y = Math.abs(minYAreaCount) + yArea;

        // access as [x][y]
        return x * (Math.abs(minXAreaCount) + Math.abs(maxXAreaCount)) + y;
    }
}
