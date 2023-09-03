// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Renderer.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype;

import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.desktop.graphics.yongle.ColorPoint;
import moe.kawayi.org.utopia.desktop.graphics.yongle.Rect;

/**
 * 渲染器. 和{@link Library}相关联
 */
public interface Renderer {

    void setOption(@NotNull Option option);

    /**
     * @exception NullPointerException 如果没有成功调用过{@link Renderer#setOption(Option)}(即尚未初始化),
     *      * 那么则抛出{@link NullPointerException}异常
     */
    @NotNull
    Option getOption();

    void setFontFace(@NotNull FontFace face);

    /**
     * @exception NullPointerException 如果没有成功调用过{@link Renderer#setFontFace(FontFace)}(即尚未初始化),
     *      * 那么则抛出{@link NullPointerException}异常
     */
    @NotNull
    FontFace getFontFace() throws NullPointerException;

    void render(int graphId, @NotNull Consumer<Rect> size, @NotNull Consumer<ColorPoint> writer)
            throws FreetypeException;
}
