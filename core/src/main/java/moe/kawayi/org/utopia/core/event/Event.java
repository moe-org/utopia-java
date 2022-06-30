// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Event.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * 事件接口
 */
public interface Event {

    /**
     * 是否取消了事件
     * @return 如果取消返回true
     */
    boolean isCancelled();

    /**
     * 设置是否取消事件
     * @param value 如果设置为true则取消事件
     * @exception IllegalCancellationException 对{@link Event#canCancel}返回false的事件使用true参数。
     */
    void setCancel(boolean value) throws IllegalCancellationException;

    /**
     * 检查事件是否可以被取消
     * @return 如果为true则可以被取消
     */
    boolean canCancel();

    /**
     * 获取事件携带的对象
     * @return 事件对象
     */
    @NotNull
    Optional<Object> getParameter();

    /**
     * 获取事件执行结果（当一个事件拥有多个接收者时）
     * @return 上一个事件的接收者所设置的结果
     */
    @NotNull
    Optional<Object> getResult();

    /**
     * 设置事件的执行结果
     * @param result 结果
     */
    void setResult(@Nullable Object result);
}
