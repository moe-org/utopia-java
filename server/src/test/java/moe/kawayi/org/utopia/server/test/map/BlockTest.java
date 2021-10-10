//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The BlockTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.test.map;

import moe.kawayi.org.utopia.server.entity.Entity;
import moe.kawayi.org.utopia.server.map.Block;
import moe.kawayi.org.utopia.server.map.BlockImpl;
import moe.kawayi.org.utopia.server.map.WorldPosition;
import moe.kawayi.org.utopia.core.util.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BlockTest {

    private final Block block = new BlockImpl();

    @Test
    public void blockAddCollisionEntityTest() {
        // 一个拥有碰撞体的实体
        var entity = new Entity() {

            private WorldPosition position = null;

            @NotNull
            @Override
            public Optional<WorldPosition> getPosition() {
                return Optional.ofNullable(position);
            }

            @Override
            public void setPosition(@NotNull WorldPosition newPosition) {
                position = newPosition;
            }

            @NotNull
            @Override
            public String getEntityId() {
                return "Can Collision Entity";
            }

            @Override
            public boolean equalEntity(@NotNull Entity other) {
                return false;
            }

            @Override
            public boolean isPassable() {
                return false;
            }

            @Override
            public boolean isCollideable() {
                return true;
            }

            @Override
            public boolean needUpdate() {
                return false;
            }

            @Override
            public void update() {
            }
        };

        var firstAdd = block.addEntity(entity);
        var added = block.addEntity(entity);

        Assertions.assertTrue(firstAdd);
        Assertions.assertFalse(added);
    }

    @Test
    public void blockNullEntityTest() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    block.addEntity(null);
                }
        );
    }


}
