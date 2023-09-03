// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PacketClassifier.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.net.PackageTypeEnum;
import moe.kawayi.org.utopia.core.net.packet.PingPacket;
import moe.kawayi.org.utopia.core.ubf.converter.Parser;
import moe.kawayi.org.utopia.core.util.NotNull;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.FastThreadLocal;

/**
 * 包分类器。根据包id进行分类。
 * <p>
 * 我们已经使用{@link LengthFieldBasedFrameDecoder}来解码长度数据了。所以我们不需要再检查长度。
 * <p>
 * 线程安全的
 */
public class PacketClassifier extends ByteToMessageDecoder {

    /**
     * 默认构造
     */
    public PacketClassifier() {}

    /**
     * 获取服务器版本号的key的netty attr
     */
    public static final String CHANNEL_SERVER_PING_VERSION = "utopia.client.received_ping_packet.server_version";

    private final Logger logger = GlobalLogManager.getLogger(this.getClass());

    @NotNull
    private final FastThreadLocal<Parser> converter = new FastThreadLocal<>() {
        @Override
        @NotNull
        protected Parser initialValue() throws Exception {
            return new Parser();
        }
    };

    @Override
    protected void decode(@NotNull ChannelHandlerContext ctx, @NotNull ByteBuf in, @NotNull List<Object> out)
            throws Exception {

        // 读取数据类型
        var packetType = in.readInt();

        // LengthFieldBasedFrameDecoder为我们准备好了完整的数据长度
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);

        // 分类数据
        if (packetType == PackageTypeEnum.PING.getTypeId()) {
            try (var byteArrayInputStream = new ByteArrayInputStream(data)) {
                try (var dataInputStream = new DataInputStream(byteArrayInputStream)) {
                    var nbt = converter.get().parse(dataInputStream);

                    var attr = ctx.channel().attr(AttributeKey.valueOf(CHANNEL_SERVER_PING_VERSION));

                    attr.set(nbt.getString(PingPacket.UBF_VERSION_KEY).orElseThrow());
                }
            }
        } else if (packetType == PackageTypeEnum.COMMAND.getTypeId()) {
            logger.debug("received command type packet");
        } else {
            logger.debug("received unknown type packet");
        }
    }
}
