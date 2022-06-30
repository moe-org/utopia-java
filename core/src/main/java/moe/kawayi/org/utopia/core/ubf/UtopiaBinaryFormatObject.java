// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatObject.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * UtopiaBinaryFormat对象。
 * 官方指定实现:{@link UtopiaBinaryFormatObjectImpl}。
 */
public sealed interface UtopiaBinaryFormatObject permits UtopiaBinaryFormatObjectImpl {

    /**
     * 获取对象长度
     *
     * @return 对象长度
     */
    int size();

    /**
     * 判断Object是否为空。
     *
     * @return 如果为空返回true，否则返回false。
     */
    boolean isEmpty();

    /**
     * 判断Object是否包含某个key
     *
     * @param key 要进行判断的key
     * @return 如果key存在则返回true，否则返回false
     */
    boolean containsKey(@NotNull String key);

    /**
     * 判断两个对象是否相等
     *
     * @param another 另一个要比较的对象
     * @return 如果相等返回true，否则false
     */
    boolean equal(@Nullable Object another);

    /**
     * 获取hash code
     *
     * @return hash code
     */
    int hashcode();

    /**
     * 清空对象
     */
    void clear();

    /**
     * 获取对象
     *
     * @param key 对象key
     * @return 获取到的对象。类型为UtopiaBinaryFormatType所包含的类型之一。如{@link String},{@link Long},{@link UtopiaBinaryFormatArray}等。
     * 如果对象不存在则返回empty。
     */
    @NotNull
    Optional<Object> get(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<Byte> getByte(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<Short> getShort(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<Integer> getInt(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<Long> getLong(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<String> getString(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<Boolean> getBoolean(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<Float> getFloat(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<Double> getDouble(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<UtopiaBinaryFormatArray> getArray(@NotNull String key);

    /**
     * 获取对象
     *
     * @param key 对象的键值
     * @return 对象，如果没有对应的对象或者类型不正确，返回empty。
     */
    @NotNull
    Optional<UtopiaBinaryFormatObject> getObject(@NotNull String key);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。类型为UtopiaBinaryFormatType所包含的类型之一。
     *              如{@link String},{@link Long},{@link UtopiaBinaryFormatArray}等。
     */
    void put(@NotNull String key, @NotNull Object value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, byte value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, short value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, int value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, long value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, float value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, double value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, boolean value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, @NotNull String value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, @NotNull UtopiaBinaryFormatArray value);

    /**
     * 放入对象。如果对象已经存在则会覆盖原有的。
     *
     * @param key   要放入对象的键值
     * @param value 要放入对象的值。
     */
    void put(@NotNull String key, @NotNull UtopiaBinaryFormatObject value);

    /**
     * 获取所有键值
     *
     * @return 键值的集合
     */
    @NotNull
    Set<String> getKeys();

    /**
     * 获取键值对
     *
     * @return 键值对
     */
    @NotNull
    Set<Map.Entry<String, Object>> getEntrySet();
}
