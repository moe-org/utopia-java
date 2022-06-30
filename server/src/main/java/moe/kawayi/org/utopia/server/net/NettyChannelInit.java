// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NettyChannelInit.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

import moe.kawayi.org.utopia.core.net.PacketEncoder;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.server.net.handle.PingPacketHandle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 初始化channel
 */
@ChannelHandler.Sharable
public final class NettyChannelInit extends ChannelInitializer<NioSocketChannel> {

    /**
     * 默认构造
     */
    public NettyChannelInit() {}

    @Override
    protected void initChannel(@NotNull NioSocketChannel socketChannel) throws Exception {

        var pipeline = socketChannel.pipeline();
        // 添加一个解码器来解析数据长度
        pipeline
                // 添加一个拆包器
                .addLast(
                        "packet length parser",
                        new LengthFieldBasedFrameDecoder(
                                Integer.MAX_VALUE,
                                0,
                                4,
                                0,
                                // 丢弃长度
                                4))
                // 添加一个包分类器
                .addLast("packet classifier", new PacketClassifier())
                // 添加一个输出解码器
                .addLast("utopia binary format output encoder", new PacketEncoder())
                // 添加一些实际处理包的handle
                .addLast("packet ping handle", new PingPacketHandle());
    }
}
