// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventWithParameter.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;

public interface EventWithParameter<T> extends Event {
    /**
     * 获取事件携带的对象.如果参数为空,则返回{@link Optional#empty()}
     * @return 事件对象
     */
    @NotNull
    Optional<T> getParameter();
}
