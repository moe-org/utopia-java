//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.test.ubf;

import moe.kawayi.org.utopia.client.ubf.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtopiaBinaryFormatTest {


    @Test
    public void UtopiaBinaryFormatValueImplNullTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatValueImpl((String) null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatValueImpl((UtopiaBinaryFormatArray) null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatValueImpl((UtopiaBinaryFormatObject) null);
        });
    }

    @Test
    public void UtopiaBinaryFormatArrayImplNullTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatArrayImpl().add(null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            var array = new UtopiaBinaryFormatArrayImpl();

            // 填充
            array.add(new UtopiaBinaryFormatValueImpl(1));

            // note
            array.set(0, null);
        });
    }

    @Test
    public void UtopiaBinaryFormatObjectImplNullTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatObjectImpl().put(null, null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatObjectImpl().put("key", null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatObjectImpl().put(null, new UtopiaBinaryFormatValueImpl(0));
        });
    }


}
