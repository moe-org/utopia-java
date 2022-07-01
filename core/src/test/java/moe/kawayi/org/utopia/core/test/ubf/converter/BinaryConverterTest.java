// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The BinaryConverterTest.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.ubf.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import moe.kawayi.org.utopia.core.ubf.*;
import moe.kawayi.org.utopia.core.ubf.converter.Parser;
import moe.kawayi.org.utopia.core.ubf.converter.Writer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BinaryConverterTest {

    /**
     * 递归调用测试
     */
    @Test
    public void recursionTest() {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        UtopiaBinaryFormatObjectImpl caller = obj;

        for (int stack = 1; stack != UtopiaBinaryFormat.MAX_STACK; stack++) {

            var newCaller = new UtopiaBinaryFormatObjectImpl();

            caller.put("", 1);

            caller = newCaller;
        }

        Assertions.assertDoesNotThrow(() -> {
            var memoryOutput = new ByteArrayOutputStream();
            var outputStream = new DataOutputStream(memoryOutput);

            var to = new Writer();
            var from = new Parser();

            // 不抛异常就算成功
            to.write(obj, outputStream);

            var inputStream = new DataInputStream(new ByteArrayInputStream(memoryOutput.toByteArray()));

            from.parse(inputStream);
        });
    }

    /**
     * 递归调用测试
     */
    @Test
    public void recursionOverflowTest() {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        UtopiaBinaryFormatObjectImpl caller = obj;

        for (int stack = 1; stack != (UtopiaBinaryFormat.MAX_STACK + 1); stack++) {

            var newCaller = new UtopiaBinaryFormatObjectImpl();

            caller.put("", newCaller);

            caller = newCaller;
        }

        Assertions.assertThrows(IllegalStateException.class, () -> {
            var memoryOutput = new ByteArrayOutputStream();
            var outputStream = new DataOutputStream(memoryOutput);

            var to = new Writer();

            to.write(obj, outputStream);
        });
    }

    /**
     * 检查基础类型转换一致性
     */
    @Test
    public void conversionCorrectnessTest() {
        Assertions.assertDoesNotThrow(() -> {
            UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
            obj.put("BYTE", (byte) 1);
            obj.put("SHORT", (short) 2);
            obj.put("INT", 3);
            obj.put("LONG", (long) 4);
            obj.put("FLOAT", (float) 5);
            obj.put("DOUBLE", (double) 6);
            obj.put("BOOLEAN", false);
            obj.put("STRING", "STR");

            var memoryOutput = new ByteArrayOutputStream();
            var outputStream = new DataOutputStream(memoryOutput);

            var to = new Writer();
            var from = new Parser();

            to.write(obj, outputStream);

            var inputStream = new DataInputStream(new ByteArrayInputStream(memoryOutput.toByteArray()));

            obj = (UtopiaBinaryFormatObjectImpl) from.parse(inputStream);

            // 检验
            var value = obj.get("BYTE");

            Assertions.assertEquals((byte) 1, (byte) value.orElseThrow());

            value = obj.get("SHORT");

            Assertions.assertEquals((short) 2, (short) value.orElseThrow());

            value = obj.get("INT");

            Assertions.assertEquals(3, (int) value.orElseThrow());

            value = obj.get("LONG");

            Assertions.assertEquals(4L, (long) value.orElseThrow());

            value = obj.get("FLOAT");

            Assertions.assertEquals(5.0f, (float) value.orElseThrow());

            value = obj.get("DOUBLE");

            Assertions.assertEquals(6.0, (double) value.orElseThrow());

            value = obj.get("BOOLEAN");

            Assertions.assertFalse((boolean) value.orElseThrow());

            value = obj.get("STRING");

            Assertions.assertEquals("STR", value.orElseThrow());
        });
    }

    /**
     * 检查数组转换一致性
     */
    @Test
    public void convertArrayCorrectnessTest() throws java.io.IOException {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        UtopiaBinaryFormatArray array = new UtopiaBinaryFormatArrayImpl();

        array.add(1);
        array.add(2);
        array.add(3);
        obj.put("", array);

        // 转换
        var memoryOutput = new ByteArrayOutputStream();
        var outputStream = new DataOutputStream(memoryOutput);

        var to = new Writer();
        var from = new Parser();

        to.write(obj, outputStream);

        var inputStream = new DataInputStream(new ByteArrayInputStream(memoryOutput.toByteArray()));

        obj = (UtopiaBinaryFormatObjectImpl) from.parse(inputStream);

        // 检查
        var value = obj.getArray("").orElseThrow();

        Assertions.assertEquals(3, value.size());
        Assertions.assertEquals(1, value.getInt(0).orElseThrow());
        Assertions.assertEquals(2, value.getInt(1).orElseThrow());
        Assertions.assertEquals(3, value.getInt(2).orElseThrow());
    }

    @Test
    public void convertObjectCorrectnessTest() throws java.io.IOException {
        UtopiaBinaryFormatObjectImpl obj = new UtopiaBinaryFormatObjectImpl();
        UtopiaBinaryFormatObject testObj = new UtopiaBinaryFormatObjectImpl();

        testObj.put("AK", "AV");
        testObj.put("BK", "BV");
        testObj.put("CK", "CV");
        obj.put("", testObj);

        // 转换
        var memoryOutput = new ByteArrayOutputStream();
        var outputStream = new DataOutputStream(memoryOutput);

        var to = new Writer();
        var from = new Parser();

        to.write(obj, outputStream);

        var inputStream = new DataInputStream(new ByteArrayInputStream(memoryOutput.toByteArray()));

        obj = (UtopiaBinaryFormatObjectImpl) from.parse(inputStream);

        // 检查
        var value = obj.getObject("").orElseThrow();

        Assertions.assertEquals(3, value.size());

        Assertions.assertTrue(testObj.get("AK").isPresent());
        Assertions.assertEquals("AV", testObj.getString("AK").orElseThrow());

        Assertions.assertTrue(testObj.get("BK").isPresent());
        Assertions.assertEquals("BV", testObj.getString("BK").orElseThrow());

        Assertions.assertTrue(testObj.get("CK").isPresent());
        Assertions.assertEquals("CV", testObj.getString("CK").orElseThrow());
    }

    @Test
    public void nullTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Writer().write(null, null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Writer().write(new UtopiaBinaryFormatObjectImpl(), null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Writer().write(null, new DataOutputStream(new ByteArrayOutputStream()));
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Parser().parse(null);
        });
    }
}
