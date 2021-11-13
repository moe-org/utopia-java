//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FPSStage.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.desktop.main.DesktopApplicationListener;
import moe.kawayi.org.utopia.desktop.screen.MainEnumScreen;

import java.util.Objects;

/**
 * fps显示
 */
public class FPSStage {

    private final Label fps;

    private int lastFps = 0;

    private long lastNano = System.nanoTime();

    private final Stage stage;

    private final ScreenViewport viewport = new ScreenViewport();

    /**
     * 构造一个fps显示器
     * @param x 屏幕x轴大小
     * @param y 屏幕y轴大小
     */
    public FPSStage(int x,int y){
        viewport.update(x, y,true);

        stage = new Stage(viewport);

        var labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.GREEN;
        labelStyle.font = new BitmapFont();

        fps = new Label("fps:0",labelStyle);
        fps.setPosition(0,0);

        stage.addActor(fps);
    }

    /**
     * 重新设置fps显示大小
     * @param x x轴
     * @param y y轴
     */
    public void resize(int x,int y){
        viewport.update(x,y,true);
    }

    /**
     * 更新
     */
    public void update(){
        if((System.nanoTime() - lastNano) >= 1000000000){
            fps.setText("fps:".concat(String.valueOf(lastFps)));
            lastFps = 0;
            lastNano = System.nanoTime();
        }
        else{
            lastFps++;
        }

        stage.act();
        stage.draw();
    }

    /**
     * 关闭fps
     */
    public void dispose(){
        stage.dispose();
    }

}
