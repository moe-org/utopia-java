// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DefaultRenderer.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype;

import java.lang.ref.Cleaner;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.util.CleanerManager;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.desktop.graphics.yongle.ColorPoint;
import moe.kawayi.org.utopia.desktop.graphics.yongle.Rect;

import org.lwjgl.util.freetype.FT_Bitmap;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FreeType;

public class RendererImpl implements Renderer {

    private final AtomicReference<Cleaner.Cleanable> cleanable = new AtomicReference<>();

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private Option option = null;

    private FontFace fontFace = null;

    private FT_Face face = null;

    @Override
    public void setOption(Option option) {
        this.initialized.set(false);
        this.option = Objects.requireNonNull(option);
    }

    @Override
    public Option getOption() {
        return Objects.requireNonNull(this.option);
    }

    @Override
    public void setFontFace(FontFace face) {
        this.initialized.set(false);
        this.fontFace = Objects.requireNonNull(face);
    }

    @Override
    public FontFace getFontFace() throws NullPointerException {
        return Objects.requireNonNull(this.fontFace);
    }

    public void initialize() throws FreetypeException {
        this.face = FT_Face.create(this.getFontFace().getFreetypeFace().get());

        var address = this.face.address();
        var clean = this.cleanable.getAndSet(
                CleanerManager.getCleaner().register(this.face, () -> FreeType.nFT_Done_Face(address)));

        if (clean != null) {
            clean.clean();
        }

        var fe = FreeType.FT_Set_Pixel_Sizes(this.face, option.getFontWidthPixel(), option.getFontHeightPixel());

        FreetypeException.checkError(fe);
        this.initialized.set(true);
    }

    private void loadAndRender(int graphId) throws FreetypeException {
        var fe = FreeType.FT_Set_Pixel_Sizes(
                this.face, this.option.getFontWidthPixel(), this.option.getFontHeightPixel());

        FreetypeException.checkError(fe);

        fe = FreeType.FT_Load_Glyph(this.face, graphId, FreeType.FT_LOAD_COLOR);

        FreetypeException.checkError(fe);

        fe = FreeType.FT_Render_Glyph(Objects.requireNonNull(this.face.glyph()), FreeType.FT_RENDER_MODE_NORMAL);

        FreetypeException.checkError(fe);
    }

    /**
     * 256位抗锯齿图
     */
    private void writeGray(FT_Bitmap bitmap, @NotNull BiConsumer<ColorPoint, FlatPosition> writer) {
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
    private void writeColor(FT_Bitmap bitmap, @NotNull BiConsumer<ColorPoint, FlatPosition> writter) {
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

        if (!this.initialized.get()) {
            this.initialize();
        }

        final var info = new CharacterInfo();

        loadAndRender(graphId);

        var bitmap = Objects.requireNonNull(this.face.glyph()).bitmap();

        info.bitmapTop = Objects.requireNonNull(this.face.glyph()).bitmap_top();
        info.bitmapLeft = Objects.requireNonNull(this.face.glyph()).bitmap_left();

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
}
