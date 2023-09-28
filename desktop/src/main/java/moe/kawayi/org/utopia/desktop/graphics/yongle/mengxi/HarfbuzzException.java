// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The HarfbuzzException.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import java.util.Objects;

import moe.kawayi.org.utopia.core.util.NotNull;

public class HarfbuzzException extends Exception {

    public HarfbuzzException(@NotNull String msg) {
        super(Objects.requireNonNull(msg));
    }
}
