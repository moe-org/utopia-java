//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopLauncher.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 主类
 */
public class DesktopLauncher {

    /**
     * 入口函数
     * @param args 命令行参数
     */
    public static void main(@NotNull String[] args){
        DesktopApplication.launch(DesktopApplication.class,args);
    }

}
