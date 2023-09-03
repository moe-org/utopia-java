// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBusImpl.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 线程安全的{@link EventBus}实现
 */
public class EventBusImpl<T extends Event> implements EventBus<T> {

    private final HashMap<Class<?>, List<Consumer<T>>> handlers = new HashMap<>();

    @Override
    public synchronized void register(@NotNull Class<? extends T> type, @NotNull Consumer<T> handler) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(handler);

        if (!handlers.containsKey(type)) {
            handlers.put(type, new ArrayList<>());
        }

        var queue = handlers.get(type);

        queue.add(handler);
    }

    @Override
    public synchronized void unregister(@NotNull Class<? extends T> type, @NotNull Consumer<T> handler) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(handler);

        if (!handlers.containsKey(type)) {
            handlers.put(type, new ArrayList<>());
        }

        var queue = handlers.get(type);

        queue.remove(handler);
    }

    @Override
    public synchronized void fire(@NotNull T event) {
        Objects.requireNonNull(event);

        if (event.isCancelled()) {
            return;
        }

        var got = handlers.get(event.getClass());

        for (var item : got) {
            item.accept(event);

            if (event.isCancelled()) return;
        }
    }
}
