//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopLauncher.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.logic.desktop.main;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 主类
 */
public class DesktopLauncher {

    /**
     * private
     */
    private DesktopLauncher(){}

    /**
     * 入口函数
     * @param args 命令行参数
     */
    public static void main(@NotNull String[] args){
        DesktopApplication application = new DesktopApplication();

        application.init();
        application.start();
    }

}
