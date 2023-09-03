// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The BlockFloorImplTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.test.map;

import java.util.ArrayList;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.server.map.BlockFloorImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static moe.kawayi.org.utopia.server.map.WorldInfo.X_BLOCKS_PER_AREA;
import static moe.kawayi.org.utopia.server.map.WorldInfo.Y_BLOCKS_PER_AREA;
import static org.junit.jupiter.api.Assertions.*;

public class BlockFloorImplTest {

    private final BlockFloorImpl floor = new BlockFloorImpl();

    @Test
    public void accessTest() {
        ArrayList<Object> objs = new ArrayList<>();

        for (int x = 0; x != X_BLOCKS_PER_AREA; x++)
            for (int y = 0; y != X_BLOCKS_PER_AREA; y++) {
                var result = floor.getBlock(new FlatPosition(x, y));

                // the block must be
                assertTrue(result.isPresent());

                // the block must be unique
                assertEquals(objs.indexOf(result.get()), -1);

                // add to list
                objs.add(result.get());
            }
    }

    @Test
    public void accessWrongTest() {
        assertFalse(floor.getBlock(new FlatPosition(Integer.MAX_VALUE, Integer.MIN_VALUE))
                .isPresent());

        assertFalse(floor.getBlock(new FlatPosition(-1, 0)).isPresent());

        assertFalse(floor.getBlock(new FlatPosition(0, -1)).isPresent());

        assertFalse(floor.getBlock(new FlatPosition(-1, -1)).isPresent());

        assertFalse(floor.getBlock(new FlatPosition(X_BLOCKS_PER_AREA, Y_BLOCKS_PER_AREA))
                .isPresent());
    }

    @Test
    public void nullPointerTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            floor.getBlock(null);
        });
    }
}
