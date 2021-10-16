//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBus.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import moe.kawayi.org.utopia.core.util.NotNull;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 事件总线
 *
 * @param <EventT> 事件参数
 */
public final class EventBus<EventT> {

    /**
     * 事件函数
     */
    @NotNull
    private final MethodType eventMethodType;

    /**
     * 构造函数
     *
     * @param type 事件类型
     */
    public EventBus(@NotNull final Class<EventT> type) {
        // null check
        Objects.requireNonNull(type, "type must not be null");

        eventMethodType = MethodType.methodType(void.class, type);
    }

    /**
     * 读写锁
     */
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 监听者列表
     */
    private final List<EventRegistrationId> listeners = new ArrayList<>();

    /**
     * 查找器
     */
    private final MethodHandles.Lookup lookup = MethodHandles.lookup();

    /**
     * 事件注册类。用于标记事件注册结果。作为register返回值。可以用来unregister。
     * <p>
     * 外部类无法使用此类干什么事情。
     */
    private record EventRegistrationId(@NotNull MethodHandle handle) {
        private EventRegistrationId(@NotNull MethodHandle handle) {
            this.handle = handle;
        }
    }

    /**
     * 注册监听函数
     * <pre>{@code
     * MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
     *
     * MethodType mt = MethodType.methodType(void.class, String.class,String.class);
     *
     * MethodHandle hm = publicLookup.findVirtual(String.class, "replace", mt);
     *
     * // note:非static函数需要绑定到一个实例
     * hm = hm.bindTo("The String Object");
     *
     * eventBus.register(hm);
     * }</pre>
     *
     * @param handle 监听函数的句柄
     * @return 监听ID
     */
    public EventRegistrationId register(@NotNull MethodHandle handle) {
        // null check
        Objects.requireNonNull(handle, "handle must not be null");

        // 转换类型
        handle = handle.asType(eventMethodType);

        // write lock
        var lock = rwLock.writeLock();
        lock.lock();

        try {
            var id = new EventRegistrationId(handle);

            listeners.add(id);

            return id;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 注册监听函数
     * 会搜索私有和保护函数
     *
     * <p>
     * Example
     * <pre>
     * {@code
     * var eventBus = createSomethingOutOfNothing();
     *
     * eventBus.register(String.class,
     * "concat", methodType(String.class, String.class))
     * }
     * </pre>
     * 注意:此函数不可注册非静态函数
     *
     * @param cls  要注册监听函数的类
     * @param name 函数名称
     * @param type 函数类型
     * @return 监听ID
     * @throws IllegalAccessException 函数检查失败
     * @throws NoSuchMethodException  函数不存在
     */
    public EventRegistrationId register(@NotNull Class<?> cls, @NotNull String name, @NotNull MethodType type)
            throws IllegalAccessException, NoSuchMethodException {
        // null check
        Objects.requireNonNull(cls, "cls must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(type, "type must not be null");

        // read lock
        var lock = rwLock.readLock();
        lock.lock();

        MethodHandle handle;

        try {
            handle = lookup.findVirtual(cls, name, type);
        } finally {
            lock.unlock();
        }

        return register(handle);
    }

    /**
     * 删除监听函数
     *
     * @param registrationId 注册监听函数时返回的id
     */
    public void unregister(@NotNull EventRegistrationId registrationId) {
        // null check
        Objects.requireNonNull(registrationId, "registrationId must not be null");

        // write lock
        var lock = rwLock.writeLock();
        lock.lock();

        try {
            listeners.remove(registrationId);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 发布事件
     *
     * @param obj 事件对象
     * @throws java.lang.Throwable 由{@link MethodHandle#invoke(Object...)}抛出
     */
    public void post(EventT obj) throws java.lang.Throwable {
        // read lock
        var lock = rwLock.readLock();
        lock.lock();

        try {
            for (var listener : listeners)
                listener.handle.invoke(obj);
        } finally {
            lock.unlock();
        }
    }


}
