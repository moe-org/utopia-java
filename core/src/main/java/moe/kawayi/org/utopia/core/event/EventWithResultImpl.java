// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventWithResultImpl.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * 线程安全.
 */
public class EventWithResultImpl<T> extends EventImpl implements EventWithResult<T> {

    private final AtomicReference<T> result = new AtomicReference<>(null);

    public EventWithResultImpl(@Nullable T defaultResult, boolean cancelable) {
        super(cancelable);
        this.result.set(defaultResult);
    }

    @Override
    @NotNull
    public Optional<T> getResult() {
        return Optional.ofNullable(result.get());
    }

    @Override
    public void setResult(@Nullable T result) {
        this.result.set(result);
    }
}
