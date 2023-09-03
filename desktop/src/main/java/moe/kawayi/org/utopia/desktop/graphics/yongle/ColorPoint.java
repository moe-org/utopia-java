// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ColorPoint.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle;

public final class ColorPoint {
    public byte r = 0;
    public byte g = 0;
    public byte b = 0;

    public byte a = (byte) 255;

    public ColorPoint() {}

    public ColorPoint(byte r, byte g, byte b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public ColorPoint(byte r, byte g, byte b, byte a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public int toRGBA8888() {
        return (((int) r) << 24) + (((int) g) << 16) + (((int) b) << 8) + (((int) a));
    }

    public static final ColorPoint BLACK = new ColorPoint((byte) 0, (byte) 0, (byte) 0);

    public static final ColorPoint GRAY = new ColorPoint((byte) 128, (byte) 128, (byte) 128);

    public static final ColorPoint WHITE = new ColorPoint((byte) 255, (byte) 255, (byte) 255);
    public static final ColorPoint BLUE = new ColorPoint((byte) 0, (byte) 0, (byte) 255);
    public static final ColorPoint YELLOW = new ColorPoint((byte) 255, (byte) 255, (byte) 0);
    public static final ColorPoint RED = new ColorPoint((byte) 255, (byte) 0, (byte) 0);
    public static final ColorPoint GREEN = new ColorPoint((byte) 0, (byte) 128, (byte) 0);
}
