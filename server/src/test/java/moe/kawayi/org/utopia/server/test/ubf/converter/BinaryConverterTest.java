//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The BinaryConverterTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.test.ubf.converter;

import moe.kawayi.org.utopia.server.ubf.*;
import moe.kawayi.org.utopia.server.ubf.converter.BinaryConverter;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BinaryConverterTest {

    /**
     * 递归调用测试
     */
    public void StackCallTest() throws java.io.IOException {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        UtopiaBinaryFormatObjectImpl caller = obj;

        for (int stack = 1; stack != UtopiaBinaryFormat.MAX_STACK; stack++) {

            var newCaller = new UtopiaBinaryFormatObjectImpl();
            var newValue = new UtopiaBinaryFormatValueImpl(newCaller);

            caller.put("", newValue);

            caller = newCaller;
        }

        Assertions.assertDoesNotThrow(
                () -> {
                    var memoryOutput = new ByteArrayOutputStream();
                    var outputStream = new DataOutputStream(memoryOutput);

                    var to = new BinaryConverter.ConvertTo();
                    var from = new BinaryConverter.ConvertFrom();

                    // 不抛异常就算成功
                    to.convert(outputStream, obj);

                    var inputStream = new DataInputStream(new ByteArrayInputStream(memoryOutput.toByteArray()));

                    from.convert(inputStream);
                }
        );
    }

    /**
     * 递归调用测试
     */
    public void StackCallErrorTest() throws java.io.IOException {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        UtopiaBinaryFormatObjectImpl caller = obj;

        for (int stack = 1; stack != (UtopiaBinaryFormat.MAX_STACK + 1); stack++) {

            var newCaller = new UtopiaBinaryFormatObjectImpl();
            var newValue = new UtopiaBinaryFormatValueImpl(newCaller);

            caller.put("", newValue);

            caller = newCaller;
        }

        Assertions.assertThrows(IllegalStateException.class,
                () -> {
                    var memoryOutput = new ByteArrayOutputStream();
                    var outputStream = new DataOutputStream(memoryOutput);

                    var to = new BinaryConverter.ConvertTo();

                    // 不抛异常就算成功
                    to.convert(outputStream, obj);
                }
        );
    }

    /**
     * 检查基础类型转换一致性
     */
    public void ConvertBaseRightTest() throws java.io.IOException {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        obj.put("BYTE", new UtopiaBinaryFormatValueImpl((byte) 1));
        obj.put("SHORT", new UtopiaBinaryFormatValueImpl((short) 2));
        obj.put("INT", new UtopiaBinaryFormatValueImpl(3));
        obj.put("LONG", new UtopiaBinaryFormatValueImpl((long) 4));
        obj.put("FLOAT", new UtopiaBinaryFormatValueImpl((float) 5));
        obj.put("DOUBLE", new UtopiaBinaryFormatValueImpl((double) 6));
        obj.put("BOOLEAN", new UtopiaBinaryFormatValueImpl(false));
        obj.put("STRING", new UtopiaBinaryFormatValueImpl("STR"));

        var memoryOutput = new ByteArrayOutputStream();
        var outputStream = new DataOutputStream(memoryOutput);

        var to = new BinaryConverter.ConvertTo();
        var from = new BinaryConverter.ConvertFrom();

        to.convert(outputStream, obj);

        var inputStream = new DataInputStream(new ByteArrayInputStream(memoryOutput.toByteArray()));

        obj = from.convert(inputStream);

        // 检验
        var value = obj.get("BYTE");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getByte().isPresent());
        Assertions.assertEquals((byte) 1, value.get().getByte().get());


        value = obj.get("SHORT");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getShort().isPresent());
        Assertions.assertEquals((short) 2, value.get().getShort().get());


        value = obj.get("INT");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getInt().isPresent());
        Assertions.assertEquals(3, value.get().getInt().get());


        value = obj.get("LONG");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getLong().isPresent());
        Assertions.assertEquals(4, value.get().getLong().get());


        value = obj.get("FLOAT");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getFloat().isPresent());
        Assertions.assertEquals((float) 0.0, value.get().getFloat().get());


        value = obj.get("DOUBLE");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getDouble().isPresent());
        Assertions.assertEquals(0.0, value.get().getDouble().get());


        value = obj.get("BOOLEAN");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getBoolean().isPresent());
        Assertions.assertEquals(false, value.get().getBoolean().get());


        value = obj.get("STRING");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getString().isPresent());
        Assertions.assertEquals("STR", value.get().getString().get());
    }

    /**
     * 检查数组转换一致性
     */
    public void ConvertArrayTest() throws java.io.IOException {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        UtopiaBinaryFormatArray array = new UtopiaBinaryFormatArrayImpl();

        array.add(new UtopiaBinaryFormatValueImpl(1));
        array.add(new UtopiaBinaryFormatValueImpl(2));
        array.add(new UtopiaBinaryFormatValueImpl(3));
        obj.put("", new UtopiaBinaryFormatValueImpl(array));

        // 转换
        var memoryOutput = new ByteArrayOutputStream();
        var outputStream = new DataOutputStream(memoryOutput);

        var to = new BinaryConverter.ConvertTo();
        var from = new BinaryConverter.ConvertFrom();

        to.convert(outputStream, obj);

        var inputStream = new DataInputStream(new ByteArrayInputStream(memoryOutput.toByteArray()));

        obj = from.convert(inputStream);

        // 检查
        var value = obj.get("");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getArray().isPresent());

        array = value.get().getArray().get();

        Assertions.assertEquals(3, array.getLength());
        Assertions.assertEquals(1, array.get(0).getInt().orElseThrow());
        Assertions.assertEquals(2, array.get(1).getInt().orElseThrow());
        Assertions.assertEquals(3, array.get(2).getInt().orElseThrow());
    }


    public void ConvertObjectTest() throws java.io.IOException {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        UtopiaBinaryFormatObject testObj = new UtopiaBinaryFormatObjectImpl();

        testObj.put("AK", new UtopiaBinaryFormatValueImpl("AV"));
        testObj.put("BK", new UtopiaBinaryFormatValueImpl("BV"));
        testObj.put("CK", new UtopiaBinaryFormatValueImpl("CV"));
        obj.put("", new UtopiaBinaryFormatValueImpl(testObj));

        // 转换
        var memoryOutput = new ByteArrayOutputStream();
        var outputStream = new DataOutputStream(memoryOutput);

        var to = new BinaryConverter.ConvertTo();
        var from = new BinaryConverter.ConvertFrom();

        to.convert(outputStream, obj);

        var inputStream = new DataInputStream(new ByteArrayInputStream(memoryOutput.toByteArray()));

        obj = from.convert(inputStream);

        // 检查
        var value = obj.get("");

        Assertions.assertTrue(value.isPresent());
        Assertions.assertTrue(value.get().getObject().isPresent());

        testObj = value.get().getObject().get();

        Assertions.assertEquals(3, testObj.getLength());

        Assertions.assertTrue(testObj.get("AK").isPresent());
        Assertions.assertTrue(testObj.get("AK").get().getString().isPresent());
        Assertions.assertEquals("AV", testObj.get("AK").get().getString().get());

        Assertions.assertTrue(testObj.get("BK").isPresent());
        Assertions.assertTrue(testObj.get("BK").get().getString().isPresent());
        Assertions.assertEquals("BV", testObj.get("BK").get().getString().get());

        Assertions.assertTrue(testObj.get("CK").isPresent());
        Assertions.assertTrue(testObj.get("CK").get().getString().isPresent());
        Assertions.assertEquals("CV", testObj.get("CK").get().getString().get());
    }

}
