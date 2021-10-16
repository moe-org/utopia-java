//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopApplicationListener.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import moe.kawayi.org.utopia.desktop.screen.MainEnumScreen;

/**
 * 游戏主循环
 */
public class DesktopApplicationListener implements ApplicationListener {

    /**
     * 摄像机默认高度。同时作为窗口最小高度。
     */
    public static final int CAMERA_DEFAULT_WIDTH = 1024;

    /**
     * 摄像机默认宽度。同时作为窗口最小宽度。
     */
    public static final int CAMERA_DEFAULT_HEIGHT = 768;

    /**
     * 当前场景
     */
    private Screen screen = null;

    @Override
    public void create() {
        screen = new MainEnumScreen();
        screen.show();
    }

    @Override
    public void resize(int width, int height) {
        if(screen != null)
            screen.resize(width, height);
    }

    @Override
    public void render() {
        if(screen != null)
            screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {
        if(screen != null)
            screen.pause();
    }

    @Override
    public void resume() {
        if(screen != null)
            screen.resume();

    }

    @Override
    public void dispose() {
        if(screen != null)
            screen.hide();
    }
}
