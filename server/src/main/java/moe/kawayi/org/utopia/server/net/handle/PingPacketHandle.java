//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PingHandle.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import moe.kawayi.org.utopia.core.net.packet.PingPacket;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 处理ping的handle
 *
 * 线程安全的(仅仅是返回输入的{@link PingPacket}包)
 */
public final class PingPacketHandle extends SimpleChannelInboundHandler<PingPacket> {

    /**
     * 默认构造函数
     */
    public PingPacketHandle(){}

    @Override
    protected void channelRead0(
            @NotNull ChannelHandlerContext ctx,
            @NotNull PingPacket msg) throws Exception {
        // 直接写入PingPacket信息
        ctx.writeAndFlush(msg);
    }
}
