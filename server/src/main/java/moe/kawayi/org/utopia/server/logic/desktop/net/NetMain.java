//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NetMain.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.logic.desktop.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 客户端网络主类
 */
public final class NetMain {

    /**
     * private
     */
    private NetMain(){}

    /**
     * 服务器运行状态
     */
    private static final AtomicBoolean IS_RUNNING = new AtomicBoolean(false);

    /**
     * 工作组
     */
    @Nullable
    private final static AtomicReference<NioEventLoopGroup> WORKER_GROUP = new AtomicReference<>(null);

    /**
     * 检查服务器是否运行
     * @return 返回true则服务器正在运行
     */
    public static boolean isRunning(){
        return IS_RUNNING.get();
    }

    /**
     * 通道组
     */
    public static final AtomicReference<Channel> CLIENT_CHANNEL = new AtomicReference<>(null);

    /**
     * 开启服务器
     * @param uriPath uri地址
     * @throws java.net.URISyntaxException URL语法错误
     * @throws java.lang.InterruptedException netty中断错误
     */
    @Nullable
    public static synchronized void start(@NotNull String uriPath)
            throws java.net.URISyntaxException,java.lang.InterruptedException{
        if(IS_RUNNING.getAndSet(true)) {
            return;
        }

        WORKER_GROUP.set(new NioEventLoopGroup(1));

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(WORKER_GROUP.get())
                .channel(NioSocketChannel.class)
                .handler(new NettyChannelInit());

        var uri = new URI("utopia-server://" + uriPath);

        var channel = bootstrap.connect(uri.getHost(),uri.getPort()).addListener(
                future -> {
                    if(!future.isSuccess()){
                        close();
                    }
                }
        ).sync().channel();

        channel.closeFuture().addListener(
                future -> {
                    close();
                }
        );
    }

    /**
     * 关闭服务器
     */
    public static void close(){
        if(!IS_RUNNING.getAndSet(false))
            return;

        var got = WORKER_GROUP.getAndSet(null);

        if(got != null && (!(got.isTerminated() || got.isShutdown())))
            got.shutdownGracefully();
    }

}
