// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Checker.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.util;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.Version;

import org.lwjgl.util.freetype.FreeType;

public class Checker {

    private Checker() {}

    public static Optional<Version> getFreetypeVersion() {
        return Optional.of(
                new Version(FreeType.FREETYPE_MAJOR, FreeType.FREETYPE_MINOR, FreeType.FREETYPE_PATCH, null));
    }
}
