//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatType.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf;

import moe.kawayi.org.utopia.core.util.NotNull;

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

    /**
     * 根据标识码获取{@link UtopiaBinaryFormatType}
     *
     * @param markCode 标志码
     * @return 获取到的UtopiaBinaryFormatType
     * @throws IllegalArgumentException 如果标识码错误
     */
    @NotNull
    public static UtopiaBinaryFormatType valueOf(byte markCode) {
        final byte b = UtopiaBinaryFormatType.BYTE.getMarkCode();
        final byte s = UtopiaBinaryFormatType.SHORT.getMarkCode();
        final byte i = UtopiaBinaryFormatType.INT.getMarkCode();
        final byte l = UtopiaBinaryFormatType.LONG.getMarkCode();
        final byte f = UtopiaBinaryFormatType.FLOAT.getMarkCode();
        final byte d = UtopiaBinaryFormatType.DOUBLE.getMarkCode();
        final byte str = UtopiaBinaryFormatType.STRING.getMarkCode();
        final byte bool = UtopiaBinaryFormatType.BOOLEAN.getMarkCode();
        final byte array = UtopiaBinaryFormatType.ARRAY.getMarkCode();
        final byte object = UtopiaBinaryFormatType.OBJECT.getMarkCode();

        if (markCode == b) {
            return UtopiaBinaryFormatType.BYTE;
        } else if (markCode == s) {
            return UtopiaBinaryFormatType.SHORT;
        } else if (markCode == i) {
            return UtopiaBinaryFormatType.INT;
        } else if (markCode == l) {
            return UtopiaBinaryFormatType.LONG;
        } else if (markCode == f) {
            return UtopiaBinaryFormatType.FLOAT;
        } else if (markCode == d) {
            return UtopiaBinaryFormatType.DOUBLE;
        } else if (markCode == str) {
            return UtopiaBinaryFormatType.STRING;
        } else if (markCode == bool) {
            return UtopiaBinaryFormatType.BOOLEAN;
        } else if (markCode == array) {
            return UtopiaBinaryFormatType.ARRAY;
        } else if (markCode == object) {
            return UtopiaBinaryFormatType.OBJECT;
        } else {
            throw new IllegalArgumentException("unknown mark code");
        }
    }
}
