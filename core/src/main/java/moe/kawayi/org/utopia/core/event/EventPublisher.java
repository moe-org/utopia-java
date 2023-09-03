// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventPublisher.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.util.NotNull;

public interface EventPublisher<E extends Event> {

    void register(@NotNull Consumer<E> handler);

    /**
     * 当多个相同的{@link Consumer<E>}注册的时候只会删除第一个
     * @param handler 注册的时候的handle
     */
    void unregister(@NotNull Consumer<E> handler);

    void fire(@NotNull E event);
}
