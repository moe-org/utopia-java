//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBusTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.test.event;

import moe.kawayi.org.utopia.client.event.EventBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class EventBusTest {

    private boolean called = false;

    private final EventBus<Boolean> eventBus = new EventBus<>(Boolean.class);

    @Test
    public void EventBusTestCaller() throws java.lang.Throwable {
        // get method
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();

        MethodType mt = MethodType.methodType(void.class, boolean.class);

        MethodHandle hm = publicLookup.findVirtual(EventBusTest.class, "EventBusTestBeCaller", mt);

        hm = hm.bindTo(this);

        // test
        var id = eventBus.register(hm);

        eventBus.post(true);

        eventBus.unregister(id);

        eventBus.post(false);

        Assertions.assertTrue(called);
    }

    public void EventBusTestBeCaller(boolean eventParameter) {
        called = eventParameter;
    }

}