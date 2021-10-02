//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NetConfig.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

/**
 * 网络配置类key
 */
public final class NetConfig {
    /**
     * netty的boss线程数量
     */
    public final static String NETTY_BOOS_THREAD_COUNT = "net.boosGroup.threadCount";

    /**
     * {@link NetConfig#NETTY_BOOS_THREAD_COUNT}的默认值
     */
    public final static String NETTY_BOOS_THREAD_COUNT_DEFAULT = "1";


    /**
     * netty的worker线程数量
     */
    public final static String NETTY_WORKER_THREAD_COUNT = "net.workerGroup.threadCount";

    /**
     * {@link NetConfig#NETTY_WORKER_THREAD_COUNT}的默认值
     */
    public final static String NETTY_WORKER_THREAD_COUNT_DEFAULT = "1";


    /**
     * 端口号
     */
    public final static String PORT = "net.port";


    /**
     * {@link NetConfig#PORT}的默认值
     */
    public final static String PORT_DEFAULT = "25674";


    /**
     * 最大等待队列
     */
    public final static String MAX_WAIT_LIST = "net.maxWaitList";

    /**
     * {@link NetConfig#MAX_WAIT_LIST}的默认值
     */
    public final static String MAX_WAIT_LIST_DEFAULT = "32";

    /**
     * 网络默认配置文件
     */
    public final static String NET_DEFAULT_CONFIG =
            "# 网络配置文件\n" +
                    NETTY_BOOS_THREAD_COUNT + "=" + NETTY_BOOS_THREAD_COUNT_DEFAULT + "\n" +
                    NETTY_WORKER_THREAD_COUNT + "=" + NETTY_WORKER_THREAD_COUNT_DEFAULT + "\n" +
                    PORT + "=" + PORT_DEFAULT + "\n" +
                    MAX_WAIT_LIST + "=" + MAX_WAIT_LIST_DEFAULT + "\n";
}
