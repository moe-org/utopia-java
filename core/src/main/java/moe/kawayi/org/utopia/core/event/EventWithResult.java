// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventWithResult.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

public interface EventWithResult<T> extends Event {
    /**
     * 获取事件执行结果（当一个事件拥有多个接收者时）
     * @return 上一个事件的接收者所设置的结果,如果为null或者没有设置结果,返回{@link Optional#empty()}
     */
    @NotNull
    Optional<T> getResult();

    /**
     * 设置事件的执行结果
     * @param result 结果
     */
    void setResult(@Nullable T result);
}
