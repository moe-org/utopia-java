// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ClientInitHandle.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.net;

import moe.kawayi.org.utopia.core.net.PackageTypeEnum;
import moe.kawayi.org.utopia.core.util.NotNull;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static moe.kawayi.org.utopia.desktop.net.NetMain.CLIENT_CHANNEL;

/**
 * 客户端初始化句柄
 *
 * 会在链接的时候发送ping
 */
public class ClientInitHandle extends ChannelInboundHandlerAdapter {

    /**
     * 默认构造函数
     */
    public ClientInitHandle() {}

    @Override
    public void channelActive(@NotNull ChannelHandlerContext ctx) throws Exception {
        CLIENT_CHANNEL.set(ctx.channel());

        var buf = ctx.alloc().buffer(8);

        // ping
        // 写入长度和类型
        buf.writeInt(4);
        buf.writeInt(PackageTypeEnum.PING.getTypeId());

        ctx.writeAndFlush(buf);
    }
}
