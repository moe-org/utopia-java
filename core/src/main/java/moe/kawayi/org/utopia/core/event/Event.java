// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Event.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

/**
 * 事件接口
 */
public interface Event {

    /**
     * 是否取消了事件
     * @return 如果取消返回true
     */
    boolean isCancelled();
}
