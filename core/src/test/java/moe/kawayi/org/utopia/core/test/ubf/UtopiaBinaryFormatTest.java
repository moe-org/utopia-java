// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.ubf;

import moe.kawayi.org.utopia.core.ubf.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtopiaBinaryFormatTest {

    @Test
    public void utopiaBinaryFormatObjectImplNullTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatObjectImpl().put(null, (String) null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new UtopiaBinaryFormatObjectImpl().put("key", (String) null);
        });
    }
}
