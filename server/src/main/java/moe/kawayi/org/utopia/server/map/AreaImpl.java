// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The AreaImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.map.Position;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * 地图区域
 * <p>
 * 线程安全
 */
public final class AreaImpl implements Area {

    /**
     * 地面高度层
     */
    private final BlockFloorImpl ground = new BlockFloorImpl();

    /**
     * 其他高度层
     */
    private final Map<Integer, BlockFloorImpl> height = new HashMap<>();

    /**
     * 读写锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 原点位置
     */
    private final FlatPosition origin;

    /**
     * 构造一个区域
     * @param origin 区域坐标。只能是{@link WorldInfo#X_BLOCKS_PER_AREA}和{@link WorldInfo#Y_BLOCKS_PER_AREA}的倍数
     */
    public AreaImpl(@NotNull FlatPosition origin) {
        Objects.requireNonNull(origin);

        if (origin.x % WorldInfo.X_BLOCKS_PER_AREA != 0) {
            throw new IllegalArgumentException("origin.x不是WorldInfo.BLOCK_FLOOR_X_SIZE的倍数");
        }
        if (origin.y % WorldInfo.Y_BLOCKS_PER_AREA != 0) {
            throw new IllegalArgumentException("origin.y不是WorldInfo.BLOCK_FLOOR_Y_SIZE的倍数");
        }

        this.origin = origin;
    }

    @Nullable
    private Position getPositionRelativeToOrigin(@NotNull Position position) {
        // null check
        Objects.requireNonNull(position, "position must not be null");

        if (position.x >= (origin.x + WorldInfo.X_BLOCKS_PER_AREA) || position.x < origin.x) return null;

        if (position.y >= (origin.y + WorldInfo.Y_BLOCKS_PER_AREA) || position.y < origin.y) return null;

        return new Position(
                origin.x >= 0 ? Math.abs(position.x) - Math.abs(origin.x) : Math.abs(origin.x) - Math.abs(position.x),
                origin.y >= 0 ? Math.abs(position.y) - Math.abs(origin.y) : Math.abs(origin.y) - Math.abs(position.y),
                position.z);
    }

    /**
     * 获取地图块
     *
     * @param position 要获取的地图块位置。相对于原点位置
     * @return 获取到的地图块。如果地图块超出范围，则返回空
     * @see BlockFloorImpl#getBlock(FlatPosition)
     */
    @Override
    @NotNull
    public Optional<Block> getBlock(@NotNull Position position) {
        // null check
        Objects.requireNonNull(position, "position must not be null");

        position = getPositionRelativeToOrigin(position);

        if (position == null) return Optional.empty();

        // it's thread safe for BlockFloorImpl
        if (position.z == WorldInfo.GROUND_Z) {
            return ground.getBlock(position.downgrade());
        }

        // entry write lock
        lock.lock();

        try {
            if (height.containsKey(position.z)) {
                // 高度层存在
                var got = height.get(position.z);

                return got.getBlock(position.downgrade());
            } else {
                // 构造新高度层
                BlockFloorImpl newFloor = new BlockFloorImpl();

                height.put(position.z, newFloor);

                return newFloor.getBlock(position.downgrade());
            }

        } finally {
            lock.unlock();
        }
    }

    @Override
    @NotNull
    public FlatPosition getPosition() {
        return origin;
    }
}
