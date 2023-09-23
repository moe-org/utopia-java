// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Writer.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf.converter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormat;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatArray;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObject;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatType;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * {@link moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObject}的写入器
 */
public final class Writer {

    /**
     * 默认构造函数
     */
    public Writer() {}

    private int callStack = 0;

    private void call() {
        callStack++;

        if (callStack > UtopiaBinaryFormat.MAX_STACK) {
            throw new IllegalStateException("stack limit exceeded");
        }
    }

    private void ret() {
        callStack--;
    }

    private void writeType(@NotNull UtopiaBinaryFormatType typed, @NotNull DataOutputStream outputStream)
            throws IOException {
        switch (typed) {
            case BYTE -> outputStream.writeByte(UtopiaBinaryFormatType.BYTE.getMarkCode());
            case SHORT -> outputStream.writeByte(UtopiaBinaryFormatType.SHORT.getMarkCode());
            case INT -> outputStream.writeByte(UtopiaBinaryFormatType.INT.getMarkCode());
            case LONG -> outputStream.writeByte(UtopiaBinaryFormatType.LONG.getMarkCode());
            case FLOAT -> outputStream.writeByte(UtopiaBinaryFormatType.FLOAT.getMarkCode());
            case DOUBLE -> outputStream.writeByte(UtopiaBinaryFormatType.DOUBLE.getMarkCode());
            case STRING -> outputStream.writeByte(UtopiaBinaryFormatType.STRING.getMarkCode());
            case BOOLEAN -> outputStream.writeByte(UtopiaBinaryFormatType.BOOLEAN.getMarkCode());
            case ARRAY -> outputStream.writeByte(UtopiaBinaryFormatType.ARRAY.getMarkCode());
            case OBJECT -> outputStream.writeByte(UtopiaBinaryFormatType.OBJECT.getMarkCode());
        }
    }

    private void writeValue(@NotNull Object value, @NotNull DataOutputStream outputStream) throws IOException {
        if (value instanceof Byte v) {
            writeType(UtopiaBinaryFormatType.BYTE, outputStream);
            outputStream.writeByte(v);
        } else if (value instanceof Short v) {
            writeType(UtopiaBinaryFormatType.SHORT, outputStream);
            outputStream.writeShort(v);
        } else if (value instanceof Integer v) {
            writeType(UtopiaBinaryFormatType.INT, outputStream);
            outputStream.writeInt(v);
        } else if (value instanceof Long v) {
            writeType(UtopiaBinaryFormatType.LONG, outputStream);
            outputStream.writeLong(v);
        } else if (value instanceof String v) {
            writeType(UtopiaBinaryFormatType.STRING, outputStream);
            var buf = v.getBytes(StandardCharsets.UTF_8);
            outputStream.writeInt(buf.length);
            outputStream.write(buf);
        } else if (value instanceof Boolean v) {
            writeType(UtopiaBinaryFormatType.BOOLEAN, outputStream);
            outputStream.writeBoolean(v);
        } else if (value instanceof Float v) {
            writeType(UtopiaBinaryFormatType.FLOAT, outputStream);
            outputStream.writeFloat(v);
        } else if (value instanceof Double v) {
            writeType(UtopiaBinaryFormatType.DOUBLE, outputStream);
            outputStream.writeDouble(v);
        } else if (value instanceof UtopiaBinaryFormatArray v) {
            writeType(UtopiaBinaryFormatType.ARRAY, outputStream);
            writeArray(v, outputStream);
        } else if (value instanceof UtopiaBinaryFormatObject v) {
            writeType(UtopiaBinaryFormatType.OBJECT, outputStream);
            writeObject(v, outputStream);
        } else {
            throw new IllegalArgumentException("unknown argument type");
        }
    }

    private void writeArray(@NotNull UtopiaBinaryFormatArray array, @NotNull DataOutputStream outputStream)
            throws IOException {
        call();

        outputStream.writeInt(array.size());

        for (var value : array) {
            writeValue(value, outputStream);
        }

        ret();
    }

    private void writeObject(@NotNull UtopiaBinaryFormatObject object, @NotNull DataOutputStream outputStream)
            throws IOException {
        call();

        outputStream.writeInt(object.size());

        for (var key : object.getKeys()) {
            // write key
            var keys = key.getBytes(StandardCharsets.UTF_8);

            outputStream.writeInt(keys.length);

            outputStream.write(keys);

            // write value
            var value = object.get(key).orElseThrow();

            writeValue(value, outputStream);
        }

        ret();
    }

    /**
     * 将输入的对象序列化为二进制格式。
     *
     * @param stream 输出流
     * @param input  输入的对象
     * @throws IOException IO错误
     */
    public void write(@NotNull UtopiaBinaryFormatObject input, @NotNull DataOutputStream stream) throws IOException {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(input);

        callStack = 0;

        writeValue(input, stream);
    }
}
