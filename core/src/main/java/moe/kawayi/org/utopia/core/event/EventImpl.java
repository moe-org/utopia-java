//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * 可取消的事件实现。
 *
 * 非线程安全。
 */
public class EventImpl<Param> implements Event{

    private final Param param;
    private boolean cancel = false;

    public EventImpl(@Nullable Param param){
        this.param = param;
    }


    @Override
    public boolean isCancel() {
        return cancel;
    }

    @Override
    public void setCancel(boolean value) {
        cancel = value;
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    @NotNull
    public Optional<Object> getParameter() {
        return Optional.ofNullable(param);
    }
}
