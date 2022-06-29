//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceUtil.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.resource;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * 管理游戏资源的工具
 */
public final class ResourceUtil {
    /**
     * private
     */
    private ResourceUtil() {
    }

    /**
     * 创建文件及其父目录
     *
     * @param path 文件的路径（并非目录路径）
     * @throws java.io.IOException IO异常
     */
    public static void touchFile(@NotNull Path path) throws java.io.IOException {
        Objects.requireNonNull(path);

        Files.createDirectories(path.getParent());
        Files.createFile(path);
    }

}
