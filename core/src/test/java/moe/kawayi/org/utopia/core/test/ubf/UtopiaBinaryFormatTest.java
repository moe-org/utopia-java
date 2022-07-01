// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatTest.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.ubf;

import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormatObjectImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtopiaBinaryFormatTest {

    @Test
    public void utopiaBinaryFormatObjectImplNullTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatObjectImpl().put(null, (String) null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatObjectImpl().put(null, "value");
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatObjectImpl().put("key", (String) null);
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertNotEquals(new UtopiaBinaryFormatObjectImpl(), null);
        });
    }

    /**
     * 相等性测试
     */
    @Test
    public void equalTest() {
        var first = new UtopiaBinaryFormatObjectImpl();

        Assertions.assertNotEquals(first, new Object());

        first.put("key", "value");
        first.put("key2", 1);

        var second = new UtopiaBinaryFormatObjectImpl();

        Assertions.assertNotEquals(first, second);
        Assertions.assertNotEquals(second, first);
        Assertions.assertNotEquals(first.hashCode(), second.hashCode());

        second.put("key", "value");
        second.put("key2", 1);

        Assertions.assertEquals(first, second);
        Assertions.assertEquals(second, first);
        Assertions.assertEquals(first.hashCode(), second.hashCode());
    }
}
