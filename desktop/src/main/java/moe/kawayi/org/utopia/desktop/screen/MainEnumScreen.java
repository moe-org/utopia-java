//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The MainEnumScreen.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.netty.util.AttributeKey;
import moe.kawayi.org.utopia.client.net.NetMain;
import moe.kawayi.org.utopia.core.net.packet.PingPacket;

import javax.swing.*;
import javax.swing.plaf.TextUI;

import java.net.URISyntaxException;

import static moe.kawayi.org.utopia.client.net.PacketClassifier.CHANNEL_SERVER_PING_VERSION;
import static moe.kawayi.org.utopia.desktop.main.DesktopApplicationListener.CAMERA_DEFAULT_HEIGHT;
import static moe.kawayi.org.utopia.desktop.main.DesktopApplicationListener.CAMERA_DEFAULT_WIDTH;

/**
 * 游戏主菜单
 */
public class MainEnumScreen implements Screen {

    /**
     * 摄像机
     */
    private OrthographicCamera camera;

    /**
     * 视窗
     */
    private ExtendViewport viewport;

    private Stage stage;

    private Label label;

    /**
     * 输入框
     */
    private TextField uriInputField;

    @Override
    public void show() {
        // 初始化摄像机和视图
        camera = new OrthographicCamera(CAMERA_DEFAULT_WIDTH,CAMERA_DEFAULT_HEIGHT);
        camera.setToOrtho(false,CAMERA_DEFAULT_WIDTH,CAMERA_DEFAULT_HEIGHT);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight,camera);

        // 初始化输入框
        var style = new TextField.TextFieldStyle();

        style.background = new TextureRegionDrawable(createBackground());
        style.fontColor = Color.BLACK;
        var font = new BitmapFont();
        font.getData().setScale(3.0f);
        style.font = font;

        uriInputField = new TextField("",style);
        uriInputField.setAlignment(Align.center);

        uriInputField.setSize(500,200);
        uriInputField.setPosition(100,100);

        // 初始化标签
        var labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.BLACK;
        labelStyle.font = font;

        label = new Label("",labelStyle);

        label.setAlignment(Align.center);
        label.setStyle(labelStyle);
        label.setSize(200,100);
        label.setPosition(500,500);

        // 设置舞台
        stage = new Stage(viewport);
        // 设置输入焦点
        Gdx.input.setInputProcessor(stage);

        stage.addActor(uriInputField);
        stage.addActor(label);
    }

    private Texture createBackground(){
        Pixmap xp = new Pixmap(500,200, Pixmap.Format.RGBA8888);
        xp.setColor(1,0,0,1);
        xp.drawRectangle(0,0,500, 200);
        Texture texture = new Texture(xp);
        xp.dispose();
        return texture;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        camera.update();

        // 检查是否链接服务器
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            try {
                if(!NetMain.isRunning())
                    NetMain.start(uriInputField.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 检查文本信息
        if(NetMain.CLIENT_CHANNEL.get() != null){
            var got = NetMain.CLIENT_CHANNEL.get();
            var attr = got.attr(AttributeKey.valueOf(CHANNEL_SERVER_PING_VERSION));

            if(attr.get() != null)
                label.setText("version:".concat(attr.get().toString()));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
