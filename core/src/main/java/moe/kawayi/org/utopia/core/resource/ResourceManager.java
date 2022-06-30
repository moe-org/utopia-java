// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceManager.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.resource;

import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 资源管理器
 */
public final class ResourceManager {

    /**
     * private
     */
    private ResourceManager() {}

    /**
     * 默认utopia根路径的系统PROPERTY。
     * <p>
     * 即默认根目录将会是
     * <br/>
     * {@link System#getProperty(String)} with argument {@link ResourceManager#DEFAULT_UTOPIA_DIR_PROPERTY}的调用结果
     */
    public static final String DEFAULT_UTOPIA_DIR_PROPERTY = "user.dir";

    /**
     * utopia的根目录
     */
    private static final AtomicReference<Path> UTOPIA_DIR =
            new AtomicReference<>(Path.of(System.getProperty(DEFAULT_UTOPIA_DIR_PROPERTY)));

    /**
     * 获取utopia根目录。有时称为utopia-root
     *
     * @return utopia的根目录。保证不为空。
     */
    @NotNull
    public static Path getUtopiaDir() {
        return UTOPIA_DIR.get();
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
