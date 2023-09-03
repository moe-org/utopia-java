// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBus.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * 事件发布器。线程安全.
 *
 * @param <EventT> 事件参数
 */
public final class EventPublisherImpl<EventT extends Event> implements EventPublisher<EventT> {

    /**
     * 默认构造
     */
    public EventPublisherImpl() {}

    /**
     * 监听者列表
     */
    private final ArrayList<Consumer<EventT>> listeners = new ArrayList<>();

    /**
     * 下一个可供事件使用的id
     */
    private final AtomicLong nextId = new AtomicLong(Long.MIN_VALUE);

    /**
     * 注册一个lambda表达式到事件
     *
     * @param handler 调用者
     */
    @NotNull
    public synchronized void register(@NotNull Consumer<EventT> handler) {
        var id = nextId.getAndIncrement();

        synchronized (listeners) {
            listeners.add(Objects.requireNonNull(handler));
        }
    }

    /**
     * 删除监听函数
     *
     * @param handler 调用者
     */
    public synchronized void unregister(@NotNull Consumer<EventT> handler) {
        // null check;
        synchronized (listeners) {
            listeners.remove(Objects.requireNonNull(handler));
        }
    }

    /**
     * 清除所有注册者
     */
    public synchronized void clear() {
        listeners.clear();
    }

    /**
     * 发布事件。发布事件之后，如果有监听者取消了事件，将会立即返回。
     * 监听者触发的所有异常原样传递。
     *
     * @param obj 事件对象
     */
    public synchronized void fire(@Nullable EventT obj) {
        if (Objects.requireNonNull(obj).isCancelled()) return;

        Consumer<EventT>[] listeners;
        synchronized (this.listeners) {
            listeners = new Consumer[this.listeners.size()];
            this.listeners.toArray(listeners);
        }

        for (var listener : listeners) {
            listener.accept(obj);

            if (obj.isCancelled()) return;
        }
    }
}
