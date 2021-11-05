//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatObject.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * UtopiaBinaryFormat对象
 */
public interface UtopiaBinaryFormatObject {

    /**
     * 获取对象长度
     *
     * @return 对象长度
     */
    int getLength();

    /**
     * 获取对象
     *
     * @param key 对象key
     * @return 获取到的对象
     */
    @NotNull
    Optional<UtopiaBinaryFormatValue> get(@NotNull String key);

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
    Set<Map.Entry<String, UtopiaBinaryFormatValue>> getEntrySet();

    /**
     * 添加键值对
     *
     * @param key   键
     * @param value 值
     */
    void put(@NotNull String key, @NotNull UtopiaBinaryFormatValue value);
}
