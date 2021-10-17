//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceManagerTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.resource;

import moe.kawayi.org.utopia.core.resource.ResourceManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceManagerTest {

    @Test
    public void RegisterMuchTimeTest(){

        var firstTime = ResourceManager.register(
                "RegisterMuchTimeTest",
                ResourceManager.getSystemResourceLoader());
        var secondTime = ResourceManager.register(
                "RegisterMuchTimeTest",
                ResourceManager.getSystemResourceLoader());

        Assertions.assertTrue(firstTime);
        Assertions.assertFalse(secondTime);
    }

    @Test
    public void UnregisterTest(){

        var registerResult = ResourceManager.register(
                "UnregisterTest",
                ResourceManager.getSystemResourceLoader());

        ResourceManager.unregister("UnregisterTest");

        var findResult = ResourceManager.findLoader("UnregisterTest");

        Assertions.assertTrue(registerResult);
        Assertions.assertTrue(findResult.isEmpty());
    }


}
