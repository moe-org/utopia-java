//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceLoader.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.resource;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.net.URI;
import java.net.URL;
import java.util.Optional;

/**
 * 资源加载器
 */
public interface ResourceLoader {

    /**
     * 获取资源加载器的名称
     * @return 资源加载器名称。不可为null。
     */
    @NotNull
    String getName();

    /**
     * 获取资源
     * @param url 资源位置
     * @return 获取到的资源。如果获取失败可返回空。
     * @exception Exception 如果抛出异常，则视为资源加载失败。如果是在{@link ResourceManager#getResource(URL)}中加载的资源，将会返回empty。
     */
    Optional<Object> getResource(@NotNull URL url) throws Exception;
}
