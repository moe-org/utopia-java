// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopLauncher.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.util.CleanerManager;
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

        // 解析参数
        int ptr = 0;
        while (ptr != args.length) {
            String arg = args[ptr];

            switch (arg) {
                case "--enable-opengl-debug" -> application.openglDebug = true;
                case "--use-small-icon" -> application.useSmallIcon = true;
                case "--enable-opengl-forward-compat" -> application.forwardCompat = true;
                case "--set-max-cleaners" -> {
                    ptr += 1;
                    if (ptr >= args.length) {
                        throw new IllegalArgumentException("--max-cleaners must be followed by an integer");
                    } else {
                        CleanerManager.setMax(Integer.parseInt(args[ptr]));
                    }
                }
                default -> throw new IllegalArgumentException("unknown argument: " + arg);
            }

            ptr += 1;
        }

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
