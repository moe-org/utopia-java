//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatObjectImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.util.*;

/**
 * {@link UtopiaBinaryFormatObject}的线程不安全实现
 */
public final class UtopiaBinaryFormatObjectImpl implements UtopiaBinaryFormatObject {

    private final HashMap<String, Object> map;

    /**
     * 默认构造参数
     */
    public UtopiaBinaryFormatObjectImpl() {
        map = new HashMap<>();
    }

    /**
     * 构造一个容量不为0的对象
     *
     * @param capital 容量
     */
    public UtopiaBinaryFormatObjectImpl(int capital) {
        map = new HashMap<>(capital);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(@NotNull String key) {
        Objects.requireNonNull(key);
        return map.containsKey(key);
    }

    @Override
    public boolean equal(@Nullable Object another) {
        if (another instanceof UtopiaBinaryFormatObjectImpl impl) {
            return impl.map.equals(this.map);
        } else if (another instanceof UtopiaBinaryFormatObject) {
            // 对于其他的UtopiaBinaryFormatObject实现
            // 我们暂时不实现如何比较 (UtopiaBinaryFormatObjectImpl应该是官方钦定的唯一实现)
            // 所以抛出一个异常来提醒用户
            throw new IllegalArgumentException(
                    "not supported comparing operator for other UtopiaBinaryFormatObject impl");
        } else {
            return false;
        }
    }

    @Override
    public int hashcode() {
        return map.hashCode();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    @NotNull
    public Optional<Object> get(@NotNull String key) {
        Objects.requireNonNull(key);

        return Optional.ofNullable(map.get(key));
    }

    @Override
    @NotNull
    public Optional<Byte> getByte(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof Byte v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Short> getShort(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof Short v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Integer> getInt(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof Integer v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Long> getLong(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof Long v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<String> getString(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof String v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Boolean> getBoolean(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof Boolean v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Float> getFloat(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof Float v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Double> getDouble(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof Double v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<UtopiaBinaryFormatArray> getArray(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof UtopiaBinaryFormatArray v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<UtopiaBinaryFormatObject> getObject(@NotNull String key) {
        Objects.requireNonNull(key);

        var got = map.get(key);

        if (got instanceof UtopiaBinaryFormatObject v) {
            return Optional.of(v);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void put(@NotNull String key, @NotNull Object value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        if (value instanceof Byte v) {
            put(key, (byte) v);
        } else if (value instanceof Short v) {
            put(key, (short) v);
        } else if (value instanceof Integer v) {
            put(key, (int) v);
        } else if (value instanceof Long v) {
            put(key, (long) v);
        } else if (value instanceof String v) {
            put(key, v);
        } else if (value instanceof Boolean v) {
            put(key, (boolean) v);
        } else if (value instanceof Float v) {
            put(key, (float) v);
        } else if (value instanceof Double v) {
            put(key, (double) v);
        } else if (value instanceof UtopiaBinaryFormatArray v) {
            put(key, v);
        } else if (value instanceof UtopiaBinaryFormatObject v) {
            put(key, v);
        } else {
            throw new IllegalArgumentException("unknown argument type");
        }
    }

    @Override
    public void put(@NotNull String key, byte value) {
        Objects.requireNonNull(key);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, short value) {
        Objects.requireNonNull(key);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, int value) {
        Objects.requireNonNull(key);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, long value) {
        Objects.requireNonNull(key);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, float value) {
        Objects.requireNonNull(key);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, double value) {
        Objects.requireNonNull(key);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, boolean value) {
        Objects.requireNonNull(key);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, @NotNull String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, @NotNull UtopiaBinaryFormatArray value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        map.put(key, value);
    }

    @Override
    public void put(@NotNull String key, @NotNull UtopiaBinaryFormatObject value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        map.put(key, value);
    }

    @Override
    @NotNull
    public Set<String> getKeys() {
        return map.keySet();
    }

    @Override
    @NotNull
    public Set<Map.Entry<String, Object>> getEntrySet() {
        return map.entrySet();
    }


}
