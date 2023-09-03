// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Cancellable.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

/**
 * 实现了这个接口的事件才能被取消
 */
public interface Cancellable {
    /**
     * 设置是否取消事件.只有实现了{@link Cancellable}的事件才能被取消.
     * 可以多次调用.{@link Event#isCancelled()}以最后一次调用为准
     *
     * @param value 如果设置为true则取消事件
     */
    void setCancel(boolean value);
}
