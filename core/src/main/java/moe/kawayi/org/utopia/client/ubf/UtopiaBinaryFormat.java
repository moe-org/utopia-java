//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormat.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.ubf;

/**
 * UtopiaBinaryFormat一些其他信息
 * <p>
 * 供转换器和使用者参考
 */
public final class UtopiaBinaryFormat {
    /**
     * 最大object/array层数;
     * <p>
     * 每遇见array/object就+1。离开array/object就-1。
     */
    public static final int MAX_STACK = 128;
}
