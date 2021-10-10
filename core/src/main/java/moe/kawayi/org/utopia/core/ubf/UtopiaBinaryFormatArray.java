//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatArray.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * UtopiaBinaryFormat数组
 */
public interface UtopiaBinaryFormatArray extends Iterable<UtopiaBinaryFormatValue> {

    /**
     * 获取数组长度
     *
     * @return 数组长度
     */
    int getLength();

    /**
     * 获取数组子对象
     *
     * @param index 索引
     * @return 获取到的数组对象
     */
    UtopiaBinaryFormatValue get(int index);

    /**
     * 设置索引所对的值
     *
     * @param index 索引
     * @param value 目标值
     */
    void set(int index, @NotNull UtopiaBinaryFormatValue value);

    /**
     * 删除索引
     *
     * @param index 要删除的索引
     */
    void remove(int index);

    /**
     * 添加一个值
     *
     * @param value 要添加的值
     */
    void add(@NotNull UtopiaBinaryFormatValue value);

    /**
     * 转换到数组
     *
     * @return 数组
     */
    UtopiaBinaryFormatValue[] asArray();
}
