// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LayoutInfo.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.mengxi;

/**
 * 布局信息
 */
public class LayoutInfo {

    public int glyphID;

    public int xOffset;

    public int yOffset;

    public int xAdvance;

    public int yAdvance;

    public int getGlyphID() {
        return glyphID;
    }

    public void setGlyphID(int glyphID) {
        this.glyphID = glyphID;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public int getxAdvance() {
        return xAdvance;
    }

    public void setxAdvance(int xAdvance) {
        this.xAdvance = xAdvance;
    }

    public int getyAdvance() {
        return yAdvance;
    }

    public void setyAdvance(int yAdvance) {
        this.yAdvance = yAdvance;
    }
}
