// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LayoutEngine.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * 排版引擎.
 */
public interface LayoutEngine extends AutoCloseable {

    @NotNull
    LayoutInfo[] layout(@NotNull FontFace face, @NotNull String string, @Nullable String preString,@Nullable String postString, @NotNull Option option)
            throws HarfbuzzException;
}
