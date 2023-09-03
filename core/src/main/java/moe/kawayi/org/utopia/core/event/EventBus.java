// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBus.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.util.NotNull;

public interface EventBus<E extends Event> {

    void register(@NotNull Class<? extends E> type, @NotNull Consumer<E> handler);

    void unregister(@NotNull Class<? extends E> type, @NotNull Consumer<E> handler);

    void fire(@NotNull E event);
}
