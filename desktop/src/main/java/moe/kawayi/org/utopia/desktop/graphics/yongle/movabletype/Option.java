// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Option.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype;

/**
 * 布局/渲染选项
 */
public class Option {

    private int fontWidthPixel = 16;

    private int fontHeightPixel = 16;

    /**
     * BCP 47:ISO 639
     */
    private String language = "en";

    /**
     * ISO 15924
     */
    private String script = "Latn";

    private boolean isRTL = false;

    public int getFontWidthPixel() {
        return fontWidthPixel;
    }

    public void setFontWidthPixel(int fontWidthPixel) {
        this.fontWidthPixel = fontWidthPixel;
    }

    public int getFontHeightPixel() {
        return fontHeightPixel;
    }

    public void setFontHeightPixel(int fontHeightPixel) {
        this.fontHeightPixel = fontHeightPixel;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * BCP 47
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getScript() {
        return script;
    }

    /**
     * ISO 15924
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * 文字是否是从右到左排列,如果不是,那么则认为是从左到右
     * @return
     */
    public boolean isRTL() {
        return isRTL;
    }

    public void setRTL(boolean RTL) {
        isRTL = RTL;
    }
}
