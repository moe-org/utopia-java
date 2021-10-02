//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ConfigManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.config;

import moe.kawayi.org.utopia.server.main.Main;
import moe.kawayi.org.utopia.server.net.NetConfig;
import moe.kawayi.org.utopia.server.util.FileTool;
import moe.kawayi.org.utopia.server.util.NotNull;
import moe.kawayi.org.utopia.server.util.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * 配置文件读取器
 */
public final class ConfigManager {

    /**
     * 配置文件目录
     */
    public static final String CONFIG_DIRECTORY = "config";

    /**
     * 系统配置文件名称
     */
    public static final String SYSTEM_CONFIG_FILE = "system-config.properties";

    /**
     * 默认配置文件内容
     */
    @NotNull
    public static final String DEFAULT_CONFIG_FILE_CONTENT =
            "# Utopia Server配置文件" + NetConfig.NET_DEFAULT_CONFIG;

    /**
     * 日志器
     */
    @NotNull
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * 系统配置
     */
    @Nullable
    private static Properties systemConfig = null;

    /**
     * 锁
     */
    @NotNull
    private static final Object locker = new Object();


    /**
     * 载入系统配置
     */
    public static void loadSystemConfig() {
        synchronized (locker) {
            systemConfig = new Properties();

            Path fileName = FileTool.getPath(CONFIG_DIRECTORY, SYSTEM_CONFIG_FILE);
            logger.info("加载配置文件:{}", fileName);

            try (FileInputStream fis = new FileInputStream(fileName.toString())) {

                systemConfig.load(fis);

            } catch (FileNotFoundException ex) {
                logger.error("未找到配置文件:{} err:{}", fileName, ex);
                logger.info("创建默认配置文件:{}", fileName);

                // 创建配置文件
                try {
                    if (Files.exists(Path.of(CONFIG_DIRECTORY)))
                        Files.createDirectories(Path.of(CONFIG_DIRECTORY));

                    Files.createFile(Path.of(fileName.toString()));
                    PrintWriter writer = new PrintWriter(fileName.toString(), StandardCharsets.UTF_8);

                    // Nothing to do
                    writer.write(DEFAULT_CONFIG_FILE_CONTENT);
                    writer.write('\n');

                    writer.close();
                } catch (IOException err) {
                    logger.error("创建默认配置文件失败", err);
                }

            } catch (IOException ex) {
                logger.error("加载配置文件失败:{} err:{}", fileName, ex);
            }
        }
    }

    /**
     * 获取系统配置
     *
     * @param key 键
     * @return 键对应的值。如果未找到键或者配置文件未加载，则返回{@link Optional#empty()}，并且日志会打印一条警告。
     */
    @NotNull
    public static Optional<String> getSystemConfig(@NotNull String key) {
        Objects.requireNonNull(key, "key must not be null");

        synchronized (locker) {

            if (systemConfig == null) {
                return Optional.empty();
            } else {
                var gotValue = systemConfig.getProperty(key);

                if (gotValue == null)
                    logger.warn("未找到系统配置key-value:{}", key);

                return Optional.ofNullable(gotValue);
            }
        }
    }

}
