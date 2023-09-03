// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PackageTypeEnum.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.net;

/**
 * 包类型
 */
public enum PackageTypeEnum {
    /**
     * PING包，将回复服务器基本信息
     * <p>
     * 服务端将会忽略一切附带数据。返回一个服务器基本信息{@link moe.kawayi.org.utopia.core.net.packet.PingPacket}
     * <p>
     * 如果客户端接收到这个类型的包。将会解码附带的数据。并且不会回复。
     */
    PING(0),

    /**
     * COMMAND包，将执行服务器指令。
     * <p>
     * 如果客户端接收到这个类型的包。将不会回复。(但仍然会执行命令)
     */
    COMMAND(1);

    /**
     * 包类型id
     */
    private final int typeId;

    PackageTypeEnum(int id) {
        typeId = id;
    }

    /**
     * 获取typeId
     *
     * @return typeId
     */
    public int getTypeId() {
        return typeId;
    }
}
