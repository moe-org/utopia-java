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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import com.badlogic.gdx.utils.Null;
import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.util.NotNull;

import com.badlogic.gdx.graphics.Pixmap;
import moe.kawayi.org.utopia.core.util.Nullable;
import org.lwjgl.util.freetype.FreeType;

/**
 * 一个字体引擎.使用Harfbuzz和Freetype
 */
public class Engine implements AutoCloseable {

    private static final Logger LOGGER = GlobalLogManager.getLogger(Engine.class);

    private final Library library;

    private final ArrayList<LoadedFont> fonts = new ArrayList<>();

    private final LayoutEngine layouts = new LayoutImpl();

    private final Renderer renderer = new RendererImpl();

    private final HashMap<LoadedFont,HashMap<Integer, WeakReference<CharacterPixmap>>> cache = new HashMap<>();

    public Engine() throws FreetypeException, HarfbuzzException {
        this.library = DefaultLibrary.create();
    }

    @NotNull
    public void addFaceFromFile(@NotNull Path file, int faceId)
            throws HarfbuzzException, IOException, FreetypeException {

        var source = FontSourceImpl.fromFile(file);

        var face = FontFaceImpl.create(this.library, source, faceId);

        this.fonts.add(new LoadedFont(source, face));
    }

    public record LoadedFont(FontSource source, FontFace face) implements AutoCloseable {
        public LoadedFont(@NotNull FontSource source, @NotNull FontFace face) {
            this.source = Objects.requireNonNull(source);
            this.face = Objects.requireNonNull(face);
        }

        @Override
        public void close() throws Exception {
            this.face.close();
            this.source.close();
        }
    }

    @NotNull
    private CharacterPixmap getCache(@NotNull LoadedFont font,int id) throws FreetypeException {
        var gotFont = this.cache.get(font);
        var addNeed = false;

        if(gotFont == null){
            gotFont = new HashMap<>();
            addNeed = true;
        }

        var got = gotFont.get(id);
        CharacterPixmap map = null;
        if (got != null) {
            map = got.get();
        }

        if (map == null) {
            final AtomicReference<Pixmap> rendered = new AtomicReference<>(null);

            var info = this.renderer.render(
                    id,
                    (r) -> rendered.set(new Pixmap(r.getWidth(), r.getHeight(), Pixmap.Format.RGBA8888)),
                    (p, position) -> rendered.get().drawPixel(position.x, position.y, p.toRGBA8888()));

            map = new CharacterPixmap(rendered.get(), info);
            got = new WeakReference<>(map);

            gotFont.put(id,got);
            if(addNeed){
                this.cache.put(font,gotFont);
            }
        }
        return map;
    }

    private boolean checkCodePointExists(@NotNull LoadedFont font, long codePoint) {
        var index = FreeType.FT_Get_Char_Index(font.face.getFreetypeFace(), codePoint);

        return index != 0;
    }

    private void sliceString(@NotNull String input, BiConsumer<String, LoadedFont> renderer) {
        var stream = input.codePoints();

        AtomicReference<StringBuilder> builder = new AtomicReference<>(new StringBuilder());
        final LoadedFont[] current = {null};

        stream.forEach((int code) -> {
            // prefer current
            if(current[0] != null && checkCodePointExists(current[0],code)){
                builder.get().appendCodePoint(code);
                return;
            }

            // find one that contain the code point
            for (var font : this.fonts) {
                if (checkCodePointExists(font, code)) {
                    if (current[0] == null) {
                        current[0] = font;
                        builder.get().appendCodePoint(code);
                    } else if (current[0] != font) {
                        // switch to new font
                        renderer.accept(builder.toString(), current[0]);
                        current[0] = font;
                        builder.set(new StringBuilder());
                        builder.get().appendCodePoint(code);
                    } else {
                        builder.get().appendCodePoint(code);
                    }
                    break;
                }
            }
        });

        // proocess last
        var last = builder.get().toString();
        if(!last.isEmpty()){
            renderer.accept(last,current[0]);
        }
    }

    @NotNull
    private Pixmap renderLine(@NotNull String string, @Nullable String preString, @Nullable String postString,
                              @NotNull Option option, @NotNull LoadedFont font)
            throws HarfbuzzException, FreetypeException {
        Objects.requireNonNull(string);
        Objects.requireNonNull(option);
        Objects.requireNonNull(font);

        this.renderer.setOption(option);
        this.renderer.setFontFace(font.face);
        var layouts = this.layouts.layout(font.face, string,preString,postString, option);

        var glyphs = new ArrayList<CharacterPixmap>();

        int xMax = 0;
        int yUpMax = 0;
        int yDownMax = 0;
        int maxAscent = 0;
        int xPen = 0;
        int yPen = 0;

        for (var layout : layouts) {
            var id = layout.getGlyphID();

            var got = this.getCache(font,id);

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
    private Pixmap mergeXPixmaps(@NotNull List<Pixmap> images) {
        int xMax = 0;
        int yMax = 0;
        for (var image : images) {
            xMax = Math.max(xMax,image.getWidth() + xMax);
            yMax = Math.max(yMax,image.getHeight());
        }

        Pixmap output = new Pixmap(xMax, yMax, Pixmap.Format.RGBA8888);

        int xPen = 0;
        for (var image : images) {
            output.drawPixmap(image, xPen, 0);
            xPen += image.getWidth();
        }

        return output;
    }

     @NotNull
    private Pixmap mergeYPixmaps(@NotNull List<Pixmap> images) {
        int xMax = 0;
        int yMax = 0;
        for (var image : images) {
            xMax = Math.max(image.getWidth(), xMax);
            yMax = Math.max(yMax, yMax + image.getHeight());
        }

        Pixmap output = new Pixmap(xMax, yMax, Pixmap.Format.RGBA8888);

        int yPen = 0;
        for (var image : images) {
            output.drawPixmap(image, 0, yPen);
            yPen += image.getHeight();
        }

        return output;
    }

    @NotNull
    public Pixmap drawLine(@NotNull String string, @NotNull Option option) throws HarfbuzzException, FreetypeException {
        Objects.requireNonNull(string);
        Objects.requireNonNull(option);

        ArrayList<String> texts = new ArrayList<>();
        ArrayList<LoadedFont> fonts = new ArrayList<>();

        this.sliceString(string, (str, loaded) -> {
            texts.add(str);
            fonts.add(loaded);
        });

        ArrayList<Pixmap> pixmap = new ArrayList<>();

        for (int i = 0; i != texts.size(); i++) {
            var str = texts.get(i);
            var font = fonts.get(i);

            String pre = null;
            String post = null;

            if((i-1) >= 0){
                pre = texts.get(i-1);
            }
            if((i-2) >= 0){
                pre = pre != null ? pre + texts.get(i-2) : texts.get(i-2);
            }
            if((i+1) < texts.size()){
                post = texts.get(i+1);
            }
            if((i+2) < texts.size()){
                post = post != null ? post + texts.get(i+2) : texts.get(i+2);
            }

            pixmap.add(renderLine(str, pre,post,option, font));
        }

        return this.mergeXPixmaps(pixmap);
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

        return mergeYPixmaps(linesImage);
    }

    @Override
    public void close() {
        try {
            this.cache.clear();
            this.renderer.close();
            this.layouts.close();
            for (var font : this.fonts) {
                font.close();
            }
            this.library.close();
        } catch (Exception e) {
            LOGGER.error("failed to clase the Engine", e);
        }
    }
}
