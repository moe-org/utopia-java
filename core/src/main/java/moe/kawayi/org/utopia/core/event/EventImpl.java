// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

/**
 * 基本的事件实现。
 * <p>
 * 非线程安全。
 */
public class EventImpl implements Event {
    private boolean cancel = false;

    private final boolean cancelable;

    /**
     * 构造一个默认事件实现
     *
     * @param cancelable 是否能够取消
     */
    public EventImpl(boolean cancelable) {
        this.cancelable = cancelable;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }
}
