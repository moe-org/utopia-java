//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Command.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.command;

import moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatObject;
import moe.kawayi.org.utopia.server.util.NotNull;

/**
 * 命令接口
 */
public interface Command {

    /**
     * 获取命令名称
     *
     * @return 命令名称
     */
    @NotNull
    String getName();

    /**
     * 获取命令帮助
     *
     * @return 命令帮助
     */
    @NotNull
    String getHelp();

    /**
     * 执行命令
     *
     * @param args 命令参数
     * @return 执行结果。
     */
    @NotNull
    UtopiaBinaryFormatObject execute(@NotNull UtopiaBinaryFormatObject args);

}
