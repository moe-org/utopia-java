//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The HoconValue.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.config.hocon;

import com.typesafe.config.*;
import moe.kawayi.org.utopia.core.config.Config;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * HoconValue的config实现
 */
public class HoconValue implements Config {

    private final ConfigValue value;

    public HoconValue(@NotNull com.typesafe.config.ConfigValue config){
        Objects.requireNonNull(config);

        value = config;
    }

    @Override
    public HoconConfig.NavigationPath createPath(String... paths) {
        Objects.requireNonNull(paths);

        return new HoconConfig.NavigationPath(ConfigUtil.joinPath(paths));
    }

    @Override
    public Optional<Byte> getByte(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getByte(path);
            }
        }

        else if (value.valueType() == ConfigValueType.NUMBER) {
            return Optional.of(((Number) value.unwrapped()).byteValue());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Short> getShort(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getShort(path);
            }
        }

        else if (value.valueType() == ConfigValueType.NUMBER) {
            return Optional.of(((Number) value.unwrapped()).shortValue());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getInt(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getInt(path);
            }
        }

        else if (value.valueType() == ConfigValueType.NUMBER) {
            return Optional.of(((Number) value.unwrapped()).intValue());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Long> getLong(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getLong(path);
            }
        }

        else if (value.valueType() == ConfigValueType.NUMBER) {
            return Optional.of(((Number) value.unwrapped()).longValue());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> getBoolean(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getBoolean(path);
            }
        }

        else if (value.valueType() == ConfigValueType.BOOLEAN) {
            return Optional.of(((Boolean) value.unwrapped()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Float> getFloat(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getFloat(path);
            }
        }

        else if (value.valueType() == ConfigValueType.NUMBER) {
            return Optional.of(((Number) value.unwrapped()).floatValue());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Double> getDouble(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getDouble(path);
            }
        }

        else if (value.valueType() == ConfigValueType.NUMBER) {
            return Optional.of(((Number) value.unwrapped()).doubleValue());
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getString(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getString(path);
            }
        }

        else if (value.valueType() == ConfigValueType.STRING) {
            return Optional.of(((String) value.unwrapped()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UUID> getUUID(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getUUID(path);
            }
        }

        else {
            var str = getString(null);

            if (str != null && str.isPresent()) {
                return Optional.of(UUID.fromString(str.get()));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Config>> getArray(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getArray(path);
            }
        }

        else if (value.valueType() == ConfigValueType.LIST) {
            var list = (ConfigList)value;
            return Optional.of(
                    list.parallelStream().map(HoconValue::new).collect(Collectors.toList())
            );
        }
        return Optional.empty();
    }

    @Override
    public Optional<Config> getObject(NavigationPath path) throws Exception {
        if(path != null){
            var map = getObject(null);

            if(map.isPresent()){
                return map.get().getObject(path);
            }
        }

        else if (value.valueType() == ConfigValueType.OBJECT) {
            return Optional.of(new HoconConfig(((ConfigObject)value).toConfig()));
        }
        return Optional.empty();
    }

    @Override
    public String renderToString() {
        return value.render(ConfigRenderOptions.concise());
    }
}
