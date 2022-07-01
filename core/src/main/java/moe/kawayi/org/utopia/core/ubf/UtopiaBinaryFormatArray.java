// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatArray.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf;

import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * UtopiaBinaryFormat数组
 * 官方指定实现:{@link UtopiaBinaryFormatArrayImpl}。
 */
public sealed interface UtopiaBinaryFormatArray extends Iterable<Object> permits UtopiaBinaryFormatArrayImpl {

    /**
     * 获取数组长度
     *
     * @return 数组长度
     */
    int size();

    /**
     * 判断Array是否为空。
     *
     * @return 如果为空返回true，否则返回false。
     */
    boolean isEmpty();

    /**
     * 判断两个对象是否相等
     *
     * @param another 另一个要比较的对象
     * @return 如果相等返回true，否则false
     */
    boolean equals(@Nullable Object another);

    /**
     * 获取hash code
     *
     * @return hash code
     */
    int hashCode();

    /**
     * 清空数组
     */
    void clear();

    /**
     * 获取数组子对象
     *
     * @param index 索引
     * @return 获取到的数组对象。类型为UtopiaBinaryFormatType所包含的类型之一。如{@link String},{@link Long},{@link UtopiaBinaryFormatArray}等。
     */
    @NotNull
    Optional<Object> get(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<Byte> getByte(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<Short> getShort(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<Integer> getInt(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<Long> getLong(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<Float> getFloat(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<Double> getDouble(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<String> getString(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<Boolean> getBoolean(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<UtopiaBinaryFormatArray> getArray(int index);

    /**
     * 获取数组对象
     *
     * @param index 要获取的对象的索引
     * @return 获取到的对象，如果对象类型不正确会返回empty
     */
    @NotNull
    Optional<UtopiaBinaryFormatObject> getObject(int index);

    /**
     * 设置数组对象
     *
     * @param index 索引
     * @param value 要设置的对象的值，类型为UtopiaBinaryFormatType所包含的类型之一。
     *              如{@link String},{@link Long},{@link UtopiaBinaryFormatArray}等。
     */
    void set(int index, @NotNull Object value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, byte value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, short value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, int value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, long value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, float value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, double value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, boolean value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, @NotNull String value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, @NotNull UtopiaBinaryFormatArray value);

    /**
     * 设置数组对象，索引必须已经存在
     *
     * @param index 数组的索引
     * @param value 要设置成的对象
     */
    void set(int index, @NotNull UtopiaBinaryFormatObject value);

    /**
     * 追加数组对象
     *
     * @param value 要追加的对象的值，类型为UtopiaBinaryFormatType所包含的类型之一。
     *              如{@link String},{@link Long},{@link UtopiaBinaryFormatArray}等。
     */
    void add(@NotNull Object value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(byte value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(short value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(int value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(long value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(float value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(double value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(boolean value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(@NotNull String value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(@NotNull UtopiaBinaryFormatArray value);

    /**
     * 追加数组对象
     *
     * @param value 对象的值
     */
    void add(@NotNull UtopiaBinaryFormatObject value);

    /**
     * 删除对象
     *
     * @param index 要删除的对象的索引
     */
    void remove(int index);

    /**
     * 转换到数组
     *
     * @return 数组
     */
    @NotNull
    Object[] toArray();
}
