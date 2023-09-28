// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Font.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import moe.kawayi.org.utopia.core.util.NotNull;

import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.harfbuzz.HarfBuzz;

/**
 * 代表一个字体Face. 这应该从属于一个{@link Library}
 */
public interface FontFace extends AutoCloseable {

    @NotNull
    FT_Face getFreetypeFace();

    /**
     * 获取harfbuzz-font
     * 一般通过{@link HarfBuzz#hb_font_create(long)}获取
     * @return 必须为非空有效的指针
     */
    long getHarfbuzzFont();

    /**
     * 获取{@link FontFace}所依赖的{@link Library}
     */
    Library getLibrary();
}
