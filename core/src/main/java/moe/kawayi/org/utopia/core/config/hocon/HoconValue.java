//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The HoconValue.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.config.hocon;

import com.typesafe.config.*;
import moe.kawayi.org.utopia.core.config.Config;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * HoconValue的config实现
 */
class HoconValue implements Config {

    private final ConfigValue value;

    public HoconValue(@NotNull com.typesafe.config.ConfigValue config){
        Objects.requireNonNull(config);

        value = config;
    }

    @Override
    @NotNull
    public HoconConfig.NavigationPath createPath(@NotNull String... paths) {
        Objects.requireNonNull(paths);

        return new HoconConfig.NavigationPath(ConfigUtil.joinPath(paths));
    }

    @Override
    @NotNull
    public Optional<Byte> getByte(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<Short> getShort(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<Integer> getInt(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<Long> getLong(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<Boolean> getBoolean(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<Float> getFloat(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<Double> getDouble(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<String> getString(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<UUID> getUUID(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<List<Config>> getArray(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public Optional<Config> getObject(@Nullable NavigationPath path) throws Exception {
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
    @NotNull
    public String renderToString() {
        return value.render(ConfigRenderOptions.concise());
    }
}
