//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Main.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.main;

import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.util.EnvironmentChecker;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.UtopiaVersion;
import moe.kawayi.org.utopia.server.logic.GameLogicLoop;
import moe.kawayi.org.utopia.server.net.NetMain;

/**
 * 主类
 */
public final class Main {

    /**
     * private
     */
    private Main(){}

    /**
     * 日志器
     */
    private static final Logger LOGGER = LogManagers.getLogger(Main.class);

    /**
     * utopia server 入口函数
     *
     * @param args 参数
     * @throws Exception 交给运行时处理
     */
    public static void main(@NotNull String[] args) throws Exception {
        // set thread name
        Thread.currentThread().setName("Main");

        // 打印信息
        EnvironmentChecker.print(LOGGER::info);

        if(!EnvironmentChecker.check()){
            LOGGER.error("JVM check failed down");
            System.exit(-1);
        }

        LOGGER.info("Server start");

        LOGGER.info("utopia-version:{}",UtopiaVersion.getUtopiaVersion());

        // 添加hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // 关闭逻辑线程
            GameLogicLoop.shutdown();

            // 关闭网络服务器
            NetMain.shutdown();

            // 服务器关闭
            LOGGER.info("Server shut down!");
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

        } catch (@NotNull Throwable err) {
            LOGGER.error("Server crash", err);

            throw err;
        } finally {
            LOGGER.error("Server shutdown");

            // 关闭逻辑线程
            GameLogicLoop.shutdown();

            // 关闭网络服务器
            NetMain.shutdown();
        }
    }


}
