// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceIDTest.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.resource;

import java.util.ArrayList;

import moe.kawayi.org.utopia.core.resource.ResourceID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceIDTest {
    @Test
    public void resourceIDConstructionTest() {
        Assertions.assertDoesNotThrow(() -> {
            new ResourceID("root", "name");
            new ResourceID("root", "subRoot", "subRoot2", "subRoot3", "name");
            new ResourceID(" ", " ", " ");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ResourceID::new);
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            new ResourceID("no sub name");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            new ResourceID("其他字符串", "name");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            new ResourceID("", "");
        });
    }

    @Test
    public void resourceIDEqualTest() {
        var id = new ResourceID("root", "subRoot", "subRoot2", "name");
        var paths = new ArrayList<String>();
        paths.add("root");
        paths.add("subRoot");
        paths.add("subRoot2");

        Assertions.assertNotEquals(id, null);
        Assertions.assertNotEquals(id, new Object());
        Assertions.assertNotEquals(id, new ResourceID("root", "subRoot", "name"));

        var second = new ResourceID("root", "subRoot", "subRoot2", "name");

        Assertions.assertEquals(id.getNamespace(), paths);
        Assertions.assertEquals(id.getName(), "name");
        Assertions.assertEquals(id, second);
        Assertions.assertEquals(id.hashCode(), second.hashCode());
    }
}
