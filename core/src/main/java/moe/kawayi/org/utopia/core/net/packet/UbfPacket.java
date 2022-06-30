// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UbfPacket.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.net.packet;

import moe.kawayi.org.utopia.core.net.PackageTypeEnum;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObject;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 使用Ubf的包。
 *
 * 应该适用于几乎所有网络传输。
 */
public interface UbfPacket {

    /**
     * 获取网络包的UBF对象
     * @return 对象
     */
    @NotNull
    UtopiaBinaryFormatObject getUtopiaBinaryFormatObject();

    /**
     * 获取包类型
     * @return 包类型的id。应该是{@link PackageTypeEnum#getTypeId()}中的值
     */
    int getPacketType();
}
