// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PacketEncoderTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.net;

import moe.kawayi.org.utopia.core.net.PackageTypeEnum;
import moe.kawayi.org.utopia.core.net.PacketEncoder;
import moe.kawayi.org.utopia.core.net.packet.PingPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PacketEncoderTest {

    @Test
    public void encoderTest() {
        // 写入
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new PacketEncoder());

        Assertions.assertTrue(embeddedChannel.writeOutbound(new PingPacket()));

        Assertions.assertTrue(embeddedChannel.finish());

        // 检查
        ByteBuf readOutbound = embeddedChannel.readOutbound();

        Assertions.assertTrue(readOutbound.readableBytes() > 8);

        var length = readOutbound.readInt();
        Assertions.assertEquals(length, readOutbound.readableBytes());

        var type = readOutbound.readInt();
        Assertions.assertEquals(type, PackageTypeEnum.PING.getTypeId());

        var data = new byte[readOutbound.readableBytes()];
        readOutbound.readBytes(data);

        Assertions.assertFalse(readOutbound.isReadable());
    }
}
