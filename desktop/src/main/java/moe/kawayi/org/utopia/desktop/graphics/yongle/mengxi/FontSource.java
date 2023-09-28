// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FontSource.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import java.nio.ByteBuffer;

import moe.kawayi.org.utopia.core.util.NotNull;

import org.lwjgl.util.harfbuzz.hb_destroy_func_tI;

/**
 * 代表一个字体的"源". 这可以独立于{@link Library}
 */
public interface FontSource extends AutoCloseable {

    /**
     * {@link org.lwjgl.util.harfbuzz.HarfBuzz#hb_blob_create(ByteBuffer, int, long, hb_destroy_func_tI)}
     */
    long getHarfbuzzBlob();

    /**
     * 获取字体在内存中的值
     */
    @NotNull
    ByteBuffer getBuffer();
}
