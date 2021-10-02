//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PackageTypeEnum.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

/**
 * 包类型
 */
public enum PackageTypeEnum {
    /**
     * PING包，将回复服务器基本信息
     */
    PING(0),

    /**
     * COMMAND包，将执行服务器指令
     */
    COMMAND(1),

    /**
     * 心跳包
     */
    ALIVE(2);


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
