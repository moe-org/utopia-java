//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatValueImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.ubf;

import moe.kawayi.org.utopia.client.util.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * {@link UtopiaBinaryFormatValue}的线程不安全实现
 */
public class UtopiaBinaryFormatValueImpl implements UtopiaBinaryFormatValue {

    @NotNull
    private UtopiaBinaryFormatType type;

    @NotNull
    private Object value;

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(byte value) {
        this.value = value;
        type = UtopiaBinaryFormatType.BYTE;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(short value) {
        this.value = value;
        type = UtopiaBinaryFormatType.SHORT;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(int value) {
        this.value = value;
        type = UtopiaBinaryFormatType.INT;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(long value) {
        this.value = value;
        type = UtopiaBinaryFormatType.LONG;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(float value) {
        this.value = value;
        type = UtopiaBinaryFormatType.FLOAT;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(double value) {
        this.value = value;
        type = UtopiaBinaryFormatType.DOUBLE;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(boolean value) {
        this.value = value;
        type = UtopiaBinaryFormatType.BOOLEAN;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(@NotNull String value) {
        Objects.requireNonNull(value, "value must not be null");

        this.value = value;
        type = UtopiaBinaryFormatType.STRING;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(@NotNull UtopiaBinaryFormatArray value) {
        Objects.requireNonNull(value, "value must not be null");

        this.value = value;
        type = UtopiaBinaryFormatType.ARRAY;
    }

    /**
     * 初始化一个值
     *
     * @param value 值
     */
    public UtopiaBinaryFormatValueImpl(@NotNull UtopiaBinaryFormatObject value) {
        Objects.requireNonNull(value, "value must not be null");

        this.value = value;
        type = UtopiaBinaryFormatType.OBJECT;
    }

    @Override
    public UtopiaBinaryFormatType getType() {
        return type;
    }

    @Override
    public void setByte(byte value) {
        this.type = UtopiaBinaryFormatType.BYTE;
        this.value = value;
    }

    @Override
    public void setShort(short value) {
        this.type = UtopiaBinaryFormatType.SHORT;
        this.value = value;
    }

    @Override
    public void setInt(int value) {
        this.type = UtopiaBinaryFormatType.INT;
        this.value = value;
    }

    @Override
    public void setLong(long value) {
        this.type = UtopiaBinaryFormatType.LONG;
        this.value = value;
    }

    @Override
    public void setFloat(float value) {
        this.type = UtopiaBinaryFormatType.FLOAT;
        this.value = value;
    }

    @Override
    public void setDouble(double value) {
        this.type = UtopiaBinaryFormatType.DOUBLE;
        this.value = value;
    }

    @Override
    public void setBoolean(boolean value) {
        this.type = UtopiaBinaryFormatType.BOOLEAN;
        this.value = value;
    }

    @Override
    public void setString(@NotNull String value) {
        Objects.requireNonNull(value, "value must not be null");

        this.type = UtopiaBinaryFormatType.STRING;
        this.value = value;
    }

    @Override
    public void setArray(@NotNull UtopiaBinaryFormatArray value) {
        Objects.requireNonNull(value, "value must not be null");

        this.type = UtopiaBinaryFormatType.ARRAY;
        this.value = value;
    }

    @Override
    public void setObject(@NotNull UtopiaBinaryFormatObject value) {
        Objects.requireNonNull(value, "value must not be null");

        this.type = UtopiaBinaryFormatType.OBJECT;
        this.value = value;
    }

    @Override
    public Optional<Byte> getByte() {
        if (type == UtopiaBinaryFormatType.BYTE)
            return Optional.of((byte) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Short> getShort() {
        if (type == UtopiaBinaryFormatType.SHORT)
            return Optional.of((short) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Integer> getInt() {
        if (type == UtopiaBinaryFormatType.INT)
            return Optional.of((int) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Long> getLong() {
        if (type == UtopiaBinaryFormatType.LONG)
            return Optional.of((long) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Float> getFloat() {
        if (type == UtopiaBinaryFormatType.FLOAT)
            return Optional.of((float) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Double> getDouble() {
        if (type == UtopiaBinaryFormatType.DOUBLE)
            return Optional.of((double) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Boolean> getBoolean() {
        if (type == UtopiaBinaryFormatType.BOOLEAN)
            return Optional.of((boolean) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<String> getString() {
        if (type == UtopiaBinaryFormatType.STRING)
            return Optional.of((String) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<UtopiaBinaryFormatArray> getArray() {
        if (type == UtopiaBinaryFormatType.ARRAY)
            return Optional.of((UtopiaBinaryFormatArray) value);
        else
            return Optional.empty();
    }

    @Override
    public Optional<UtopiaBinaryFormatObject> getObject() {
        if (type == UtopiaBinaryFormatType.OBJECT)
            return Optional.of((UtopiaBinaryFormatObject) value);
        else
            return Optional.empty();
    }
}
