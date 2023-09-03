// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBusTest.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.event;

import java.util.function.Consumer;

import moe.kawayi.org.utopia.core.event.ComplexEvent;
import moe.kawayi.org.utopia.core.event.ComplexEventImpl;
import moe.kawayi.org.utopia.core.event.EventPublisherImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventPublisherImplTest {

    private boolean called = false;

    private final EventPublisherImpl<ComplexEvent<Boolean>> eventPublisherImpl = new EventPublisherImpl<>();

    @Test
    public void eventBusTest() {
        // test
        Consumer<ComplexEvent<Boolean>> event = (ComplexEvent<Boolean> e) -> {
            called = e.getParameter().orElseThrow();
        };

        eventPublisherImpl.register(event);

        eventPublisherImpl.fire(new ComplexEventImpl<>(true, null, true));

        eventPublisherImpl.unregister(event);

        eventPublisherImpl.fire(new ComplexEventImpl<>(false, null, true));

        Assertions.assertTrue(called);
    }

    @Test
    public void nullTest() {
        var bus = new EventPublisherImpl<ComplexEvent<Integer>>();

        Assertions.assertThrows(NullPointerException.class, () -> {
            bus.register(null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            bus.unregister(null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            bus.fire(null);
        });
    }
}
