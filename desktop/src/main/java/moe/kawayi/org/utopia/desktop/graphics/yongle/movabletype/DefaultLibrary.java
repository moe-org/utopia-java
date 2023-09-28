// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Library.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.util.NotNull;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.FreeType;
import org.lwjgl.util.harfbuzz.HarfBuzz;

public class DefaultLibrary implements Library {

    private final PointerBuffer freetype;

    private final long harfbuzz;

    private DefaultLibrary(@NotNull PointerBuffer freetype, @NotNull long harfbuzz) {
        this.freetype = freetype;
        this.harfbuzz = harfbuzz;
    }

    public static DefaultLibrary create() throws FreetypeException, HarfbuzzException {
        // freetype
        var freetype = MemoryUtil.memAllocPointer(1);
        var fr = FreeType.FT_Init_FreeType(freetype);

        FreetypeException.checkError(fr);

        // harfbuzz
        var harfbuzz = HarfBuzz.hb_buffer_create();

        if (harfbuzz == 0) {
            throw new HarfbuzzException("hb_buffer_create return null");
        }

        return new DefaultLibrary(freetype, harfbuzz);
    }

    @NotNull
    public long getFreetype() {
        return this.freetype.duplicate().get();
    }

    public long getHarfbuzzBuffer() {
        return this.harfbuzz;
    }

    @Override
    public void close() {
        GlobalLogManager.GLOBAL_LOGGER.debug("destroy library");
        FreeType.FT_Done_FreeType(this.getFreetype());
        HarfBuzz.hb_buffer_destroy(this.getHarfbuzzBuffer());
        MemoryUtil.memFree(this.freetype);
    }
}
