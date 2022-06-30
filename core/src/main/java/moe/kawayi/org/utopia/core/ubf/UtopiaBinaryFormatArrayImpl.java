// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatArrayImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

/**
 * {@link UtopiaBinaryFormatArray}的线程不安全实现
 */
public final class UtopiaBinaryFormatArrayImpl implements UtopiaBinaryFormatArray {

    private final ArrayList<Object> items;

    /**
     * 默认构造函数
     */
    public UtopiaBinaryFormatArrayImpl() {
        items = new ArrayList<>();
    }

    /**
     * 构造一个容量不为0的数组
     *
     * @param capital 容量
     */
    public UtopiaBinaryFormatArrayImpl(int capital) {
        items = new ArrayList<>(capital);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean equal(@Nullable Object another) {
        if (another instanceof UtopiaBinaryFormatArrayImpl impl) {
            return impl.items.equals(this.items);
        } else if (another instanceof UtopiaBinaryFormatArray) {
            // 对于其他的UtopiaBinaryFormatArray实现
            // 我们暂时不实现如何比较 (UtopiaBinaryFormatArrayImpl应该是官方钦定的唯一实现)
            // 所以抛出一个异常来提醒用户
            throw new IllegalArgumentException(
                    "not supported comparing operator for other UtopiaBinaryFormatArray impl");
        } else {
            return false;
        }
    }

    @Override
    public int hashcode() {
        return items.hashCode();
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    @NotNull
    public Optional<Object> get(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        return Optional.of(items.get(index));
    }

    @Override
    @NotNull
    public Optional<Byte> getByte(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof Byte b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Short> getShort(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof Short b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Integer> getInt(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof Integer b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Long> getLong(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof Long b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Float> getFloat(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof Float b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Double> getDouble(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof Double b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<String> getString(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof String b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<Boolean> getBoolean(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof Boolean b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<UtopiaBinaryFormatArray> getArray(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof UtopiaBinaryFormatArray b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @NotNull
    public Optional<UtopiaBinaryFormatObject> getObject(int index) {
        if (index > items.size()) {
            return Optional.empty();
        }

        var got = items.get(index);

        if (got instanceof UtopiaBinaryFormatObject b) {
            return Optional.of(b);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void set(int index, @NotNull Object value) {
        Objects.requireNonNull(value);

        if (value instanceof Byte v) {
            set(index, (byte) v);
        } else if (value instanceof Short v) {
            set(index, (short) v);
        } else if (value instanceof Integer v) {
            set(index, (int) v);
        } else if (value instanceof Long v) {
            set(index, (long) v);
        } else if (value instanceof String v) {
            set(index, v);
        } else if (value instanceof Boolean v) {
            set(index, (boolean) v);
        } else if (value instanceof Float v) {
            set(index, (float) v);
        } else if (value instanceof Double v) {
            set(index, (double) v);
        } else if (value instanceof UtopiaBinaryFormatArray v) {
            set(index, v);
        } else if (value instanceof UtopiaBinaryFormatObject v) {
            set(index, v);
        } else {
            throw new IllegalArgumentException("unknown argument type");
        }
    }

    @Override
    public void set(int index, byte value) {
        items.set(index, value);
    }

    @Override
    public void set(int index, short value) {
        items.set(index, value);
    }

    @Override
    public void set(int index, int value) {
        items.set(index, value);
    }

    @Override
    public void set(int index, long value) {
        items.set(index, value);
    }

    @Override
    public void set(int index, float value) {
        items.set(index, value);
    }

    @Override
    public void set(int index, double value) {
        items.set(index, value);
    }

    @Override
    public void set(int index, boolean value) {
        items.set(index, value);
    }

    @Override
    public void set(int index, @NotNull String value) {
        Objects.requireNonNull(value);

        items.set(index, value);
    }

    @Override
    public void set(int index, @NotNull UtopiaBinaryFormatArray value) {
        Objects.requireNonNull(value);

        items.set(index, value);
    }

    @Override
    public void set(int index, @NotNull UtopiaBinaryFormatObject value) {
        Objects.requireNonNull(value);

        items.set(index, value);
    }

    @Override
    public void add(@NotNull Object value) {
        Objects.requireNonNull(value);

        if (value instanceof Byte v) {
            add(v);
        } else if (value instanceof Short v) {
            add((short) v);
        } else if (value instanceof Integer v) {
            add((int) v);
        } else if (value instanceof Long v) {
            add((long) v);
        } else if (value instanceof String v) {
            add(v);
        } else if (value instanceof Boolean v) {
            add((boolean) v);
        } else if (value instanceof Float v) {
            add((float) v);
        } else if (value instanceof Double v) {
            add((double) v);
        } else if (value instanceof UtopiaBinaryFormatArray v) {
            add(v);
        } else if (value instanceof UtopiaBinaryFormatObject v) {
            add(v);
        } else {
            throw new IllegalArgumentException("unknown argument type");
        }
    }

    @Override
    public void add(byte value) {
        items.add(value);
    }

    @Override
    public void add(short value) {
        items.add(value);
    }

    @Override
    public void add(int value) {
        items.add(value);
    }

    @Override
    public void add(long value) {
        items.add(value);
    }

    @Override
    public void add(float value) {
        items.add(value);
    }

    @Override
    public void add(double value) {
        items.add(value);
    }

    @Override
    public void add(boolean value) {
        items.add(value);
    }

    @Override
    public void add(@NotNull String value) {
        Objects.requireNonNull(value);

        items.add(value);
    }

    @Override
    public void add(@NotNull UtopiaBinaryFormatArray value) {
        Objects.requireNonNull(value);

        items.add(value);
    }

    @Override
    public void add(@NotNull UtopiaBinaryFormatObject value) {
        Objects.requireNonNull(value);

        items.add(value);
    }

    @Override
    public void remove(int index) {
        items.remove(index);
    }

    @Override
    @NotNull
    public Object[] toArray() {
        return items.toArray();
    }

    @Override
    @NotNull
    public Iterator<Object> iterator() {
        return items.iterator();
    }
}
