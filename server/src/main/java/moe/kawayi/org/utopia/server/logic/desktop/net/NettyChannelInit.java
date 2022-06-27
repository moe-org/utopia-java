//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NettyChannelInit.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.logic.desktop.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import moe.kawayi.org.utopia.core.net.PacketEncoder;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 初始化netty channel
 */
public final class NettyChannelInit extends ChannelInitializer<SocketChannel>
{
    /**
     * 默认构造
     */
    public NettyChannelInit(){}

    @Override
    protected void initChannel(@NotNull SocketChannel ch) throws Exception {
        ch.pipeline()
                // 添加一个拆包器
                .addLast("packet length parser",new LengthFieldBasedFrameDecoder(
                        Integer.MAX_VALUE,
                        0,
                        4,
                        0,
                        // 丢弃长度
                        4
                ))
                // 添加一个包分类器
                .addLast("packet classifier",new PacketClassifier())
                // 添加一个输出解码器
                .addLast("utopia binary format output encoder",new PacketEncoder())
                // 添加一个处理client logic的channel
                .addLast("client logic channel",new ClientInitHandle());
    }
}
