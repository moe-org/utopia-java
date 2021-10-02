//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatArrayImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.ubf;

import moe.kawayi.org.utopia.server.util.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * {@link moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatArray}的线程不安全实现
 */
public final class UtopiaBinaryFormatArrayImpl implements moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatArray {

    private final ArrayList<moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue> array;

    /**
     * 默认构造
     */
    public UtopiaBinaryFormatArrayImpl() {
        array = new ArrayList<>();
    }

    /**
     * 带数组容量大小的构造
     *
     * @param preCapacity 数组预留容量大小; {@link ArrayList#ArrayList(int)}
     */
    public UtopiaBinaryFormatArrayImpl(int preCapacity) {
        array = new ArrayList<>(preCapacity);
    }

    @Override
    public int getLength() {
        return array.size();
    }

    @Override
    public moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue get(int index) {
        return array.get(index);
    }

    @Override
    public void set(int index, moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue value) {
        Objects.requireNonNull(value, "value must not be null");

        array.set(index, value);
    }

    @Override
    public void remove(int index) {
        array.remove(index);
    }

    @Override
    public void add(@NotNull moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue value) {
        Objects.requireNonNull(value, "value must not be null");

        array.add(value);
    }

    @Override
    public moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue[] asArray() {
        var array = new moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue[this.array.size()];

        this.array.toArray(array);

        return array;
    }

    @Override
    public Iterator<moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue> iterator() {
        return array.iterator();
    }
}
