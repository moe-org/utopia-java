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
import java.util.concurrent.atomic.AtomicLong;

/**
 * fps显示
 */
public class FPSStage {

    private final Label fps;

    private final AtomicLong lastFps = new AtomicLong();

    private final AtomicLong lastResize = new AtomicLong();

    private long lastNano = System.nanoTime();

    private final Stage stage;

    private final ScreenViewport viewport = new ScreenViewport();

    private final Viewport parentViewport;

    /**
     * 构造一个fps显示器
     * @param x 屏幕x轴大小
     * @param y 屏幕y轴大小
     * @param parentViewport 父视图。
     *                       将会在使用本类的私有视图的相关渲染函数(如{@link FPSStage#resize(int, int)}或者{@link FPSStage#render()})，
     *                       之前调用私有视图的{@link Viewport#apply(boolean)}，之后调用父视图的{@link Viewport#apply(boolean)}。
     *                       此举为了防止在{@link FPSStage}之外意外的视图渲染操作。
     *                       不会对父视图进行{@link FPSStage#dispose()}操作。
     */
    public FPSStage(int x,int y,Viewport parentViewport){
        viewport.update(x, y,true);

        this.parentViewport = parentViewport;

        // 设置舞台
        stage = new Stage(viewport);

        var labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.GREEN;
        labelStyle.font = new BitmapFont();

        fps = new Label("f:0;r:0;",labelStyle);
        fps.setPosition(0,0);

        stage.addActor(fps);
    }

    /**
     * 重新设置fps显示大小
     * @param x x轴
     * @param y y轴
     */
    public void resize(int x,int y){
        viewport.apply();
        viewport.update(x,y,true);

        // 增加resize 频次
        lastResize.incrementAndGet();

        parentViewport.apply();
    }

    /**
     * 更新
     */
    public void render(){
        if((System.nanoTime() - lastNano) >= 1000000000){
            // 满1秒
            // 刷新数据
            fps.setText(
                    "f:".concat(String.valueOf(lastFps.get()))
                            .concat(";r:").concat(String.valueOf(lastResize.get()))
                            .concat(";"));

            // 当前render调用
            // 处在新的一秒里的第一帧
            // 所以fps设置为1
            lastFps.set(1);

            // resize则设置为0
            lastResize.set(0);

            lastNano = System.nanoTime();
        }
        else{
            // or，增加帧率
            lastFps.incrementAndGet();
        }

        // 渲染
        viewport.apply(true);
        stage.act();
        stage.draw();
        parentViewport.apply(true);
    }

    /**
     * 关闭fps
     */
    public void dispose(){
        stage.dispose();
    }

}
