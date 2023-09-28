// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopApplication.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.resource.ResourceManager;
import moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype.Engine;
import moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype.FreetypeException;
import moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype.HarfbuzzException;
import moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype.Option;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    private Engine engine;

    private Stage stage;
    private Table table;

    com.badlogic.gdx.graphics.Texture texture;

    SpriteBatch batch;

    @Override
    public void create() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(true); // This is optional, but enables debug lines for tables.
        this.batch = new SpriteBatch();

        var option = new Option();
        option.setLanguage("en-US");
        option.setScript("Latn");
        option.setRTL(false);
        option.setFontHeightPixel(28);
        option.setFontWidthPixel(26);

        Pixmap draw;
        try (var engine = Engine.createFromFontFile(ResourceManager.getPath("fonts/unifont.ttf"), 0)) {
            draw = engine.drawLine("Hello World From Harfbuzz!!!", option);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.texture = new com.badlogic.gdx.graphics.Texture(draw);
        draw.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        this.stage.dispose();
        this.batch.dispose();
        this.texture.dispose();
    }
}
