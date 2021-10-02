//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NetMain.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import moe.kawayi.org.utopia.server.config.ConfigManager;
import moe.kawayi.org.utopia.server.util.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 设置网络
 */
public final class NetMain {

    /**
     * 日志器
     */
    private static final Logger logger = LogManager.getLogger(NetMain.class);

    /**
     * 服务器运行状态
     */
    private static final AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * 工作组
     */
    @Nullable
    private static EventLoopGroup bossGroup = null;

    /**
     * 工作组
     */
    @Nullable
    private static EventLoopGroup workerGroup = null;

    /**
     * 锁
     */
    private static final Object locker = new Object();

    /**
     * 启动网络系统
     *
     * @throws InterruptedException 线程中断
     */
    public static void internetBootstrap() throws InterruptedException {
        // never run again
        if (isRunning.get())
            return;

        synchronized (locker) {
            // 获取设置
            int boosThreadCount = Integer.parseInt(
                    ConfigManager.getSystemConfig(NetConfig.NETTY_BOOS_THREAD_COUNT).orElseThrow());

            int workerThreadCount = Integer.parseInt(
                    ConfigManager.getSystemConfig(NetConfig.NETTY_WORKER_THREAD_COUNT).orElseThrow());

            int port = Integer.parseInt(
                    ConfigManager.getSystemConfig(NetConfig.PORT).orElseThrow());

            int maxWaitList = Integer.parseInt(
                    ConfigManager.getSystemConfig(NetConfig.MAX_WAIT_LIST).orElseThrow());

            // 初始化事件循环线程
            bossGroup =
                    new NioEventLoopGroup(
                            boosThreadCount,
                            new NetThreadFactory("boos-thread-"));

            workerGroup =
                    new NioEventLoopGroup(
                            workerThreadCount,
                            new NetThreadFactory("worker-thread-"));

            // 设置启动类
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyChannelInit())
                    .option(ChannelOption.SO_BACKLOG, maxWaitList)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            isRunning.set(true);

            logger.info("网络服务器启动");

            // 设置关闭动作
            f.channel().closeFuture().addListener((ChannelFutureListener) channelFuture -> {
                logger.info("网络服务器关闭");
                isRunning.set(false);
            });
        }
    }

    /**
     * 获取netty是否在运行
     *
     * @return 如果返回true，则说明netty还在运行。
     */
    public static boolean isRun() {
        return isRunning.get();
    }

    /**
     * 关闭netty服务器
     */
    public static void shutdown() {
        synchronized (locker) {
            if (isRun()) {
                if (bossGroup != null)
                    bossGroup.shutdownGracefully();

                if (workerGroup != null)
                    workerGroup.shutdownGracefully();

                bossGroup = null;
                workerGroup = null;
            }
        }
    }

}
