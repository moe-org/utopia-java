// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ServiceProviderImpl.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceProviderImpl implements ServiceProvider {

    private final ConcurrentHashMap<Class<?>, Object> service = new ConcurrentHashMap<>();

    @Override
    public Optional<Object> getService(Class<?> cls) {
        return Optional.ofNullable(service.get(cls));
    }

    @Override
    public Optional<Object> register(Class<?> cls, Object service) {
        return Optional.ofNullable(this.service.put(cls, service));
    }

    @Override
    public void remove(Class<?> cls) {
        this.service.remove(cls);
    }
}
