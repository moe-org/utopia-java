//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UbfPacket.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net.packet;

import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObject;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 使用Ubf的包。
 *
 * 应该适用于大部分网络传输。
 */
public interface UbfPacket {

    /**
     * 获取网络包的UBF对象
     * @return 对象
     */
    @NotNull
    UtopiaBinaryFormatObject geyUtopiaBinaryFormatObject();

}
