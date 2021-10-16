//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ClientInitHandle.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import moe.kawayi.org.utopia.core.net.PackageTypeEnum;

import static moe.kawayi.org.utopia.client.net.NetMain.CLIENT_CHANNEL;

/**
 * 客户端初始化句柄
 *
 * 会在链接的时候发送ping
 */
public class ClientInitHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CLIENT_CHANNEL.set(ctx.channel());
        System.out.println("linked");

        var buf = ctx.alloc().buffer(512);

        // ping
        buf.writeInt(0);
        buf.writeInt(PackageTypeEnum.COMMAND.getTypeId());

        ctx.channel().writeAndFlush(buf);
    }
}
