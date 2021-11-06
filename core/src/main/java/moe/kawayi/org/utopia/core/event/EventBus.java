//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBus.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * 事件总线
 *
 * @param <EventT> 事件参数
 */
public final class EventBus<EventT extends Event> {

    /**
     * 监听者列表
     */
    private final ConcurrentHashMap<EventRegistrationId,Function<EventT,Void>> listeners = new ConcurrentHashMap<>();

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
    public EventRegistrationId register(@NotNull Function<EventT,Void> caller){
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
        Objects.requireNonNull(registrationId, "registrationId must not be null");

        listeners.remove(registrationId);
    }

    /**
     * 发布事件。发布事件之后，此函数当前调用返回之前，
     * 调用{@link EventBus#unregister(EventRegistrationId)}和{@link EventBus#register(Function)} )}将对此函数当前调用无效，直到当前调用返回或者发起一次新调用。
     *
     * @param obj 事件对象
     */
    public void fireEvent(@NotNull EventT obj) {
        Objects.requireNonNull(obj);

        if(obj.isCancel())
            return;

        var listeners = this.listeners.values();

        for(var listener : listeners){
            listener.apply(obj);

            if(obj.isCancel())
                return;
        }
    }


}
