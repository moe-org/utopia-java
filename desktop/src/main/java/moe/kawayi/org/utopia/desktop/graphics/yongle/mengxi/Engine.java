// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Engine.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.util.NotNull;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * 一个字体引擎.使用Harfbuzz和Freetype
 */
public class Engine implements AutoCloseable {

    private static final Logger LOGGER = GlobalLogManager.getLogger(Engine.class);

    private final Library library;

    private final FontSource source;

    private final FontFace face;

    private final LayoutEngine layouts = new LayoutImpl();

    private final Renderer renderer = new RendererImpl();

    private final HashMap<Integer, WeakReference<CharacterPixmap>> cache = new HashMap<>();

    public Engine(FontSource source, FontFace face) throws FreetypeException, HarfbuzzException {
        this.source = source;
        this.face = face;
        this.library = face.getLibrary();
        this.renderer.setFontFace(face);
    }

    @NotNull
    public static Engine createFromFontFile(@NotNull Path file, int faceId)
            throws HarfbuzzException, IOException, FreetypeException {
        var library = DefaultLibrary.create();

        var source = FontSourceImpl.fromFile(file);

        var face = FontFaceImpl.create(library, source, faceId);

        return new Engine(source, face);
    }

    @NotNull
    private CharacterPixmap getCache(int id) throws FreetypeException {
        var got = this.cache.get(id);
        CharacterPixmap map = null;
        if (got != null) {
            map = got.get();
        }

        if (got == null || map == null) {
            final AtomicReference<Pixmap> rendered = new AtomicReference<>(null);

            var info = this.renderer.render(
                    id,
                    (r) -> rendered.set(new Pixmap(r.getWidth(), r.getHeight(), Pixmap.Format.RGBA8888)),
                    (p, position) -> rendered.get().drawPixel(position.x, position.y, p.toRGBA8888()));

            map = new CharacterPixmap(rendered.get(), info);
            got = new WeakReference<>(map);
            this.cache.put(id, got);
        }
        return map;
    }

    @NotNull
    public Pixmap drawLine(@NotNull String string, @NotNull Option option) throws HarfbuzzException, FreetypeException {
        Objects.requireNonNull(string);
        Objects.requireNonNull(option);

        this.renderer.setOption(option);
        var layouts = this.layouts.layout(this.face, string, option);

        var glyphs = new ArrayList<CharacterPixmap>();

        int xMax = 0;
        int yUpMax = 0;
        int yDownMax = 0;
        int maxAscent = 0;
        int xPen = 0;
        int yPen = 0;

        for (var layout : layouts) {
            var id = layout.getGlyphID();

            var got = this.getCache(id);

            final int xOffset = got.info.bitmapLeft;
            final int yDown = got.map.getHeight() - got.info.bitmapTop;

            xMax = Math.max(got.map.getWidth() + layout.xOffset + xPen + xOffset, xMax);
            yUpMax = Math.max(got.map.getHeight() + layout.yOffset + yPen, yUpMax);
            yDownMax = Math.max(Math.abs(yDown), yDownMax);
            maxAscent = Math.max(got.info.bitmapTop, maxAscent);

            xPen += layout.xAdvance;
            yPen += layout.yAdvance;
            glyphs.add(got);
        }

        xPen = 0;
        yPen = 0;
        var output = new Pixmap(xMax, yUpMax + yDownMax, Pixmap.Format.RGBA8888);

        int index = 0;
        while (index != layouts.length) {
            var glyph = glyphs.get(index);
            var layout = layouts[index];

            final var xPos = xPen + layout.xOffset + glyph.info.bitmapLeft;
            final var yPos = yPen + layout.yOffset + maxAscent - glyph.info.bitmapTop;

            output.drawPixmap(glyph.map, xPos, yPos);

            xPen += layout.xAdvance;
            yPen += layout.yAdvance;
            index++;
        }

        return output;
    }

    @NotNull
    public Pixmap drawMultipleLine(@NotNull String string, @NotNull Option option)
            throws HarfbuzzException, FreetypeException {
        var lines = string.lines().toList();
        ArrayList<Pixmap> linesImage = new ArrayList<>();

        for (var line : lines) {
            var got = this.drawLine(line, option);
            linesImage.add(got);
        }

        int xMax = 0;
        int yMax = 0;
        for (var image : linesImage) {
            xMax = Math.max(image.getWidth(), xMax);
            yMax = Math.max(yMax, yMax + image.getHeight());
        }

        Pixmap output = new Pixmap(xMax, yMax, Pixmap.Format.RGBA8888);

        int yPen = 0;
        for (var image : linesImage) {
            output.drawPixmap(image, 0, yPen);
            yPen += image.getHeight();
        }

        return output;
    }

    @Override
    public void close() {
        try {
            this.cache.clear();
            this.renderer.close();
            this.layouts.close();
            this.face.close();
            this.source.close();
            this.library.close();
        } catch (Exception e) {
            LOGGER.error("failed to clase the Engine", e);
        }
    }
}
