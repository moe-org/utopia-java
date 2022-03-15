//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DekstopApplication.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import moe.kawayi.org.utopia.core.resource.ResourceManager;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * javafx程序
 */
public class DesktopApplication extends Application {

    /**
     * 最小窗口高度
     */
    public static final int MIN_HEIGHT = 768;

    /**
     * 最小窗口宽度
     */
    public static final int MIN_WIDTH = 1024;

    /**
     * 宽高比
     */
    private static final double W_H_RATIO = (double)MIN_WIDTH / (double)MIN_HEIGHT;

    /**
     * 小图标(32x32)路径。基于Utopia Root。
     */
    public static final String SMALL_ICON_PATH = "Utopia(32x32).png";

    /**
     * 大图标(128x128)路径。基于Utopia Root。
     */
    public static final String LARGE_ICON_PATH = "Utopia(128x128).png";

    /**
     * 检查当前是否正在调整窗口大小
     */
    private final AtomicBoolean isResizing = new AtomicBoolean(false);

    /**
     * 标准化窗口。设置窗口标准属性。
     * @param stage 窗口
     */
    private static void standardizationStage(@NotNull Stage stage){
        stage.setTitle("Utopia");
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setHeight(MIN_HEIGHT);
        stage.setWidth(MIN_WIDTH);

        stage.getIcons().add(new Image(
                "file:" +
                        ResourceManager.getPath(LARGE_ICON_PATH)));

        stage.getIcons().add(new Image(
                "file:" +
                        ResourceManager.getPath(SMALL_ICON_PATH)));
    }

    @Override
    public void start(@NotNull Stage stage) throws Exception {
        standardizationStage(Objects.requireNonNull(stage));

        Group root = new Group();
        Scene scene = new Scene(root);
        root.setAutoSizeChildren(true);
        
        Text text = new Text((double)MIN_WIDTH / 2, (double)MIN_HEIGHT / 2, "我日你先人");

        text.setFont(new Font(24));

        root.getChildren().add(text);

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            if(isResizing.get())
                return;
            else
                isResizing.set(true);

            stage.setWidth(newVal.doubleValue() * W_H_RATIO);

            isResizing.set(false);
        });

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if(isResizing.get())
                return;
            else
                isResizing.set(true);

            stage.setHeight(newVal.doubleValue() / W_H_RATIO);

            isResizing.set(false);
        });

        stage.setScene(scene);
        stage.show();
    }
}
