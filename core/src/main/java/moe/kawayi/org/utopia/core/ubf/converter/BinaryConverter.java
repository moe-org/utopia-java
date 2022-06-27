//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The BinaryConverter.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.ubf.converter;

import moe.kawayi.org.utopia.core.ubf.*;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * UtopiaBinaryFormat的二进制转换器
 */
public final class BinaryConverter {

    /**
     * private
     */
    private BinaryConverter(){}

    /**
     * UtopiaBinaryFormat转换到Binary的线程不安全类
     */
    public static final class ConvertTo {
        /**
         * 默认构造
         */
        public ConvertTo(){}

        /**
         * 递归层数
         */
        private int callStack = 0;

        private void call() {
            callStack++;

            if (callStack > UtopiaBinaryFormat.MAX_STACK)
                throw new IllegalStateException("递归超出UtopiaBinaryFormat.MAX_STACK限制!");
        }

        private void ret() {
            callStack--;
        }

        /**
         * UTF-8 encoding
         * <p>
         * 先写入字符串长度，再写入字符串。
         *
         * @param output 输出流
         * @param str    字符串
         */
        private void convertString(@NotNull DataOutputStream output,
                                   @NotNull String str) throws java.io.IOException {
            Objects.requireNonNull(str, "str must not be null");
            Objects.requireNonNull(output);

            byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);

            output.writeInt(utf8.length);
            output.write(utf8);
        }

        private void convertId(
                @NotNull DataOutputStream output,
                @NotNull UtopiaBinaryFormatType type)
                    throws java.io.IOException {
            // 直接写入id
            output.write(type.getMarkCode());
        }

        private void convertObject(@NotNull DataOutputStream output,
                                   @NotNull UtopiaBinaryFormatObject obj) throws java.io.IOException {
            call();

            // object布局:
            // object length:
            //  - object string length
            //    object string
            //    object id
            //    object value
            //  - ...
            output.writeInt(obj.getLength());

            for (var value : obj.getEntrySet()) {
                convertId(output, value.getValue().getType());
                convertString(output, value.getKey());
                convertValue(output, value.getValue());
            }

            ret();
        }

        private void convertArray(@NotNull DataOutputStream output,
                                  @NotNull UtopiaBinaryFormatArray array) throws java.io.IOException {
            call();

            // array布局:
            // array length:
            //  - array item id
            //    array item value
            //  - ...

            output.writeInt(array.getLength());
            for (var value : array) {
                convertId(output, value.getType());
                convertValue(output, value);
            }

            ret();
        }

        private void convertValue(
                @NotNull DataOutputStream output,
                @NotNull UtopiaBinaryFormatValue obj) throws java.io.IOException {
            switch (obj.getType()) {
                case BYTE -> {
                    output.write(obj.getByte().orElseThrow());
                }
                case SHORT -> {
                    output.writeShort(obj.getShort().orElseThrow());
                }
                case INT -> {
                    output.writeInt(obj.getInt().orElseThrow());
                }
                case LONG -> {
                    output.writeLong(obj.getLong().orElseThrow());
                }
                case FLOAT -> {
                    output.writeFloat(obj.getFloat().orElseThrow());
                }
                case DOUBLE -> {
                    output.writeDouble(obj.getDouble().orElseThrow());
                }
                case BOOLEAN -> {
                    output.writeBoolean(obj.getBoolean().orElseThrow());
                }
                case STRING -> {
                    convertString(output, obj.getString().orElseThrow());
                }
                case ARRAY -> {
                    convertArray(output, obj.getArray().orElseThrow());
                }
                case OBJECT -> {
                    convertObject(output, obj.getObject().orElseThrow());
                }
                default -> {
                    throw new IOException("wrong enum input");
                }
            }
        }

        /**
         * UtopiaBinaryFormat转换到Binary
         *
         * @param output 输出流
         * @param obj    输入对象
         * @throws IllegalStateException 递归层数过多
         * @throws java.io.IOException 来自{@link DataInputStream}
         */
        public void convert(
                @NotNull DataOutputStream output,
                @NotNull UtopiaBinaryFormatObject obj) throws java.io.IOException,IllegalStateException {
            Objects.requireNonNull(output, "output must not be null");
            Objects.requireNonNull(obj, "obj must not be null");

            callStack = 0;

            convertObject(output, obj);
        }
    }

    /**
     * 从Binary转换到UtopiaBinaryFormat的线程不安全类
     */
    public static final class ConvertFrom {

        /**
         * 默认构造
         */
        public ConvertFrom(){}

        /**
         * 递归层数
         */
        private int callStack = 0;

        private void call() {
            callStack++;

            if (callStack > UtopiaBinaryFormat.MAX_STACK)
                throw new IllegalStateException("递归超出UtopiaBinaryFormat.MAX_STACK限制!");
        }

        private void ret() {
            callStack--;
        }


        /**
         * UTF-8 encoding
         * <p>
         * 先读取字符串长度，再读取字符串。
         *
         * @param input 输入流
         */
        @NotNull
        private String convertString(@NotNull DataInputStream input)
                throws java.io.IOException {

            int length = input.readInt();

            return new String(input.readNBytes(length), StandardCharsets.UTF_8);
        }

        @NotNull
        private UtopiaBinaryFormatType convertType(byte id) {
            for (var value : UtopiaBinaryFormatType.values()) {
                if (id == value.getMarkCode())
                    return value;
            }
            throw new IllegalStateException("无效的类型id");
        }

        @NotNull
        private UtopiaBinaryFormatObjectImpl convertObject(
                @NotNull DataInputStream input
        ) throws java.io.IOException {
            call();

            // 读取object长度
            int length = input.readInt();
            var obj = new UtopiaBinaryFormatObjectImpl(length);

            // 遍历
            for (int index = 0; index != length; index++) {
                // 读取类型
                var type = convertType(input.readByte());

                // 读取name
                var name = convertString(input);

                // 读取value
                var value = convertValue(input, type);

                // 组装
                obj.put(name, value);
            }

            ret();

            return obj;
        }

        @NotNull
        private UtopiaBinaryFormatArrayImpl convertArray(
                @NotNull DataInputStream input
        ) throws java.io.IOException {
            call();

            // 读取数组长度
            int length = input.readInt();
            var array = new UtopiaBinaryFormatArrayImpl(length);

            // 遍历
            for (int index = 0; index != length; index++) {
                // 读取类型
                var type = convertType(input.readByte());

                // 读取value
                var value = convertValue(input, type);

                // 组装
                array.add(value);
            }

            ret();

            return array;
        }

        @NotNull
        private UtopiaBinaryFormatValueImpl
            convertValue(@NotNull DataInputStream input, @NotNull UtopiaBinaryFormatType type)
                throws java.io.IOException {
            switch (type) {
                case BYTE -> {
                    return new UtopiaBinaryFormatValueImpl(input.readByte());
                }
                case SHORT -> {
                    return new UtopiaBinaryFormatValueImpl(input.readShort());
                }
                case INT -> {
                    return new UtopiaBinaryFormatValueImpl(input.readInt());
                }
                case LONG -> {
                    return new UtopiaBinaryFormatValueImpl(input.readLong());
                }
                case FLOAT -> {
                    return new UtopiaBinaryFormatValueImpl(input.readFloat());
                }
                case DOUBLE -> {
                    return new UtopiaBinaryFormatValueImpl(input.readDouble());
                }
                case BOOLEAN -> {
                    return new UtopiaBinaryFormatValueImpl(input.readBoolean());
                }
                case STRING -> {
                    return new UtopiaBinaryFormatValueImpl(convertString(input));
                }
                case ARRAY -> {
                    return new UtopiaBinaryFormatValueImpl(convertArray(input));
                }
                case OBJECT -> {
                    return new UtopiaBinaryFormatValueImpl(convertObject(input));
                }
            }
            throw new IllegalStateException("不可能的情况!");
        }

        /**
         * 从Binary转换到UtopiaBinaryFormat
         *
         * @param input 输入流
         * @return UtopiaBinaryFormatObject的不安全实现
         * @throws IllegalStateException 递归层数过多
         * @throws java.io.IOException 来自{@link DataInputStream}
         */
        @NotNull
        public UtopiaBinaryFormatObjectImpl convert(@NotNull DataInputStream input)
                throws java.io.IOException,IllegalStateException {
            Objects.requireNonNull(input, "input must not be null");

            callStack = 0;

            return convertObject(input);
        }

    }

}
