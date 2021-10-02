//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Main.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.main;

import moe.kawayi.org.utopia.server.config.ConfigManager;
import moe.kawayi.org.utopia.server.logic.GameLogicLoop;
import moe.kawayi.org.utopia.server.net.NetMain;
import moe.kawayi.org.utopia.server.util.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 主类
 */
public final class Main {

    /**
     * 日志器
     */
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * utopia server 入口函数
     *
     * @param args 参数
     * @throws Exception 交给运行时处理
     */
    public static void main(@NotNull String[] args) throws Exception {
        Thread.currentThread().setName("Main");
        logger.info("Server start.");

        // 读取配置文件
        ConfigManager.loadSystemConfig();

        // 添加hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // 关闭逻辑线程
            GameLogicLoop.shutdown();

            // 关闭网络服务器
            NetMain.shutdown();

            // 服务器关闭
            logger.info("Server shut down!");
        }));

        try {
            // 启动逻辑服务器
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    GameLogicLoop.mainLoop();
                }
            });
            t.setName("Main Logic Thread");
            t.start();

            // 设置网络
            NetMain.internetBootstrap();

            // Nothing to do!
            t.join();

        } catch (Throwable err) {
            logger.error("Server crash", err);

            throw err;
        } finally {
            logger.error("Server shutdown");

            // 关闭逻辑线程
            GameLogicLoop.shutdown();

            // 关闭网络服务器
            NetMain.shutdown();
        }
    }


}
