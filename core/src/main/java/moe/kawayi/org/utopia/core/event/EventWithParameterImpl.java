// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventWithParameterImpl.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 线程安全
 */
public class EventWithParameterImpl<T> extends EventImpl implements EventWithParameter<T> {

    private final T parameter;

    public EventWithParameterImpl(T parameter, boolean cancelable) {
        super(cancelable);
        this.parameter = parameter;
    }

    @Override
    @NotNull
    public Optional<T> getParameter() {
        return Optional.ofNullable(parameter);
    }
}
