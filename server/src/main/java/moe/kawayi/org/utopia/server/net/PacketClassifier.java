//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PacketClassifier.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.FastThreadLocal;
import moe.kawayi.org.utopia.core.ubf.converter.BinaryConverter;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.server.net.packet.PingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

/**
 * 包分类器。根据包id进行分类。
 *
 * 我们已经使用{@link LengthFieldBasedFrameDecoder}来解码长度数据了。所以我们不需要再检查长度。
 *
 * 线程安全的
 */
@ChannelHandler.Sharable
public final class PacketClassifier extends ByteToMessageDecoder {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @NotNull
    private final FastThreadLocal<BinaryConverter.ConvertFrom> converter = new FastThreadLocal<>(){
        @Override
        protected BinaryConverter.ConvertFrom initialValue() throws Exception {
            return new BinaryConverter.ConvertFrom();
        }
    };


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 读取数据
        var packetType = in.readInt();

        // LengthFieldBasedFrameDecoder为我们准备好了完整的数据长度
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);

        // 分类数据
        if(packetType == PackageTypeEnum.PING.getTypeId()){
            out.add(new PingPacket());
        } else if(packetType == PackageTypeEnum.COMMAND.getTypeId()){
            // TODO
            // out.add(converter.get().convert(new DataInputStream(new ByteArrayInputStream(data))));
        }
        else{
            // TODO
        }
    }
}
