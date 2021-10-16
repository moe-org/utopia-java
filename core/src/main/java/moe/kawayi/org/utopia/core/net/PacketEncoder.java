//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PacketEncoder.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.FastThreadLocal;
import moe.kawayi.org.utopia.core.ubf.converter.BinaryConverter;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.net.packet.UbfPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * 编码器。输出{@link UbfPacket}类型的完整包(包含长度)。
 *
 * 线程安全的
 */
@ChannelHandler.Sharable
public final class PacketEncoder extends MessageToByteEncoder<UbfPacket> {


    private final Logger logger = LogManager.getLogger(this.getClass());

    @NotNull
    private final FastThreadLocal<BinaryConverter.ConvertTo> converter = new FastThreadLocal<>(){
        @Override
        protected BinaryConverter.ConvertTo initialValue() throws Exception {
            return new BinaryConverter.ConvertTo();
        }
    };

    @Override
    protected void encode(ChannelHandlerContext ctx, UbfPacket msg, ByteBuf out) throws Exception {
        // 转换
        ByteArrayOutputStream outputBuf = new ByteArrayOutputStream(512);
        DataOutputStream output = new DataOutputStream(outputBuf);

        converter.get().convert(output,msg.geyUtopiaBinaryFormatObject());

        output.flush();

        // 检查和扩容
        if(out.writableBytes() < (output.size()+4)){
            out.capacity(out.writerIndex() + output.size() + 4);
        }

        // 写出
        out.writeInt(outputBuf.size());
        out.writeBytes(outputBuf.toByteArray());
    }
}
