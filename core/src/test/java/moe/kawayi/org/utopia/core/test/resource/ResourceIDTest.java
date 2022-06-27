//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceIDTest.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.resource;

import moe.kawayi.org.utopia.core.resource.ResourceID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ResourceIDTest {

    @Test
    public void resourceIDConTest(){
        Assertions.assertDoesNotThrow(()->{
            new ResourceID("root","name");
            new ResourceID("root","subRoot","subRoot2","subRoot3","name");
            new ResourceID(" "," "," ");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                ResourceID::new);
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> {
                    new ResourceID("no sub name");
                });
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> {
                    new ResourceID("其他字符串","name");
                });
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> {
                    new ResourceID("","");
                });
    }

    @Test
    public void resourceIDEqualTest(){
       var id = new ResourceID("root","subRoot","subRoot2","name");
       var paths = new ArrayList<String>();
       paths.add("root");paths.add("subRoot");paths.add("subRoot2");

       var second = new ResourceID("root","subRoot","subRoot2","name");

       Assertions.assertEquals(id.getNamespace(),paths);
       Assertions.assertEquals(id.getName(),"name");
       Assertions.assertEquals(id,second);
       Assertions.assertEquals(id.hashCode(),second.hashCode());
    }

}
