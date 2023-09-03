// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ComplexEvent.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

public interface ComplexEvent<T> extends Event, EventWithParameter<T>, EventWithResult<T> {}
