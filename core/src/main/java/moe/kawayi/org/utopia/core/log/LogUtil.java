// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LogUtil.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import moe.kawayi.org.utopia.core.resource.ResourceManager;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.VersionGetter;

import io.netty.util.internal.logging.InternalLoggerFactory;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * log设置
 */
public class LogUtil {

    /**
     * private
     */
    private LogUtil() {}

    /**
     * 控制台日志样式
     */
    private static final String CONSOLE_LOG_PATTERN = "[%d{HH:mm:ss.SSS}] [%t] "
            + "[%highlight{%-5level}{FATAL=Bright red, ERROR=Red, WARN=Yellow, INFO=Black, DEBUG=Blue, TRACE=Cyan}] "
            + "[%logger{36}]:%msg %exception%n";

    /**
     * 滚动文件输出格式
     */
    private static final String ROLLING_FILE_PATTERN =
            "[%d{HH:mm:ss.SSS}] [%t] [%highlight{%-5level}{${highlight_color}}] [%logger{36}]:%msg%exception%n";

    /**
     * 日志上下文名称
     */
    public static final String CONTEXT_NAME = "utopia-log-context";

    /**
     * 读取日志配置xml配置路径。基于utopia-root
     */
    public static final String CONFIG_FILE_PATH = "log-config.xml";

    /**
     * 内置配置文件路径（位于发布jar内）
     */
    public static final String BUILT_IN_CONFIG_FILE_PATH = "/utopia-default-log4j2-config.xml";

    /**
     * 值得注意的是:Log4j2的彩色输出在Windows下并不是默认的
     */
    private static void enableLog4j2Color() {
        System.setProperty("log4j.skipJansi", "false");
    }

    /**
     * 配置日志器。目前实现基于log4j2
     * <br/>
     * 会做以下动作:<br/>
     * - 读取日志配置。如果不存在则创建，
     * 复制{@link LogUtil#BUILT_IN_CONFIG_FILE_PATH}到{@link LogUtil#CONFIG_FILE_PATH}<br/>
     * - 根据配置创建日志管理器<br/>
     * - 设置netty使用此日志管理器<br/>
     * - 返回此日志管理器<br/>
     * <br/>
     * 如果配置失败将会退出程序{@link System#exit(int)}(退出代码为-1)
     * <br/>
     *
     * @return 配置后的日志管理器
     */
    @NotNull
    public static synchronized LogManager configureLog() {
        enableLog4j2Color();

        // 配置日志配置文件
        try {
            if (!Files.exists(ResourceManager.getPath(CONFIG_FILE_PATH))) {
                try (var is = VersionGetter.class.getResourceAsStream(BUILT_IN_CONFIG_FILE_PATH)) {

                    if (is == null)
                        throw new IOException("couldn't open built-in file in jar:" + BUILT_IN_CONFIG_FILE_PATH);

                    var data = is.readAllBytes();

                    Files.write(ResourceManager.getPath(CONFIG_FILE_PATH), data, StandardOpenOption.CREATE);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        // 配置文件
        var standard = Configurator.initialize(
                CONTEXT_NAME,
                // ClassLoader.getSystemClassLoader(),
                LogUtil.class.getClassLoader(),
                ResourceManager.getPath(CONFIG_FILE_PATH).toString());

        Configurator.initialize(
                CONTEXT_NAME, ResourceManager.getPath(CONFIG_FILE_PATH).toString());

        var manager = new Log4j2LogManager(standard);

        // 设置netty日志
        InternalLoggerFactory.setDefaultFactory(manager);

        return manager;
    }
}
