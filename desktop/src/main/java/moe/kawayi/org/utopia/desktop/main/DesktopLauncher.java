// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopLauncher.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import java.io.IOException;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.VersionGetter;
import moe.kawayi.org.utopia.desktop.util.Checker;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * 主类
 */
public class DesktopLauncher {

    private static final Logger LOGGER = GlobalLogManager.getLogger(DesktopLauncher.class);

    /**
     * private
     */
    private DesktopLauncher() {}

    /**
     * 入口函数
     *
     * @param args 命令行参数
     */
    public static void main(@NotNull String[] args) throws IOException {

        // 解析参数
        int ptr = 0;
        while (ptr != args.length) {
            String arg = args[ptr];

            ptr += 1;
        }

        LOGGER.info("start");

        LOGGER.debug("Utopia version:{}", VersionGetter.getUtopiaVersion().toString());
        LOGGER.debug("Freetype version:{}", Checker.getFreetypeVersion().orElseThrow());

        try {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setForegroundFPS(60);
            config.setTitle("Utopia");
            new Lwjgl3Application(new DesktopApplication(), config);
        } catch (Exception other) {
            LOGGER.error("crash", other);
        }
    }
}
