//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PackageDecoder.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.FastThreadLocal;
import moe.kawayi.org.utopia.core.ubf.converter.BinaryConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 解包器
 */
public final class PackageDecoder extends ByteToMessageDecoder {

    /**
     * 日志器
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 每个线程一个mapper
     */
    private final FastThreadLocal<BinaryConverter.ConvertFrom> ubfReader = new FastThreadLocal<>() {
        @Override
        protected BinaryConverter.ConvertFrom initialValue() {
            return new BinaryConverter.ConvertFrom();
        }
    };

    /**
     * 保存长度上下文
     */
    private final ConcurrentHashMap<ChannelHandlerContext, Integer> ctxWithLength = new ConcurrentHashMap<>();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // channelHandlerContext已经存在
        if (ctxWithLength.containsKey(channelHandlerContext)) {

            int length = ctxWithLength.get(channelHandlerContext);

            // 读取数据
            if (byteBuf.readableBytes() >= length) {
                // copy
                byte[] data = new byte[length];

                byteBuf.getBytes(byteBuf.readerIndex(), data);

                // output
                var memoryStream = new ByteArrayInputStream(data);
                var dataStream = new DataInputStream(memoryStream);

                try (dataStream; memoryStream) {
                    list.add(ubfReader.get().convert(dataStream));
                }
            }
        }


        // channelHandlerContext未存在
        else if (byteBuf.readableBytes() > 4) {
            // 读取数据长度
            int length = byteBuf.readInt();

            // 设置容量
            if (byteBuf.capacity() < length)
                byteBuf.capacity(length);

            // 添加到has
            ctxWithLength.put(channelHandlerContext, length);

            channelHandlerContext.close().addListener(
                    (ChannelFutureListener) channelFuture -> ctxWithLength.remove(channelHandlerContext));


            channelHandlerContext.executor().schedule(
                    (Runnable) channelHandlerContext::close, 10, TimeUnit.SECONDS);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("网络错误:", cause);
        ctx.fireExceptionCaught(cause);
    }
}
