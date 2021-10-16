//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NetMain.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class NetMain {


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
     */
    public static synchronized void start(@NotNull String uriPath)throws java.net.URISyntaxException{
        if(IS_RUNNING.get())
            return;

        WORKER_GROUP.set(new NioEventLoopGroup(1));

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(WORKER_GROUP.get())
                .channel(NioSocketChannel.class)
                .handler(new NettyChannelInit());

        var uri = new URI("utopia-server://" + uriPath);

        bootstrap.connect(uri.getHost(),uri.getPort()).addListener(
                future -> {
                    if(!future.isSuccess()){
                        IS_RUNNING.set(false);
                    }
                }
        );

        IS_RUNNING.set(true);
    }

    /**
     * 关闭服务器
     */
    public static void close(){
        if(!IS_RUNNING.get())
            return;

        IS_RUNNING.set(false);
        var got = WORKER_GROUP.getAndUpdate(ignore -> null);

        if(got == null)
            return;

        got.shutdownGracefully();
    }

}
