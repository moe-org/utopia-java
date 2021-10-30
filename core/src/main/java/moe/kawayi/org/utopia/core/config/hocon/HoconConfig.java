//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The HoconConfig.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.config.hocon;

import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigUtil;
import moe.kawayi.org.utopia.core.config.Config;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * HOCON的config实现
 *
 * getter参数不支持null参数，将会抛出{@link NullPointerException}异常。
 *
 * 支持null的getter仅在{@link HoconConfig#getArray(Config.NavigationPath)}的返回对象{@link HoconValue}中实现。
 * 并且在调用{@link HoconValue#getObject(Config.NavigationPath)}之后再次失去该能力。
 */
public class HoconConfig implements Config {

    private final com.typesafe.config.Config obj;

    /**
     * 导航路径
     */
    public record NavigationPath(@NotNull String path) implements Config.NavigationPath{ }

    public HoconConfig(@NotNull com.typesafe.config.Config config){
        Objects.requireNonNull(config);

        obj = config;
    }

    @Override
    public NavigationPath createPath(String... paths) {
        Objects.requireNonNull(paths);

        return new NavigationPath(ConfigUtil.joinPath(paths));
    }

    @Override
    public Optional<Byte> getByte(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getNumber(((NavigationPath)path).path).byteValue());
    }

    @Override
    public Optional<Short> getShort(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getNumber(((NavigationPath)path).path).shortValue());
    }

    @Override
    public Optional<Integer> getInt(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getNumber(((NavigationPath)path).path).intValue());
    }

    @Override
    public Optional<Long> getLong(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getNumber(((NavigationPath)path).path).longValue());
    }

    @Override
    public Optional<Boolean> getBoolean(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getBoolean(((NavigationPath)path).path));
    }

    @Override
    public Optional<Float> getFloat(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(((Double)obj.getDouble(((NavigationPath)path).path)).floatValue());
    }

    @Override
    public Optional<Double> getDouble(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getDouble(((NavigationPath)path).path));
    }

    @Override
    public Optional<String> getString(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getString(((NavigationPath)path).path));
    }

    @Override
    public Optional<UUID> getUUID(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(
                UUID.fromString(obj.getString(((NavigationPath)path).path)));
    }

    @Override
    public Optional<List<Config>> getArray(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        var list = obj.getList(((NavigationPath)path).path);
        return Optional.of(
                list.parallelStream().map(HoconValue::new).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Config> getObject(Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(
                new HoconConfig(obj.getConfig(((NavigationPath)path).path))
        );
    }

    @Override
    public String renderToString() {
        return obj.root().render(ConfigRenderOptions.concise());
    }

}
