// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The AreaTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.test.map;

import java.util.ArrayList;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.map.Position;
import moe.kawayi.org.utopia.server.map.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AreaTest {

    private final Area area = new AreaImpl(new FlatPosition(0, 0));

    @Test
    public void areaAccessTest() {
        ArrayList<Object> objs = new ArrayList<>();

        for (int x = 0; x != WorldInfo.X_BLOCKS_PER_AREA; x++)
            for (int y = 0; y != WorldInfo.Y_BLOCKS_PER_AREA; y++) {
                var block = area.getBlock(new Position(x, y, WorldInfo.GROUND_Z));

                // 确保存在
                Assertions.assertTrue(block.isPresent());

                // 并且不重复
                Assertions.assertEquals(objs.indexOf(block.get()), -1);

                objs.add(block.get());
            }
    }

    @Test
    public void areaAccessWrongTest() {
        var result1 = area.getBlock(
                new Position(WorldInfo.X_BLOCKS_PER_AREA, WorldInfo.Y_BLOCKS_PER_AREA, WorldInfo.GROUND_Z));

        var result2 = area.getBlock(new Position(-1, -1, WorldInfo.GROUND_Z));

        Assertions.assertFalse(result1.isPresent());
        Assertions.assertFalse(result2.isPresent());
    }

    @Test
    public void areaNullPointerTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            area.getBlock(null);
        });
    }
}
