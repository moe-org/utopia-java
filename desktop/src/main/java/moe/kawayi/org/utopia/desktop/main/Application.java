// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Application.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

/**
 * 存储一个应用常量
 */
public class Application {

    /**
     * private
     */
    private Application() {}

    /**
     * 最小窗口高度
     */
    public static final int MIN_HEIGHT = 360;

    /**
     * 最小窗口宽度
     */
    public static final int MIN_WIDTH = 800;

    /**
     * 宽高比
     */
    private static final double W_H_RATIO = (double) MIN_WIDTH / (double) MIN_HEIGHT;

    /**
     * 小图标(32x32)路径。基于Utopia Root。
     */
    public static final String SMALL_ICON_PATH = "Utopia(32x32).png";

    /**
     * 大图标(128x128)路径。基于Utopia Root。
     */
    public static final String LARGE_ICON_PATH = "Utopia(128x128).png";
}
