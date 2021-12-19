//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The GdxResourceManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import moe.kawayi.org.utopia.core.resource.ResourceManager;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.nio.file.Path;
import java.util.Objects;

/**
 * 对于GDX的{@link moe.kawayi.org.utopia.core.resource.ResourceManager}包装。
 *
 * 输入{@link FileHandle}
 */
public class GdxResourceManager {

    /**
     * 根据路径获取基于utopia-root的路径
     * <br/>
     * 等价于: Gdx.files.absolute({@link ResourceManager#getPath(Path)});
     * @param relative 应该为基于utopia-root的相对路径。
     * @see ResourceManager#getPath(Path)
     * @return 获取到的路径
     */
    @NotNull
    public static FileHandle getPath(@NotNull Path relative){
        Objects.requireNonNull(relative);
        return Gdx.files.absolute(
                ResourceManager.getPath(relative).toString()
        );
    }

    /**
     * 根据路径获取基于utopia-root的路径
     * <br/>
     * 等价于: Gdx.files.absolute({@link ResourceManager#getPath(String)});
     * @param relative 应该为基于utopia-root的相对路径。
     * @see ResourceManager#getPath(String)
     * @return 获取到的路径
     */
    @NotNull
    public static FileHandle getPath(@NotNull String relative){
        Objects.requireNonNull(relative);
        return Gdx.files.absolute(
                ResourceManager.getPath(relative).toString()
        );
    }

}
