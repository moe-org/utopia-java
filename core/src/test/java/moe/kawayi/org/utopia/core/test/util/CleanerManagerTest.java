//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The CleanerManagerTest.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.util;

import moe.kawayi.org.utopia.core.util.CleanerManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CleanerManagerTest {

    @Test
    public void setMaxTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CleanerManager.setMax(0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CleanerManager.setMax(-1);
        });
        CleanerManager.setMax(1);

        Assertions.assertEquals(1, CleanerManager.getMax());

        Assertions.assertEquals(CleanerManager.getCleaner(), CleanerManager.getCleaner());
    }
}
