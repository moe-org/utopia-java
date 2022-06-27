//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceManager.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.resource;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 资源管理器
 */
public final class ResourceManager {

    /**
     * private
     */
    private ResourceManager(){}

    /**
     * utopia的根目录
     */
    private static final AtomicReference<Path> UTOPIA_DIR = new AtomicReference<>();

    /**
     * 默认utopia根路径的系统PROPERTY。
     * <p>
     * 即默认根目录将会是
     * <br/>
     * {@link System#getProperty(String)} with argument {@link ResourceManager#DEFAULT_UTOPIA_DIR_PROPERTY}的调用结果
     */
    public static final String DEFAULT_UTOPIA_DIR_PROPERTY = "user.dir";

    /**
     * 设置utopia根目录
     * <br/>
     * 不应该在除了utopia启动的时候设置这个值(即从有人调用任何getPath函数族之前)。
     * <br/>
     * 否则会导致现有的，已经加载的plugin或者其他代码无法找到资源。并且有更多未知造成的影响。
     *
     * @param utopiaRoot 根目录。非空。
     */
    public static void setUtopiaDir(@NotNull Path utopiaRoot) {
        Objects.requireNonNull(utopiaRoot);

        UTOPIA_DIR.set(utopiaRoot);
    }

    /**
     * 获取utopia根目录。有时称为utopia-root
     *
     * @return utopia的根目录。保证不为空。
     */
    @NotNull
    public static Path getUtopiaDir() {
        var got = UTOPIA_DIR.get();
        if (got == null) {
            var defaultValue = Path.of(System.getProperty("user.dir"));
            UTOPIA_DIR.compareAndSet(null, defaultValue);

            return defaultValue;
        } else {
            return got;
        }
    }

    /**
     * 根据路径获取基于utopia-root的路径
     * <br/>
     * 等价于: getUtopiaDir().resolve(other);
     *
     * @param relative 应该为基于utopia-root的相对路径。
     * @return 获取到的路径
     * @see ResourceManager#getPath(String)
     */
    @NotNull
    public static Path getPath(@NotNull Path relative) {
        Objects.requireNonNull(relative);
        return getUtopiaDir().resolve(relative);
    }

    /**
     * 根据路径获取基于utopia-root的路径
     * <br/>
     * 等价于: getUtopiaDir().resolve(other);
     *
     * @param relative 应该为基于utopia-root的相对路径。
     * @return 获取到的路径
     * @see ResourceManager#getPath(Path)
     */
    @NotNull
    public static Path getPath(@NotNull String relative) {
        Objects.requireNonNull(relative);
        return getUtopiaDir().resolve(relative);
    }

    /**
     * 获取基于utopia-root路径的相对路径
     * <br/>
     * 等价于: getUtopiaDir().relativize(other);
     *
     * @param absolute 绝对路径
     * @return 相对于utopia-root的相对路径。
     */
    @NotNull
    public static Path relativize(@NotNull Path absolute) {
        Objects.requireNonNull(absolute);
        return getUtopiaDir().relativize(absolute);
    }


}
