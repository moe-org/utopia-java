//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The HoconConfigTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.config;

import com.typesafe.config.ConfigFactory;
import moe.kawayi.org.utopia.core.config.hocon.HoconConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HoconConfigTest {


    public static final String HOCON = """
            {
                byte: 1,
                short: 2,
                int: 3,
                long: 4,
                float: 5.0,
                double: 6.0
                boolean: false,
                string: "Hello HOCON!",
                list: [
                   7
                   8.0
                   true
                   "Hello Utopia!"
                   {
                      in-list: true
                   }
                ]
                object:{
                    in-object: true
                }
            }
            """;

    @Test
    public void praseTest() throws Exception{
        var hocon = new HoconConfig(ConfigFactory.parseString(HOCON));

        Assertions.assertEquals((byte)1,hocon.getByte(hocon.createPath("byte")).orElseThrow());
        Assertions.assertEquals((short)2,hocon.getShort(hocon.createPath("short")).orElseThrow());
        Assertions.assertEquals(3,hocon.getInt(hocon.createPath("int")).orElseThrow());
        Assertions.assertEquals(4L,hocon.getLong(hocon.createPath("long")).orElseThrow());
        Assertions.assertEquals(5.0f,hocon.getFloat(hocon.createPath("float")).orElseThrow());
        Assertions.assertEquals(6.0d,hocon.getDouble(hocon.createPath("double")).orElseThrow());
        Assertions.assertEquals(false,hocon.getBoolean(hocon.createPath("boolean")).orElseThrow());
        Assertions.assertEquals("Hello HOCON!",hocon.getString(hocon.createPath("string")).orElseThrow());

        var list = hocon.getArray(hocon.createPath("list")).orElseThrow();
        Assertions.assertEquals(5,list.size());

        Assertions.assertEquals(7,list.get(0).getInt(null).orElseThrow());
        Assertions.assertEquals(8.0d,list.get(1).getDouble(null).orElseThrow());
        Assertions.assertEquals(true,list.get(2).getBoolean(null).orElseThrow());
        Assertions.assertEquals("Hello Utopia!",list.get(3).getString(null).orElseThrow());

        var object = list.get(4).getObject(null).orElseThrow();

        Assertions.assertEquals(true,object.getBoolean(object.createPath("in-list")).orElseThrow());

        object = hocon.getObject(hocon.createPath("object")).orElseThrow();

        Assertions.assertEquals(true,object.getBoolean(object.createPath("in-object")).orElseThrow());
    }




}
