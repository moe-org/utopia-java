//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FileTool.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.util;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 文件工具
 */
public final class FileTool {
    private FileTool() {
    }


    /**
     * 连接路径
     *
     * @param paths 路径
     * @return 返回使用File.separator连接的路径
     */
    public static Path getPath(@NotNull String... paths) {
        Objects.requireNonNull(paths, "paths must not be null");

        Path path = Path.of(".");

        return path.resolve(String.join(File.pathSeparator, paths));
    }


}
