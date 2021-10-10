//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EntityManagerTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.test.entity;

import moe.kawayi.org.utopia.server.entity.Entity;
import moe.kawayi.org.utopia.server.entity.EntityFactory;
import moe.kawayi.org.utopia.server.entity.EntityManager;
import moe.kawayi.org.utopia.core.util.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * EntityManager测试
 */
public class EntityManagerTest {

    /**
     * 重复添加实体测试
     */
    @Test
    public void entityManagerAddTest() {
        var object = new EntityFactory() {
            @NotNull
            @Override
            public String getEntityId() {
                return "!Exists Entity!";
            }

            @NotNull
            @Override
            public Entity produce() {
                return null;
            }
        };

        var firstAdd = EntityManager.addEntity(object);

        var added = EntityManager.addEntity(object);

        Assertions.assertTrue(firstAdd);
        Assertions.assertFalse(added);
    }

}
