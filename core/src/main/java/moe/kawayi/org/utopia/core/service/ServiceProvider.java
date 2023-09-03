// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ServiceProvider.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.service;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;

public interface ServiceProvider {
    /**
     * 获取服务
     * @param cls 服务
     * @return 获取到的服务
     */
    @NotNull
    Optional<Object> getService(@NotNull Class<?> cls);

    /**
     * 注册服务.如果服务已经存在,那么则替换
     * @param cls 服务类型
     * @param service 服务实体
     * @return 如果原来已经注册过此服务,那么则返回原来注册过的服务实体.
     */
    Optional<Object> register(@NotNull Class<?> cls, @NotNull Object service);

    /**
     * 删除服务
     * @param cls 要删除的服务的类型
     */
    void remove(@NotNull Class<?> cls);
}
