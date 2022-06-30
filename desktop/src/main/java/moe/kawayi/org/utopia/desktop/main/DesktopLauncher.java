// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopLauncher.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.desktop.graphics.OpenGLException;

/**
 * 主类
 */
public class DesktopLauncher {

    private static final Logger LOGGER = LogManagers.getLogger(DesktopLauncher.class);

    /**
     * private
     */
    private DesktopLauncher() {}

    /**
     * 入口函数
     *
     * @param args 命令行参数
     */
    public static void main(@NotNull String[] args) {
        DesktopApplication application = new DesktopApplication();

        LOGGER.info("start");

        try {
            application.init();
            application.start();
        } catch (OpenGLException err) {
            LOGGER.error("crash by OpenGL", err);
        } catch (Exception other) {
            LOGGER.error("crash", other);
        }
    }
}
