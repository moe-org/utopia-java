//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Event.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Optional;

/**
 * 事件接口
 */
public interface Event {

    /**
     * 是否取消了事件
     * @return 如果取消返回true
     */
    boolean isCancel();

    /**
     * 设置是否取消事件
     * @param value 如果设置为true则取消事件
     */
    void setCancel(boolean value);

    /**
     * 检查事件是否可以被取消
     * @return 如果为true则可以被取消
     */
    boolean canCancel();

    /**
     * 获取事件携带的对象
     * @return 事件对象
     */
    Optional<Object> getParameter();
}
