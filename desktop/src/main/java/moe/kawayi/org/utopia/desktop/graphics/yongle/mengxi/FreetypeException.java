// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FreetypeException.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

import moe.kawayi.org.utopia.core.util.NotNull;

import org.lwjgl.util.freetype.FreeType;

public class FreetypeException extends Exception {

    private FreetypeException(@NotNull String str) {
        super(str);
    }

    public static void checkError(int error) throws FreetypeException {
        if (error == 0) {
            return;
        }

        var str = FreeType.FT_Error_String(error);
        throw new FreetypeException("Freetype error(code 0x" + Integer.toString(error, 16) + "):" + str);
    }
}
