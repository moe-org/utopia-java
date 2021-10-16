//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopApplicationListener.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * 游戏主循环
 */
public class DesktopApplicationListener implements ApplicationListener {

    /**
     * 摄像机默认高度。同时作为窗口最小高度。
     */
    private static final int CAMERA_DEFAULT_WIDTH = 800;

    /**
     * 摄像机默认宽度。同时作为窗口最小宽度。
     */
    private static final int CAMERA_DEFAULT_HEIGHT = 480;


    /**
     * 摄像机
     */
    private OrthographicCamera camera;

    /**
     * 视窗
     */
    private ExtendViewport viewport;

    @Override
    public void create() {
        camera = new OrthographicCamera(CAMERA_DEFAULT_WIDTH,CAMERA_DEFAULT_HEIGHT);
        camera.setToOrtho(false,CAMERA_DEFAULT_WIDTH,CAMERA_DEFAULT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight,camera);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        camera.update();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        camera.update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
