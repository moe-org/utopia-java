//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatValueImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.ubf;

import moe.kawayi.org.utopia.server.util.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * {@link moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue}的线程不安全实现
 */
public final class UtopiaBinaryFormatValueImpl implements moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatValue {

    @NotNull
    private moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType type;

    @NotNull
    private Object value;

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(byte value) {
        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.BYTE;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(short value) {
        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.SHORT;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(int value) {
        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.INT;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(long value) {
        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.LONG;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(float value) {
        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.FLOAT;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(double value) {
        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.DOUBLE;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(boolean value) {
        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.BOOLEAN;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(@NotNull String value) {
        Objects.requireNonNull(value, "value must not be null");

        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.STRING;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(@NotNull moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatArray value) {
        Objects.requireNonNull(value, "value must not be null");

        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.ARRAY;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(@NotNull UtopiaBinaryFormatObject value) {
        Objects.requireNonNull(value, "value must not be null");

        this.value = value;
        type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.OBJECT;
    }

    @Override
    public moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType getType() {
        return type;
    }

    @Override
    public void setByte(byte value) {
        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.BYTE;
        this.value = value;
    }

    @Override
    public void setShort(short value) {
        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.SHORT;
        this.value = value;
    }

    @Override
    public void setInt(int value) {
        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.INT;
        this.value = value;
    }

    @Override
    public void setLong(long value) {
        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.LONG;
        this.value = value;
    }

    @Override
    public void setFloat(float value) {
        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.FLOAT;
        this.value = value;
    }

    @Override
    public void setDouble(double value) {
        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.DOUBLE;
        this.value = value;
    }

    @Override
    public void setBoolean(boolean value) {
        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.BOOLEAN;
        this.value = value;
    }

    @Override
    public void setString(@NotNull String value) {
        Objects.requireNonNull(value, "value must not be null");

        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.STRING;
        this.value = value;
    }

    @Override
    public void setArray(@NotNull moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatArray value) {
        Objects.requireNonNull(value, "value must not be null");

        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.ARRAY;
        this.value = value;
    }

    @Override
    public void setObject(@NotNull UtopiaBinaryFormatObject value) {
        Objects.requireNonNull(value, "value must not be null");

        this.type = moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.OBJECT;
        this.value = value;
    }

    @Override
    public Optional<Byte> getByte() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.BYTE)
            return Optional.of((byte) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Short> getShort() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.SHORT)
            return Optional.of((short) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Integer> getInt() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.INT)
            return Optional.of((int) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Long> getLong() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.LONG)
            return Optional.of((long) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Float> getFloat() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.FLOAT)
            return Optional.of((float) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Double> getDouble() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.DOUBLE)
            return Optional.of((double) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Boolean> getBoolean() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.BOOLEAN)
            return Optional.of((boolean) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<String> getString() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.STRING)
            return Optional.of((String) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatArray> getArray() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.ARRAY)
            return Optional.of((moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatArray) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<UtopiaBinaryFormatObject> getObject() {
        if (type == moe.kawayi.org.utopia.server.ubf.UtopiaBinaryFormatType.OBJECT)
            return Optional.of((UtopiaBinaryFormatObject) value);
        else
            return Optional.empty();
    }
}
