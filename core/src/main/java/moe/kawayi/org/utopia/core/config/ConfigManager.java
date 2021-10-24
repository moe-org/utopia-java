//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ConfigManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.config;

import com.typesafe.config.*;
import moe.kawayi.org.utopia.core.ubf.UtopiaBinaryFormat;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

/**
 * 配置文件管理器
 */
public final class ConfigManager {

    /**
     * 加载配置文件
     * @param path 配置文件路径
     * @return 加载到的配置文件。如果配置文件不存在则返回empty
     * @throws IllegalArgumentException 如果文件不以.conf结尾则抛出
     */
    public static Optional<Config> loadConfig(@NotNull Path path) throws IllegalArgumentException{
        Objects.requireNonNull(path);

        if(!Files.exists(path)){
            return Optional.empty();
        }
        else{
            if(path.endsWith(".conf")){
                var options = ConfigParseOptions.defaults();
                options = options.setClassLoader(null);
                options = options.setAllowMissing(false);
                options = options.setIncluder(null);
                options = options.setSyntax(ConfigSyntax.CONF);

                com.typesafe.config.Config config = ConfigFactory.parseFile(path.toFile(),options);

                return Optional.of(config);
            }
            else{
                throw new IllegalArgumentException("file is not end with '.conf'");
            }
        }
    }

    /**
     * 根据参数configInfoClass对config注入默认配置
     *
     * @param config 要注入的配置
     * @param configInfoClass 要注入的配置的信息
     */
    public static void putDefaultConfig(@NotNull Config config,@NotNull Class<?> configInfoClass){
        Objects.requireNonNull(config);
        Objects.requireNonNull(configInfoClass);

        // 根据反射注入



    }
}
