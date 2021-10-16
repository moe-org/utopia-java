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
import moe.kawayi.org.utopia.core.net.PackageTypeEnum;
import moe.kawayi.org.utopia.core.util.UtopiaVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * {@link PackageTypeEnum#PING}的包内容
 */
public final class PingPacket implements UbfPacket{

    private static final Logger LOGGER = LogManager.getLogger(PingPacket.class);

    @NotNull
    @Override
    public UtopiaBinaryFormatObject geyUtopiaBinaryFormatObject(){
        UtopiaBinaryFormatObject obj = new UtopiaBinaryFormatObjectImpl();

        try {
            obj.put("version", new UtopiaBinaryFormatValueImpl(UtopiaVersion.getUtopiaVersion()));
        }
        catch(IOException err){
            LOGGER.error("get utopia version failed down",err);
        }

        return obj;
    }
}
