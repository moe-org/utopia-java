// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PacketClassifier.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

import java.util.List;

import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.net.PackageTypeEnum;
import moe.kawayi.org.utopia.core.net.packet.PingPacket;
import moe.kawayi.org.utopia.core.ubf.converter.Parser;
import moe.kawayi.org.utopia.core.util.NotNull;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.FastThreadLocal;

/**
 * 包分类器。根据包id进行分类。
 * <p>
 * 我们已经使用{@link LengthFieldBasedFrameDecoder}来解码长度数据了。所以我们不需要再检查长度。
 * <p>
 * 线程安全的
 */
public final class PacketClassifier extends ByteToMessageDecoder {

    /**
     * 默认构造
     */
    public PacketClassifier() {}

    private static final Logger LOGGER = LogManagers.getLogger(PacketClassifier.class);

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

        // 读取type
        var packetType = in.readInt();

        // LengthFieldBasedFrameDecoder为我们准备好了完整的数据长度
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);

        // 分类数据
        if (packetType == PackageTypeEnum.PING.getTypeId()) {
            out.add(new PingPacket());
            LOGGER.debug("received ping type packet");
        } else if (packetType == PackageTypeEnum.COMMAND.getTypeId()) {
            // TODO
            // out.add(converter.get().convert(new DataInputStream(new ByteArrayInputStream(data))));
            LOGGER.debug("received command type packet");
        } else {
            // TODO
            LOGGER.debug("received unknown type packet");
        }
    }
}
