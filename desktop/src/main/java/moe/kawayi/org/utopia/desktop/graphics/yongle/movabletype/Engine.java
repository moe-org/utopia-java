// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Engine.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.util.NotNull;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * 一个字体引擎.使用Harfbuzz和Freetype
 */
public class Engine {

    private final Library library = DefaultLibrary.create();

    private FontSource source;

    private FontFace face;

    private final LayoutEngine layouts = new LayoutImpl();

    private final Renderer renderer = new RendererImpl();

    private final HashMap<Integer, Pixmap> cache = new HashMap<Integer, Pixmap>();

    public Engine() throws FreetypeException, HarfbuzzException {}

    public void loadFontFromFile(@NotNull Path file, int faceId)
            throws HarfbuzzException, IOException, FreetypeException {

        this.source = FontSourceImpl.fromFile(file);

        this.face = FontFaceImpl.create(this.library, this.source, faceId);
        this.renderer.setFontFace(this.face);
    }

    public Pixmap drawLine(@NotNull String string, @NotNull Option option) throws HarfbuzzException, FreetypeException {
        Objects.requireNonNull(string);
        Objects.requireNonNull(option);

        this.renderer.setOption(option);
        var layouts = this.layouts.layout(this.face, string, option);

        GlobalLogManager.GLOBAL_LOGGER.debug("require {} characters", string.codePointCount(0, string.length()));
        GlobalLogManager.GLOBAL_LOGGER.debug("layout {} glyphs", layouts.length);

        var glyphs = new ArrayList<Pixmap>();

        int xMax = 0;
        int yMax = 0;
        int xPen = 0;
        int yPen = 0;

        for (var layout : layouts) {
            var id = layout.getGlyphID();

            var got = this.cache.get(id);
            if (got == null) {
                final AtomicReference<Pixmap> rendered = new AtomicReference<>(null);
                AtomicInteger x = new AtomicInteger(0);
                AtomicInteger y = new AtomicInteger(0);

                this.renderer.render(
                        id,
                        (r) -> {
                            rendered.set(new Pixmap(r.getWidth(), r.getHeight(), Pixmap.Format.RGBA8888));
                        },
                        (p) -> {
                            rendered.get().drawPixel(x.get(), y.get(), p.toRGBA8888());
                            x.getAndIncrement();

                            if (x.get() > rendered.get().getWidth()) {
                                y.incrementAndGet();
                                x.set(0);
                            }
                        });

                got = rendered.get();
                this.cache.put(id, got);
            }

            xMax = Math.max(got.getWidth() + layout.xOffset + xPen, xMax);
            yMax = Math.max(got.getHeight() + layout.yOffset + yPen, yMax);

            xPen += layout.xAdvance;
            yPen += layout.yAdvance;
            glyphs.add(got);
        }

        xPen = 0;
        yPen = 0;
        var output = new Pixmap(xMax, yMax, Pixmap.Format.RGBA8888);

        int index = 0;
        while (index != layouts.length) {
            var glyph = glyphs.get(index);
            var layout = layouts[index];
            output.drawPixmap(glyph, xPen + layout.xOffset, yPen + layout.yOffset);

            xPen += layout.xAdvance;
            yPen += layout.yAdvance;
            index++;
        }

        return output;
    }
}
