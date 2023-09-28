// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LayoutImpl.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import java.util.ArrayList;
import java.util.Objects;

import moe.kawayi.org.utopia.core.util.NotNull;

import moe.kawayi.org.utopia.core.util.Nullable;
import org.lwjgl.util.harfbuzz.HarfBuzz;

public class LayoutImpl implements LayoutEngine {

    @Override
    @NotNull
    public LayoutInfo[] layout(@NotNull FontFace face, @NotNull String text,
                               @Nullable String preString, @Nullable String postString, @NotNull Option option)
            throws HarfbuzzException {
        Objects.requireNonNull(face);
        Objects.requireNonNull(text);
        Objects.requireNonNull(option);

        var buffer = face.getLibrary().getHarfbuzzBuffer();

        // set up
        HarfBuzz.hb_buffer_reset(buffer);

        var finalString = (preString == null ? "" : preString) + text + (postString == null ? "" : postString);
        var offset = preString == null ? 0 : preString.length();

        HarfBuzz.hb_buffer_add_utf16(buffer, finalString, offset, text.length());

        var script = HarfBuzz.hb_script_from_string(option.getScript());
        var language = HarfBuzz.hb_language_from_string(option.getLanguage());
        var rtl = option.isRTL() ? HarfBuzz.HB_DIRECTION_RTL : HarfBuzz.HB_DIRECTION_LTR;

        HarfBuzz.hb_buffer_set_direction(face.getLibrary().getHarfbuzzBuffer(), rtl);
        HarfBuzz.hb_buffer_set_script(face.getLibrary().getHarfbuzzBuffer(), script);
        HarfBuzz.hb_buffer_set_language(face.getLibrary().getHarfbuzzBuffer(), language);

        // see:
        // https://harfbuzz.github.io/harfbuzz-hb-font.html#hb-font-set-scale
        HarfBuzz.hb_font_set_scale(face.getHarfbuzzFont(), option.getFontWidthPixel(), option.getFontHeightPixel());

        // begin layout text
        HarfBuzz.hb_shape(face.getHarfbuzzFont(), face.getLibrary().getHarfbuzzBuffer(), null);

        ArrayList<LayoutInfo> outputs = new ArrayList<>();

        // read result
        try (var infos = HarfBuzz.hb_buffer_get_glyph_infos(face.getLibrary().getHarfbuzzBuffer())) {
            try (var poses =
                    HarfBuzz.hb_buffer_get_glyph_positions(face.getLibrary().getHarfbuzzBuffer())) {

                if (infos == null || poses == null || infos.sizeof() != poses.sizeof()) {
                    throw new HarfbuzzException(
                            "hb_buffer_get_glyph_infos or hb_buffer_get_glyph_positions return null or length is not the same");
                }

                final var size = infos.sizeof();

                while (infos.position() != infos.capacity()) {
                    var info = infos.get();
                    var pos = poses.get();

                    var layout = new LayoutInfo();

                    layout.setGlyphID(info.codepoint());
                    layout.setxAdvance(pos.x_advance());
                    layout.setyAdvance(pos.y_advance());
                    layout.setxOffset(pos.x_offset());
                    layout.setyOffset(pos.y_offset());

                    outputs.add(layout);
                }
            }
        }

        LayoutInfo[] infos = new LayoutInfo[outputs.size()];
        outputs.toArray(infos);
        return infos;
    }

    @Override
    public void close() {}
}
