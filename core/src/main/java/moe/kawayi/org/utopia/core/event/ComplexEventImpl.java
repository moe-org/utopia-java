// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ComplexEventImpl.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * 线程安全
 */
public class ComplexEventImpl<T> extends EventWithResultImpl<T> implements ComplexEvent<T> {

    private final T parameter;

    public ComplexEventImpl(@Nullable T parameter, @Nullable T defaultResult, boolean cancelAble) {
        super(defaultResult, cancelAble);
        this.parameter = parameter;
    }

    @Override
    @NotNull
    public Optional<T> getParameter() {
        return Optional.ofNullable(this.parameter);
    }
}
