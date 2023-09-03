// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopApplication.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import java.io.IOException;
import java.nio.file.Path;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype.Engine;
import moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype.FreetypeException;
import moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype.HarfbuzzException;
import moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype.Option;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * LWJGL3程序
 */
public class DesktopApplication extends ApplicationAdapter {

    /**
     * 默认构造函数
     */
    public DesktopApplication() throws FreetypeException, HarfbuzzException {}

    /**
     * 日志器
     */
    public final Logger logger = GlobalLogManager.getLogger(this.getClass());

    private final Engine engine = new Engine();

    com.badlogic.gdx.graphics.Texture texture;

    SpriteBatch batch;

    @Override
    public void create() {
        this.batch = new SpriteBatch();

        try {
            engine.loadFontFromFile(Path.of("C:\\Users\\mingm\\Downloads\\unifont-15.0.06.ttf"), 0);
        } catch (HarfbuzzException | IOException | FreetypeException e) {
            throw new RuntimeException(e);
        }

        var option = new Option();
        option.setLanguage("zh-CN");
        option.setScript("Hans");
        option.setRTL(true);
        option.setFontHeightPixel(64);
        option.setFontWidthPixel(64);

        Pixmap draw;
        try {
            draw = engine.drawLine("既来之则安之", option);
        } catch (HarfbuzzException | FreetypeException e) {
            throw new RuntimeException(e);
        }

        GlobalLogManager.GLOBAL_LOGGER.debug("pixmap size:{}x{}", draw.getWidth(), draw.getHeight());

        this.texture = new com.badlogic.gdx.graphics.Texture(draw);
        draw.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 0);
        batch.begin();
        batch.draw(this.texture, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.texture.dispose();
    }
}
