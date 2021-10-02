//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The AreaImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import moe.kawayi.org.utopia.server.util.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

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
     * 获取地图块
     *
     * @param position 要获取的地图块位置
     * @return 获取到的地图块。如果地图块超出范围，则返回空
     * @see BlockFloorImpl#getBlock(FlatPosition)
     */
    @Override
    public Optional<Block> getBlock(@NotNull Position position) {
        // null check
        Objects.requireNonNull(position, "position must not be null");

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
}
