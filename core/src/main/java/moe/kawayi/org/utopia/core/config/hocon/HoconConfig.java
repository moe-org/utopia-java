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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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

    /**
     * 构造一个HoconConfig
     * @param config Hocon的Config，非空
     */
    public HoconConfig(@NotNull com.typesafe.config.Config config){
        Objects.requireNonNull(config);

        obj = config;
    }

    @Override
    @NotNull
    public NavigationPath createPath(@NotNull String... paths) {
        Objects.requireNonNull(paths);

        return new NavigationPath(ConfigUtil.joinPath(paths));
    }

    @Override
    @NotNull
    public Optional<Byte> getByte(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getNumber(((NavigationPath)path).path).byteValue());
    }

    @Override
    @NotNull
    public Optional<Short> getShort(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getNumber(((NavigationPath)path).path).shortValue());
    }

    @Override
    @NotNull
    public Optional<Integer> getInt(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getNumber(((NavigationPath)path).path).intValue());
    }

    @Override
    @NotNull
    public Optional<Long> getLong(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getNumber(((NavigationPath)path).path).longValue());
    }

    @Override
    @NotNull
    public Optional<Boolean> getBoolean(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getBoolean(((NavigationPath)path).path));
    }

    @Override
    @NotNull
    public Optional<Float> getFloat(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(((Double)obj.getDouble(((NavigationPath)path).path)).floatValue());
    }

    @Override
    @NotNull
    public Optional<Double> getDouble(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getDouble(((NavigationPath)path).path));
    }

    @Override
    @NotNull
    public Optional<String> getString(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(obj.getString(((NavigationPath)path).path));
    }

    @Override
    @NotNull
    public Optional<UUID> getUUID(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(
                UUID.fromString(obj.getString(((NavigationPath)path).path)));
    }

    @Override
    @NotNull
    public Optional<List<Config>> getArray(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        var list = obj.getList(((NavigationPath)path).path);
        return Optional.of(
                list.parallelStream().map(HoconValue::new).collect(Collectors.toList())
        );
    }

    @Override
    @NotNull
    public Optional<Config> getObject(@NotNull Config.NavigationPath path) throws Exception {
        Objects.requireNonNull(path);
        return Optional.of(
                new HoconConfig(obj.getConfig(((NavigationPath)path).path))
        );
    }

    @Override
    @NotNull
    public String renderToString() {
        return obj.root().render(ConfigRenderOptions.concise());
    }

}
