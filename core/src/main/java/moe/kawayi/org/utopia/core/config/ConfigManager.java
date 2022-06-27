//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ConfigManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.config;

import com.typesafe.config.*;
import moe.kawayi.org.utopia.core.config.hocon.HoconConfig;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 配置文件管理器
 */
public final class ConfigManager {

    /**
     * private
     */
    private ConfigManager(){}

    /**
     * 加载配置文件
     * @param path 配置文件路径
     * @return 加载到的配置文件。如果配置文件不存在则返回empty
     * @throws IllegalArgumentException 如果文件不以.conf结尾则抛出
     */
    @NotNull
    public static Optional<Config> loadConfig(@NotNull Path path) throws IllegalArgumentException{
        Objects.requireNonNull(path);

        if(!Files.exists(path)){
            return Optional.empty();
        }
        else{
            if(path.toFile().getName().endsWith(".conf")){
                var options = ConfigParseOptions.defaults();
                options = options.setClassLoader(null);
                options = options.setAllowMissing(false);
                options = options.setIncluder(null);
                options = options.setSyntax(ConfigSyntax.CONF);

                com.typesafe.config.Config config = ConfigFactory.parseFile(path.toFile(),options);

                return Optional.of(new HoconConfig(config));
            }
            else{
                throw new IllegalArgumentException("unknown file type");
            }
        }
    }

    /**
     * 生成一个默认的json配置字符串
     * <br/>
     * 根据输入的configClazz中的static变量生成。不支持嵌套类型。
     * <br/>
     * 如果一个static变量不以"_DEFAULT"结尾，则将该变量视为key
     * <br/>
     * 如果一个static变量已"_DEFAULT"结尾，则将该变量视为变量的名称去除"_DEFAULT"后缀后的变量的value
     * 如:
     * <pre>
     * {@code
     *  public class Config{
     *      public static final String PORT = "a-port";
     *      public static final int PORT_DEFAULT = 1;
     *  }
     * }
     * </pre>
     * 将会生成类似结构的字符串: {@code {"a-port":1}}
     * <br/>
     * 作为key的变量将调用{@link Object#toString()}作为结果
     * <br/>
     * 作为value的变量的类型支持byte,short,int,long,float,double,boolean,String。其他类型将调用{@link Object#toString()}以String储存。
     * <br/>
     * 不支持任何Map,List等复杂类型。
     * <br/>
     * @param configClazz 配置类
     * @return Hocon配置字符串。同时可以被HOCON解析。
     * @throws IllegalAccessException java的反射API所抛出的异常
     */
    @NotNull
    public static String createDefaultHocon(@NotNull Class<?> configClazz) throws java.lang.IllegalAccessException{
        Field[] declaredFields = configClazz.getDeclaredFields();

        var statics = Arrays.stream(declaredFields).toList().stream().filter(
                value -> java.lang.reflect.Modifier.isStatic(value.getModifiers())).collect(Collectors.toList());

        var keys = statics.stream().filter(value -> !value.getName().endsWith("_DEFAULT")).collect(Collectors.toList());

        var values =
                statics.stream().filter(value -> value.getName().endsWith("_DEFAULT")).collect(Collectors.toList());

        // key as field name
        // value[0] as json key
        // value[1] as json value
        HashMap<String,Object[]> hashMap = new HashMap<>();

        for (var field : keys){
            Object[] objs = new Object[2];
            objs[0] = field.get(null);
            objs[1] = null;

            hashMap.put(field.getName(),objs);
        }

        for(var field : values){
            var got = hashMap.get(field.getName().substring(0,field.getName().length() - "_DEFAULT".length()));

            if(got == null){
                continue;
            }

            got[1] = field.get(null);

            hashMap.put(field.getName().substring(0,field.getName().length() - "_DEFAULT".length() - 1),got);
        }

        HashMap<String,Object> keyValues = new HashMap<>();
        hashMap.values().forEach(
                value -> {
                    keyValues.put((String)value[0],value[1]);
                }
        );

        // 序列化
        return ConfigValueFactory.fromMap(keyValues).render(ConfigRenderOptions.concise());
    }
}
