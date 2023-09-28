// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Library.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import org.lwjgl.PointerBuffer;
import org.lwjgl.util.freetype.FreeType;
import org.lwjgl.util.harfbuzz.HarfBuzz;

public interface Library extends AutoCloseable {

    /**
     * 获取freetype库. <br/>
     * 一般通过{@link FreeType#FT_Init_FreeType(PointerBuffer)}获取.
     * @return 有效的非空指针.
     */
    long getFreetype();

    /**
     * 获取harfbuzz-buffer. <br/>
     * 一般通过{@link HarfBuzz#hb_buffer_create()}获取
     * @return 必须为非空有效的指针
     */
    long getHarfbuzzBuffer();
}
