// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Parser.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf.converter;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import moe.kawayi.org.utopia.core.ubf.*;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * {@link moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObject}的解析器
 */
public final class Parser {

    /**
     * 默认构造
     */
    public Parser() {}

    private int callStack = 0;

    private void call() {
        callStack++;

        if (callStack >= UtopiaBinaryFormat.MAX_STACK) {
            throw new IllegalStateException("stack limit exceeded");
        }
    }

    private void ret() {
        callStack--;
    }

    @NotNull
    private String parseString(@NotNull DataInputStream stream) throws IOException {
        var size = stream.readInt();
        var buf = new byte[size];
        var read = stream.read(buf);

        if (read != size) {
            throw new IOException("unexpected End-of-Stream");
        }

        return new String(buf, StandardCharsets.UTF_8);
    }

    @NotNull
    private Object parseValue(@NotNull DataInputStream stream) throws IOException {
        var typed = stream.readByte();

        return switch (UtopiaBinaryFormatType.valueOf(typed)) {
            case BYTE -> stream.readByte();
            case SHORT -> stream.readShort();
            case INT -> stream.readInt();
            case LONG -> stream.readLong();
            case FLOAT -> stream.readFloat();
            case DOUBLE -> stream.readDouble();
            case STRING -> parseString(stream);
            case BOOLEAN -> stream.readBoolean();
            case ARRAY -> parseArray(stream);
            case OBJECT -> parseObject(stream);
        };
    }

    @NotNull
    private UtopiaBinaryFormatArray parseArray(@NotNull DataInputStream stream) throws IOException {
        call();

        var size = stream.readInt();

        var array = new UtopiaBinaryFormatArrayImpl(size);

        while (size != 0) {
            size--;

            array.add(parseValue(stream));
        }

        ret();
        return array;
    }

    @NotNull
    private UtopiaBinaryFormatObject parseObject(@NotNull DataInputStream stream) throws IOException {
        call();

        var size = stream.readInt();

        var object = new UtopiaBinaryFormatObjectImpl(size);

        while (size != 0) {
            size--;

            var key = parseString(stream);
            var value = parseValue(stream);

            object.put(key, value);
        }

        ret();
        return object;
    }

    /**
     * 解析二进制输入流为对象
     *
     * @param stream 输入流
     * @return 解析获得的对象
     * @throws IOException IO错误
     */
    @NotNull
    public UtopiaBinaryFormatObject parse(@NotNull DataInputStream stream) throws IOException {
        Objects.requireNonNull(stream);

        callStack = 0;

        try {
            return (UtopiaBinaryFormatObject) parseValue(stream);
        } catch (ClassCastException err) {
            throw new IOException("illegal input", err);
        }
    }
}
