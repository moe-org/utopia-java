//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PingPacket.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net.packet;

import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObject;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObjectImpl;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatValueImpl;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.server.net.PackageTypeEnum;

/**
 * {@link PackageTypeEnum#PING}的包内容
 */
public final class PingPacket implements UbfPacket{
    private final String serverVersion = "";

    /**
     * 获取服务器版本号
     * @return  版本号
     */
    public String getServerVersion(){
        return serverVersion;
    }

    @NotNull
    @Override
    public UtopiaBinaryFormatObject geyUtopiaBinaryFormatObject() {
        UtopiaBinaryFormatObject obj = new UtopiaBinaryFormatObjectImpl();

        obj.put("version",new UtopiaBinaryFormatValueImpl(serverVersion));

        return obj;
    }
}
