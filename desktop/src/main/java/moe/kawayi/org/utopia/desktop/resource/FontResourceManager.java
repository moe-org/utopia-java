//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FontResourceManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.resource;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.resource.ResourceManager;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

/**
 * 字体资源管理器
 */
public class FontResourceManager {

    /**
     * 字体文件夹。基于{@link Resource#RESOURCE_DIR}.
     */
    public static final String FONT_DIR = Resource.RESOURCE_DIR + "/fonts";

    private static final Logger LOGGER = LogManagers.getLogger(FontResourceManager.class);

    /**
     * 获取字体文件
     * @return 获取到的字体文件
     */
    @Nullable
    private static FileHandle getFontFiles() throws java.io.IOException{
        File fontDir = ResourceManager.getPath(FONT_DIR).toFile();

        if (!fontDir.exists()) {
            LOGGER.warn("未找到字体资源文件夹:{}", fontDir.toString());
            // create
            Files.createDirectory(fontDir.toPath());
            return null;
        }

        var fonts = Arrays.stream(
                        Objects.requireNonNull(fontDir.listFiles((dir, name) -> {
                            if (name.toLowerCase().endsWith(".ttf")) {
                                return true;
                            } else {
                                LOGGER.warn("在字体文件夹下找到不以 '.ttf' 结尾:{} 不加载", name);
                                return false;
                            }
                        })))
                .map(FileHandle::new).toList();

        LOGGER.warn("在字体文件夹下找到多个字体");

        var usedFont = fonts.stream().findFirst().orElse(null);

        if (usedFont != null) {
            LOGGER.warn("使用字体 {}", usedFont.file().toString());
        } else {
            LOGGER.warn("未找到字体");
        }
        return usedFont;
    }

    /**
     * 加载字体
     * @param parameter 字体参数。如果为null则使用{@link FreeTypeFontGenerator.FreeTypeFontParameter}默认值。
     * @return 加载到的字体。保证不为null。如果没有找到assets字体，则使用libgdx默认字体（即new {@link BitmapFont})
     */
    @NotNull
    public static BitmapFont loadFont(@Nullable FreeTypeFontGenerator.FreeTypeFontParameter parameter){
        try {
            var fontFile = getFontFiles();

            if (fontFile == null) {
                LOGGER.warn("未在字体资源文件夹下找到字体");
                return new BitmapFont();
            }

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

            if (parameter == null)
                parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

            return generator.generateFont(parameter);
        }
        catch(@NotNull java.io.IOException ex){
            LOGGER.error("load font failed. use default font",ex);
            return new BitmapFont();
        }
    }

}
