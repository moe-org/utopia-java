// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * 基本的事件实现。
 * <p>
 * 非线程安全。
 */
public class EventImpl<Param> implements Event {

    private final Param param;
    private boolean cancel = false;
    private Object result = null;
    private final boolean cancelable;

    /**
     * 构造一个默认事件实现
     *
     * @param param      事件参数
     * @param cancelable 是否能够取消
     */
    public EventImpl(@Nullable Param param, boolean cancelable) {
        this.param = param;
        this.cancelable = cancelable;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancel(boolean value) throws IllegalCancellationException {
        if ((!cancelable) && value) {
            throw new IllegalCancellationException();
        }
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

    @Override
    @NotNull
    public Optional<Object> getResult() {
        return Optional.ofNullable(result);
    }

    @Override
    public void setResult(@Nullable Object result) {
        this.result = result;
    }
}
