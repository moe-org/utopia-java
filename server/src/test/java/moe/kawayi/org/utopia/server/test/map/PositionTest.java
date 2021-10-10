//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PositionTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.test.map;

import moe.kawayi.org.utopia.server.map.FlatPosition;
import moe.kawayi.org.utopia.server.map.Position;
import moe.kawayi.org.utopia.server.map.WorldPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {


    @Test
    public void worldPositionDowngradeTest() {
        WorldPosition wPos = new WorldPosition(
                Integer.MAX_VALUE,
                Integer.MIN_VALUE,
                1,
                0);

        Position position = wPos.downgrade();

        Assertions.assertEquals(position.x, wPos.x);
        Assertions.assertEquals(position.y, wPos.y);
        Assertions.assertEquals(position.z, wPos.z);
    }

    @Test
    public void positionDowngradeTest() {
        Position wPos = new Position(
                Integer.MAX_VALUE,
                Integer.MIN_VALUE,
                0);

        FlatPosition position = wPos.downgrade();

        Assertions.assertEquals(position.x, wPos.x);
        Assertions.assertEquals(position.y, wPos.y);
    }

}
