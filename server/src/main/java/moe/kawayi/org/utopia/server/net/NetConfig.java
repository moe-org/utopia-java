// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NetConfig.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

/**
 * 网络配置类key
 */
public final class NetConfig {

    /**
     * private
     */
    private NetConfig() {}

    /**
     * netty的boss线程数量
     */
    public static final String NETTY_BOOS_THREAD_COUNT = "BoosGroupThreadCount";

    /**
     * {@link NetConfig#NETTY_BOOS_THREAD_COUNT}的默认值
     */
    public static final int NETTY_BOOS_THREAD_COUNT_DEFAULT = 1;

    /**
     * netty的worker线程数量
     */
    public static final String NETTY_WORKER_THREAD_COUNT = "WorkerGroupThreadCount";

    /**
     * {@link NetConfig#NETTY_WORKER_THREAD_COUNT}的默认值
     */
    public static final int NETTY_WORKER_THREAD_COUNT_DEFAULT = 1;

    /**
     * 端口号
     */
    public static final String PORT = "Port";

    /**
     * {@link NetConfig#PORT}的默认值
     */
    public static final int PORT_DEFAULT = 25674;

    /**
     * 最大等待队列
     */
    public static final String MAX_WAIT_LIST = "NetMaxWaitList";

    /**
     * {@link NetConfig#MAX_WAIT_LIST}的默认值
     */
    public static final int MAX_WAIT_LIST_DEFAULT = 32;
}
