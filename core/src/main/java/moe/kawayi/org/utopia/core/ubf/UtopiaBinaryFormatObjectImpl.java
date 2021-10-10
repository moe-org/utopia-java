//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatObjectImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf;

import java.util.*;

/**
 * {@link UtopiaBinaryFormatObject}的线程不安全实现
 */
public final class UtopiaBinaryFormatObjectImpl implements UtopiaBinaryFormatObject {

    private final HashMap<String, UtopiaBinaryFormatValue> keyValue;

    /**
     * 默认构造
     */
    public UtopiaBinaryFormatObjectImpl() {
        keyValue = new HashMap<>();
    }

    /**
     * 带map容量大小的构造
     *
     * @param preCapacity map预留容量大小; {@link HashMap#HashMap(int)}
     */
    public UtopiaBinaryFormatObjectImpl(int preCapacity) {
        keyValue = new HashMap<>(preCapacity);
    }

    @Override
    public int getLength() {
        return keyValue.size();
    }

    @Override
    public Optional<UtopiaBinaryFormatValue> get(String key) {
        Objects.requireNonNull(key, "key must not be null");

        return Optional.ofNullable(keyValue.get(key));
    }

    @Override
    public Set<String> getKeys() {
        return keyValue.keySet();
    }

    @Override
    public Set<Map.Entry<String, UtopiaBinaryFormatValue>> getEntrySet() {
        return keyValue.entrySet();
    }

    @Override
    public void put(String key, UtopiaBinaryFormatValue value) {
        Objects.requireNonNull(value, "value must not be null");
        Objects.requireNonNull(key, "key must not be null");

        keyValue.put(key, value);
    }
}
