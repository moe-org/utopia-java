// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The CharacterPixmap.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype;

import com.badlogic.gdx.graphics.Pixmap;

public class CharacterPixmap {

    public final Pixmap map;

    public final CharacterInfo info;

    public CharacterPixmap(Pixmap font, CharacterInfo info) {
        this.map = font;
        this.info = info;
    }
}
