//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EnvironmentTest.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.util;

import moe.kawayi.org.utopia.core.util.EnvironmentChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 环境测试
 */
public class EnvironmentTest {

    @Test
    public void printTest() {
        EnvironmentChecker.print((msg) -> {
            Assertions.assertNotEquals(msg.length(), 0);
        });
    }

    @Test
    public void checkTest() {
        var origin = System.getProperty("file.encoding");
        System.setProperty("file.encoding", "GBK");

        Assertions.assertFalse(EnvironmentChecker.check());

        System.setProperty("file.encoding", "utf-8");

        Assertions.assertTrue(EnvironmentChecker.check());

        System.setProperty("file.encoding", origin);
    }
}
