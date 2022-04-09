//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBus.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * 事件总线。线程安全
 *
 * @param <EventT> 事件参数
 */
public final class EventBus<EventT extends Event> {

    /**
     * 监听者列表
     */
    private final ConcurrentHashMap<EventRegistrationId, Consumer<EventT>> listeners = new ConcurrentHashMap<>();

    /**
     * 下一个可供事件使用的id
     */
    private final AtomicLong nextId = new AtomicLong(Long.MIN_VALUE);

    /**
     * 事件注册类。用于标记事件注册结果。作为register返回值。可以用来unregister。
     * <p>
     * 外部类无法使用此类干什么事情。
     */
    private record EventRegistrationId(@NotNull long handle) {
        private EventRegistrationId(@NotNull long handle) {
            this.handle = handle;
        }
    }

    /**
     * 注册一个lambda表达式到事件
     * @param caller 调用者
     * @return 注册id
     */
    @NotNull
    public EventRegistrationId register(@NotNull Consumer<EventT> caller){
        Objects.requireNonNull(caller);

        var id = nextId.getAndIncrement();

        var registrationId = new EventRegistrationId(id);

        listeners.put(registrationId,caller);

        return registrationId;
    }

    /**
     * 删除监听函数
     *
     * @param registrationId 注册监听函数时返回的id
     */
    public void unregister(@NotNull EventRegistrationId registrationId) {
        // null check
        Objects.requireNonNull(registrationId);

        listeners.remove(registrationId);
    }

    /**
     * 发布事件。发布事件之后，如果有监听者取消了事件，将会立即返回。
     * <p/>
     * 监听者触发的所有异常原样传递。
     *
     * @param obj 事件对象
     */
    public void fireEvent(@Nullable EventT obj) {

        if(obj.isCancelled())
            return;

        var listeners = this.listeners.values();

        for(var listener : listeners){
            listener.accept(obj);

            if(obj.isCancelled())
                return;
        }
    }


}
