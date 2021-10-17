//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.resource;

import moe.kawayi.org.utopia.core.util.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源管理器
 */
public final class ResourceManager {

    private static final Logger LOGGER = LogManager.getLogger(ResourceManager.class);

    /**
     * 资源加载器
     */
    private static final ConcurrentHashMap<String,ResourceLoader>
            RESOURCE_LOADERS = new ConcurrentHashMap<>();

    /**
     * 系统资源加载器
     */
    private static final ResourceLoaderBase SYSTEM_LOADER = new ResourceLoaderBase();

    /**
     * 获取系统资源加载器
     * @return 系统资源加载器
     */
    @NotNull
    public static ResourceLoader getSystemResourceLoader(){
        return SYSTEM_LOADER;
    }

    /**
     * 注册资源加载器
     * @param protocol  访问资源加载器的协议名称
     * @param loader    资源加载器
     * @return  如果主机名称已经被占用，则注册失败，返回false。返回true则注册成功。
     */
    public static boolean register(@NotNull String protocol,@NotNull ResourceLoader loader){
        Objects.requireNonNull(protocol);
        Objects.requireNonNull(loader);

        var result = RESOURCE_LOADERS.putIfAbsent(protocol,loader);

        // 之前从未注册过，putIfAbsent返回null
        var isRegister = result == null;

        if(isRegister){
            LOGGER.debug("register {} hostname to resource loader {}",protocol,loader.getName());
        }

        return isRegister;
    }

    /**
     * 更新资源加载器
     * @param protocol  访问资源加载器的协议名称
     * @param loader    资源加载器
     */
    public  static void update(@NotNull String protocol,@NotNull ResourceLoader loader){
        Objects.requireNonNull(protocol);
        Objects.requireNonNull(loader);

        LOGGER.debug("update {} hostname for resource loader {}",protocol,loader.getName());

        RESOURCE_LOADERS.put(protocol,loader);
    }

    /**
     * 寻找资源加载器
     * @param protocol  访问资源加载器的协议名称
     * @return          资源加载器。未注册返回empty
     */
    public static Optional<ResourceLoader> findLoader(@NotNull String protocol){
        Objects.requireNonNull(protocol);

        return Optional.ofNullable(RESOURCE_LOADERS.get(protocol));
    }

    /**
     * 注销资源加载器
     * @param protocol  访问资源加载器的协议名称
     */
    public static void unregister(@NotNull String protocol){
        Objects.requireNonNull(protocol);

        var v = RESOURCE_LOADERS.remove(protocol);

        if(v != null){
            LOGGER.debug("unregister {} hostname to resource loader {}",protocol,v.getName());
        }
    }

    /**
     * 获取资源
     *
     * 值得注意的是：将会优先将URL传递给
     * {@link ResourceManager#getSystemResourceLoader#getResource(URL)}，
     * 加载失败且{@link ResourceManager#getSystemResourceLoader#getResource(URL)}不抛出异常时才寻找其他资源加载器。
     *
     * @param url 资源的URL
     * @return 获取到的资源。如果没有对应加载器或者获取资源失败(包括获取时抛出异常)则返回empty
     */
    public static Optional<Object> getResource(@NotNull URL url){
        try {
            var baseLoadResult = getSystemResourceLoader().getResource(url);

            if(baseLoadResult.isEmpty()){
                var loader = RESOURCE_LOADERS.get(url.getProtocol());

                if(loader != null){
                    return loader.getResource(url);
                }
                else{
                    return Optional.empty();
                }
            }
            else{
                return baseLoadResult;
            }
        }
        catch(Exception ex){
            LOGGER.warn("load resource '{}' failed down and throw exception",url,ex);
            // 返回空
            return Optional.empty();
        }
    }

}
