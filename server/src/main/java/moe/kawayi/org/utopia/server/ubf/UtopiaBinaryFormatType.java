//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatType.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.ubf;

/**
 * UtopiaBinaryFormat类型
 */
public enum UtopiaBinaryFormatType {
    /**
     * byte
     */
    BYTE((byte) 0),

    /**
     * short
     */
    SHORT((byte) 1),

    /**
     * int
     */
    INT((byte) 2),

    /**
     * long
     */
    LONG((byte) 3),

    /**
     * float
     */
    FLOAT((byte) 4),

    /**
     * double
     */
    DOUBLE((byte) 5),

    /**
     * boolean
     */
    BOOLEAN((byte) 6),

    /**
     * string
     */
    STRING((byte) 7),

    /**
     * object
     */
    OBJECT((byte) 8),

    /**
     * array
     */
    ARRAY((byte) 9);

    private final byte markCode;

    UtopiaBinaryFormatType(byte markCode) {
        this.markCode = markCode;
    }

    /**
     * 获取标记二进制码
     *
     * @return 标记码
     */
    public byte getMarkCode() {
        return markCode;
    }
}
