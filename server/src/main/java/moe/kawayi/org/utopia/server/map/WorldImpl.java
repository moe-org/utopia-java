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

import static moe.kawayi.org.utopia.server.map.WorldInfo.X_BLOCKS_PER_AREA;
import static moe.kawayi.org.utopia.server.map.WorldInfo.Y_BLOCKS_PER_AREA;

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
    private final int areaCountForEveryXQuadrant;

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
    public WorldImpl(long worldId, @NotNull int quadrantSize) throws IllegalArgumentException {
        // null check
        Objects.requireNonNull(quadrantSize, "quadrantSize must not be null");

        if (quadrantSize <= 0) {
            throw new IllegalArgumentException("argument `quadrantSize` is negative.");
        }

        this.worldId = worldId;

        // 设置世界范围
        this.areaCountForEveryXQuadrant = quadrantSize;

        // 设置世界索引范围
        int xAreaLength = quadrantSize * 2;
        int yAreaLength = quadrantSize * 2;

        // 初始化世界索引
        areas = (AtomicReference<Area>[][]) new AtomicReference[xAreaLength][];

        int xIndex = getMinXSize();
        int yIndex = getMinYSize();

        for (int xPtr = 0; xPtr != areas.length; xPtr++) {
            areas[xPtr] = (AtomicReference<Area>[]) new AtomicReference[yAreaLength];

            for (int yPtr = 0; yPtr != areas.length; yPtr++) {
                areas[xPtr][yPtr] = new AtomicReference<>(new AreaImpl(new FlatPosition(xIndex, yIndex)));
                yIndex += Y_BLOCKS_PER_AREA;
            }
            yIndex = getMinYSize();
            xIndex += X_BLOCKS_PER_AREA;
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
        return this.areaCountForEveryXQuadrant * X_BLOCKS_PER_AREA - 1;
    }

    /**
     * @return 返回X轴最小值
     */
    @Override
    public int getMinXSize() {
        return this.areaCountForEveryXQuadrant * X_BLOCKS_PER_AREA;
    }

    /**
     * @return 返回Y轴最大值
     */
    @Override
    public int getMaxYSize() {
        return this.areaCountForEveryXQuadrant * Y_BLOCKS_PER_AREA - 1;
    }

    /**
     * @return 返回Y轴最小值
     */
    @Override
    public int getMinYSize() {
        return this.areaCountForEveryXQuadrant * Y_BLOCKS_PER_AREA;
    }

    @NotNull
    private static int[] getAreaIndexByPosition(int coordinate, int unitSize) {
        int areaIndex;
        int posInArea;

        if (coordinate >= 0) {
            posInArea = coordinate % unitSize;
            areaIndex = Math.floorDiv(coordinate, unitSize);
        } else {
            coordinate = Math.abs(coordinate);
            areaIndex = (int) -Math.ceilDiv(coordinate, unitSize);
            posInArea = (coordinate % unitSize) == 0 ? 0 : unitSize - (coordinate % unitSize);
        }

        return new int[] {areaIndex, posInArea};
    }

    public boolean isIn(@NotNull Position position) {
        Objects.requireNonNull(position);

        return position.x <= this.getMaxXSize()
                && position.x >= this.getMinXSize()
                && position.y <= this.getMaxYSize()
                && position.y >= this.getMinYSize();
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

        if (!this.isIn(position)) {
            return Optional.empty();
        }

        // 检查范围
        var x = getAreaIndexByPosition(position.x, X_BLOCKS_PER_AREA);
        var y = getAreaIndexByPosition(position.y, Y_BLOCKS_PER_AREA);

        var area = this.areas[this.areaCountForEveryXQuadrant + x[0]][this.areaCountForEveryXQuadrant + y[0]];

        return area.get().getBlock(new Position(x[1], y[1], position.z));
    }
}
