//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The WorldImplTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.test.map;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.map.Position;
import moe.kawayi.org.utopia.server.map.WorldConstructionException;
import moe.kawayi.org.utopia.server.map.WorldImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WorldImplTest {

    private final WorldImpl world;

    public WorldImplTest() throws moe.kawayi.org.utopia.server.map.WorldConstructionException {
        world = new WorldImpl(0, new FlatPosition(4, 4));
    }

    @Test
    public void accessTest() {
        ArrayList<Object> objs = new ArrayList<>();

        int xMax = world.getMaxXSize();
        int xMin = world.getMinXSize();

        int yMax = world.getMaxYSize();
        int yMin = world.getMinYSize();

        for (int x = xMin; x <= xMax; x++)
            for (int y = yMin; y <= yMax; y++) {
                // get block by pos
                var result = world.getBlock(new Position(x,y,0));

                // the block must be existed
                assertTrue(result.isPresent());

                // the block must be unique
                assertEquals(objs.indexOf(result.get()), -1);

                // add to list
                objs.add(result.get());
            }
    }

    @Test
    public void accessWrongTest() {
        int xMax = world.getMaxXSize();
        int xMin = world.getMinXSize();
        int yMax = world.getMaxYSize();
        int yMin = world.getMinYSize();

        assertFalse(world.getBlock(new Position(
                xMax + 1,
                yMax + 1,
                0)).isPresent());

        assertFalse(world.getBlock(new Position(
                xMin - 1,
                yMin - 1,
                0)).isPresent());
    }

    @ParameterizedTest
    @CsvSource({"-1,0", "0,-1", "0,0", "-1,-1"})
    public void wrongConstructionTest(int x, int y) {
        Assertions.assertThrows(
                WorldConstructionException.class,
                () -> {
                    new WorldImpl(0, new FlatPosition(x, y));
                }
        );
    }

    @Test
    public void nullPointerTest() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new WorldImpl(0, null);
                }
        );
    }

}
