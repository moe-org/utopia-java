// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FontFaceImpl.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import java.util.Objects;

import moe.kawayi.org.utopia.core.util.NotNull;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FreeType;
import org.lwjgl.util.harfbuzz.HarfBuzz;

public class FontFaceImpl implements FontFace {

    private final FT_Face freetypeFace;

    private final PointerBuffer freetypeFaceBuffer;

    private final long harfbuzzFace;

    private final long harfbuzzFont;

    private final Library library;

    private FontFaceImpl(
            @NotNull PointerBuffer freetypeFace, long harfbuzzFace, long harfbuzzFont, @NotNull Library library) {
        this.freetypeFaceBuffer = Objects.requireNonNull(freetypeFace);
        this.library = Objects.requireNonNull(library);
        this.freetypeFace = FT_Face.create(this.freetypeFaceBuffer.duplicate().get());
        this.harfbuzzFace = harfbuzzFace;
        this.harfbuzzFont = harfbuzzFont;
    }

    @NotNull
    public static FontFace create(@NotNull Library library, @NotNull FontSource source, int faceIndex)
            throws FreetypeException, HarfbuzzException {
        Objects.requireNonNull(library);
        Objects.requireNonNull(source);

        // freetype
        var face = MemoryUtil.memAllocPointer(1);

        var fe = FreeType.FT_New_Memory_Face(library.getFreetype(), source.getBuffer(), faceIndex, face);
        FreetypeException.checkError(fe);

        // harfbuzz
        var hbFace = HarfBuzz.hb_face_create(source.getHarfbuzzBlob(), faceIndex);

        if (hbFace == 0) {
            throw new HarfbuzzException("hb_face_create return null");
        }

        var hbFont = HarfBuzz.hb_font_create(hbFace);

        if (hbFont == 0) {
            throw new HarfbuzzException("hb_font_create return null");
        }

        return new FontFaceImpl(face, hbFace, hbFont, library);
    }

    @Override
    @NotNull
    public FT_Face getFreetypeFace() {
        return this.freetypeFace;
    }

    @Override
    public long getHarfbuzzFont() {
        return this.harfbuzzFont;
    }

    @Override
    @NotNull
    public Library getLibrary() {
        return this.library;
    }

    @Override
    public void close() {
        FreeType.FT_Done_Face(this.freetypeFace);
        HarfBuzz.hb_font_destroy(this.harfbuzzFont);
        HarfBuzz.hb_face_destroy(this.harfbuzzFace);
        MemoryUtil.memFree(this.freetypeFaceBuffer);
    }
}
