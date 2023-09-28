// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DefaultRenderer.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.desktop.graphics.yongle.ColorPoint;
import moe.kawayi.org.utopia.desktop.graphics.yongle.Rect;

import org.lwjgl.util.freetype.FT_Bitmap;
import org.lwjgl.util.freetype.FreeType;

public class RendererImpl implements Renderer {

    private Option option = null;

    private FontFace fontFace = null;

    @Override
    public void setOption(@NotNull Option option) {
        this.option = Objects.requireNonNull(option);
    }

    @Override
    @NotNull
    public Option getOption() {
        return Objects.requireNonNull(this.option);
    }

    @Override
    public void setFontFace(@NotNull FontFace face) {
        this.fontFace = Objects.requireNonNull(face);
    }

    @Override
    @NotNull
    public FontFace getFontFace() throws NullPointerException {
        return Objects.requireNonNull(this.fontFace);
    }

    private void loadAndRender(int graphId) throws FreetypeException {
        int fe;
        fe = FreeType.FT_Set_Pixel_Sizes(
                this.fontFace.getFreetypeFace(), this.option.getFontWidthPixel(), this.option.getFontHeightPixel());

        FreetypeException.checkError(fe);

        fe = FreeType.FT_Load_Glyph(this.fontFace.getFreetypeFace(), graphId, FreeType.FT_LOAD_COLOR);

        FreetypeException.checkError(fe);

        fe = FreeType.FT_Render_Glyph(
                Objects.requireNonNull(this.fontFace.getFreetypeFace().glyph()), FreeType.FT_RENDER_MODE_NORMAL);

        FreetypeException.checkError(fe);
    }

    /**
     * 256位抗锯齿图
     */
    private void writeGray(@NotNull FT_Bitmap bitmap, @NotNull BiConsumer<ColorPoint, FlatPosition> writer) {
        final int y = bitmap.rows();
        final int x = bitmap.width();
        final var buf = bitmap.buffer(y * Math.abs(bitmap.pitch()));

        if (bitmap.pitch() < 0) {
            throw new IllegalStateException("no supported negative bitmap.pitch");
        }

        int pointer = 0;

        for (int yIndex = 0; yIndex < y; yIndex++) {
            for (int xIndex = 0; xIndex < x; xIndex++) {
                byte value = buf.get(pointer + xIndex);

                writer.accept(new ColorPoint((byte) 0, (byte) 0, (byte) 0, value), new FlatPosition(xIndex, yIndex));
            }
            pointer += bitmap.pitch();
        }
    }

    /**
     *
     */
    private void writeColor(@NotNull FT_Bitmap bitmap, @NotNull BiConsumer<ColorPoint, FlatPosition> writter) {
        final int y = bitmap.rows();
        final int x = bitmap.width();
        final var buf = bitmap.buffer(x * Math.abs(bitmap.pitch()));

        if (bitmap.pitch() < 0) {
            throw new IllegalStateException("no supported negative bitmap.pitch");
        }

        int pointer = 0;

        for (int yIndex = 0; yIndex < y; yIndex++) {
            for (int xIndex = 0; xIndex < x; xIndex++) {
                byte r = buf.get(pointer + xIndex * 4);
                byte g = buf.get(pointer + xIndex * 4 + 1);
                byte b = buf.get(pointer + xIndex * 4 + 2);
                byte a = buf.get(pointer + xIndex * 4 + 3);

                writter.accept(new ColorPoint(r, g, b, a), new FlatPosition(xIndex, yIndex));
            }
            pointer += bitmap.pitch();
        }
    }

    @Override
    @NotNull
    public CharacterInfo render(
            int graphId, @NotNull Consumer<Rect> size, @NotNull BiConsumer<ColorPoint, FlatPosition> writer)
            throws FreetypeException {

        final var info = new CharacterInfo();

        loadAndRender(graphId);

        var bitmap =
                Objects.requireNonNull(this.fontFace.getFreetypeFace().glyph()).bitmap();

        info.bitmapTop =
                Objects.requireNonNull(this.fontFace.getFreetypeFace().glyph()).bitmap_top();
        info.bitmapLeft =
                Objects.requireNonNull(this.fontFace.getFreetypeFace().glyph()).bitmap_left();

        size.accept(new Rect(bitmap.width(), bitmap.rows()));

        if (bitmap.pixel_mode() == FreeType.FT_PIXEL_MODE_BGRA) {
            writeColor(bitmap, writer);
        } else if (bitmap.pixel_mode() == FreeType.FT_PIXEL_MODE_GRAY) {
            writeGray(bitmap, writer);
        } else {
            throw new IllegalStateException("no support pixel mode");
        }

        return info;
    }

    @Override
    public void close() {}
}
