// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PingPacket.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.net.packet;

import java.io.IOException;

import moe.kawayi.org.utopia.core.log.GlobalLogManager;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.net.PackageTypeEnum;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObject;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObjectImpl;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Version;

/**
 * {@link PackageTypeEnum#PING}的包内容
 */
public final class PingPacket implements UbfPacket {

    /**
     * 默认构造
     */
    public PingPacket() {}

    /**
     * 日志器
     */
    private static final Logger LOGGER = GlobalLogManager.getLogger(PingPacket.class);

    /**
     * 获取version的UtopiaBinaryFormat键值
     */
    public static final String UBF_VERSION_KEY = "version";

    @NotNull
    @Override
    public UtopiaBinaryFormatObject getUtopiaBinaryFormatObject() {
        UtopiaBinaryFormatObject obj = new UtopiaBinaryFormatObjectImpl();

        try {
            obj.put(UBF_VERSION_KEY, Version.getUtopiaVersion());
        } catch (@NotNull IOException err) {
            LOGGER.error("get utopia version failed down", err);
        }

        return obj;
    }

    @Override
    public int getPacketType() {
        return PackageTypeEnum.PING.getTypeId();
    }
}
