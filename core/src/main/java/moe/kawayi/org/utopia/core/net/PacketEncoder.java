// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PacketEncoder.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import moe.kawayi.org.utopia.core.net.packet.UbfPacket;
import moe.kawayi.org.utopia.core.ubf.converter.Writer;
import moe.kawayi.org.utopia.core.util.NotNull;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.FastThreadLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 编码器。输出{@link UbfPacket}类型的完整包(包含长度)。
 * <p>
 * 线程安全的
 */
public final class PacketEncoder extends MessageToByteEncoder<UbfPacket> {

    /**
     * 构造一个解码器
     */
    public PacketEncoder() {
        super(UbfPacket.class, true);
    }

    private final Logger logger = LogManager.getLogger(this.getClass());

    @NotNull
    private final FastThreadLocal<Writer> converter = new FastThreadLocal<>() {
        @Override
        @NotNull
        protected Writer initialValue() throws Exception {
            return new Writer();
        }
    };

    @Override
    @NotNull
    protected void encode(@NotNull ChannelHandlerContext ctx, @NotNull UbfPacket msg, @NotNull ByteBuf out)
            throws Exception {
        // 转换
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512)) {

            try (DataOutputStream outputStream = new DataOutputStream(byteArrayOutputStream)) {
                converter.get().write(msg.getUtopiaBinaryFormatObject(), outputStream);
                outputStream.flush();
                byteArrayOutputStream.flush();
            }

            // 检查和扩容
            if (out.writableBytes() < (byteArrayOutputStream.size() + 8)) {
                out.capacity(out.writerIndex() + byteArrayOutputStream.size() + 8);
            }

            // 写出
            // 包长度
            // 包类型
            // 包数据
            out.writeInt(byteArrayOutputStream.size() + 4);
            out.writeInt(msg.getPacketType());
            out.writeBytes(byteArrayOutputStream.toByteArray());
        }
    }
}
