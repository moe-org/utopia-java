//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The BlockImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import moe.kawayi.org.utopia.server.entity.Entity;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 一个地图块
 * <p>
 * 线程安全
 */
public final class BlockImpl implements Block {

    /**
     * 实体列表
     */
    private final ArrayList<Entity> entities = new ArrayList<>(/*事不过三*/3);

    /**
     * 是否可通过
     */
    private int cannotPassableCount = 0;

    /**
     * 是否可碰撞
     */
    private @NotNull
    Optional<Entity> collision = Optional.empty();

    /**
     * 读写锁
     */
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 添加实体
     * 注:如果实体重复会导致添加失败
     *
     * @param entity 要添加的实体，不为null
     * @return 如果添加成功，返回true，否则返回false
     */
    @Override
    public boolean addEntity(@NotNull final Entity entity) {
        // null check
        Objects.requireNonNull(entity, "entity must not be null");

        // 写锁
        var lock = rwLock.writeLock();
        lock.lock();

        try {
            var isPassable = entity.isPassable();
            var collision = entity.isCollideable();

            // 检查碰撞体
            if (collision && (this.collision.isPresent())) {
                return false;
            } else if (collision) {
                this.collision = Optional.of(entity);
            }

            // 检查通过性
            if (!isPassable) {
                cannotPassableCount++;
            }

            // 添加
            entities.add(entity);

            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 供remove系列函数使用
     */
    private interface RemoveCallback {
        boolean callable(@NotNull Entity removed);
    }

    /**
     * 删除实体
     *
     * @param removeCallback 判断一个实体是否需要删除
     * @see RemoveCallback
     */
    private void removeBody(@NotNull RemoveCallback removeCallback) {
        // 写锁
        var lock = rwLock.writeLock();
        lock.lock();

        try {
            // remove it
            entities.removeIf(
                    entity -> {
                        var result = removeCallback.callable(entity);

                        // 处理后事
                        if (result) {
                            if (!entity.isPassable()) {
                                cannotPassableCount--;
                            }

                            if (entity.isCollideable()) {
                                collision = null;
                            }
                        }

                        return result;
                    });
        } finally {
            lock.unlock();
        }
    }

    /**
     * 删除实体
     *
     * @param entityId 要删除的实体的ID
     * @see RemoveCallback
     */
    @Override
    public void removeEntity(@NotNull String entityId) {
        // null check
        Objects.requireNonNull(entityId, "entityId must not be null");

        removeBody(entity -> entity.getEntityId().equals(entityId));
    }

    /**
     * 删除实体
     *
     * @param removeEntity 要删除的实体的引用
     * @see RemoveCallback
     */
    @Override
    public void removeEntity(@NotNull Entity removeEntity) {
        // null check
        Objects.requireNonNull(removeEntity, "removeEntity must not be null");

        removeBody(entity -> entity.equalEntity(removeEntity));
    }

    /**
     * 此方块是否能通过
     *
     * @return 如果返回true，则可以通过，否则false
     */
    @Override
    public boolean canPass() {
        // 读锁
        var lock = rwLock.readLock();
        lock.lock();

        try {
            return cannotPassableCount == 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 此方块是否能碰撞
     *
     * @return 如果返回true，则可以碰撞，否则false
     */
    @Override
    public boolean canCollide() {
        // 读锁
        var lock = rwLock.readLock();
        lock.lock();

        try {
            return collision != null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取碰撞体
     *
     * @return 如果此方块拥有碰撞体，则返回，否则返回空Optional
     */
    @NotNull
    @Override
    public Optional<Entity> getCollide() {
        // 读锁
        var lock = rwLock.readLock();
        lock.lock();

        try {
            return collision;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取所有实体
     *
     * @return 所有实体的列表
     */
    @Override
    @NotNull
    public Entity[] getEntities() {
        // 读锁
        var lock = rwLock.readLock();
        lock.lock();

        try {
            var array = new Entity[entities.size()];

            entities.toArray(array);

            return array;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 查找实体
     *
     * @param entityId 要查找的实体的ID
     * @return 查找结果
     */
    @Override
    @NotNull
    public Entity[] findEntities(@NotNull String entityId) {
        // null check
        Objects.requireNonNull(entityId, "entityId must not be null");

        // 读锁
        var lock = rwLock.readLock();
        lock.lock();

        try {
            ArrayList<Entity> result = new ArrayList<>();

            for (var entity : entities)
                if (entity.getEntityId().equals(entityId))
                    result.add(entity);

            Entity[] outputBuffer = new Entity[result.size()];
            result.toArray(outputBuffer);

            return outputBuffer;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 查找实体是否存在
     *
     * @param entityId 要查找的实体的ID
     * @return 查找结果，如果存在返回true，否则false
     */
    @Override
    public boolean contain(@NotNull String entityId) {
        // null check
        Objects.requireNonNull(entityId, "entityId must not be null");

        // 读锁
        var lock = rwLock.readLock();
        lock.lock();

        try {
            for (var entity : entities)
                if (entity.getEntityId().equals(entityId))
                    // found it
                    return true;

            // not found
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contain(@NotNull Entity entity) {
        // null check
        Objects.requireNonNull(entity);

        // 读锁
        var lock = rwLock.readLock();
        lock.lock();

        try{
            for (var item : entities)
                if (entity == item)
                    return true;

            return false;
        }
        finally {
            lock.unlock();
        }
    }
}
