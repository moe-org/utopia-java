// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PositionTest.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.map;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.map.Position;
import moe.kawayi.org.utopia.core.map.WorldPosition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link moe.kawayi.org.utopia.core.map.FlatPosition}和
 * {@link moe.kawayi.org.utopia.core.map.Position}和
 * {@link moe.kawayi.org.utopia.core.map.WorldPosition}的测试类
 */
public class PositionTest {

    @Test
    public void equalsFlatTest() {
        var pos = new FlatPosition(0, 0);

        Assertions.assertNotEquals(pos, null);
        Assertions.assertNotEquals(pos, new Object());
        Assertions.assertNotEquals(pos, new FlatPosition(0, 1));
        Assertions.assertNotEquals(pos, new FlatPosition(1, 0));

        Assertions.assertEquals(pos, new FlatPosition(0, 0));
        Assertions.assertEquals(pos.hashCode(), new FlatPosition(0, 0).hashCode());
    }

    @Test
    public void equalsTest() {
        var pos = new Position(0, 0, 0);

        Assertions.assertNotEquals(pos, null);
        Assertions.assertNotEquals(pos, new Object());
        Assertions.assertNotEquals(pos, new Position(0, 0, 1));
        Assertions.assertNotEquals(pos, new Position(0, 1, 0));
        Assertions.assertNotEquals(pos, new Position(1, 0, 0));
        Assertions.assertNotEquals(pos, new Position(1, 1, 0));
        Assertions.assertNotEquals(pos, new Position(0, 1, 1));
        Assertions.assertNotEquals(pos, new Position(1, 1, 1));

        Assertions.assertEquals(pos, new Position(0, 0, 0));
        Assertions.assertEquals(pos.hashCode(), new Position(0, 0, 0).hashCode());
    }

    @Test
    public void equalsWorldTest() {
        var pos = new WorldPosition(0, 0, 0, 0);

        Assertions.assertNotEquals(pos, null);
        Assertions.assertNotEquals(pos, new Object());
        Assertions.assertNotEquals(pos, new WorldPosition(0, 0, 1, 0));
        Assertions.assertNotEquals(pos, new WorldPosition(0, 1, 0, 0));
        Assertions.assertNotEquals(pos, new WorldPosition(1, 0, 0, 0));
        Assertions.assertNotEquals(pos, new WorldPosition(1, 1, 0, 0));
        Assertions.assertNotEquals(pos, new WorldPosition(0, 1, 1, 0));
        Assertions.assertNotEquals(pos, new WorldPosition(1, 1, 1, 0));
        Assertions.assertNotEquals(pos, new WorldPosition(0, 0, 0, 1));

        Assertions.assertEquals(pos, new WorldPosition(0, 0, 0, 0));
        Assertions.assertEquals(pos.hashCode(), new WorldPosition(0, 0, 0, 0).hashCode());
    }
}
