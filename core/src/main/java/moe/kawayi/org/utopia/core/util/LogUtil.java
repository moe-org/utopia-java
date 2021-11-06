//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The LogUtil.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

import moe.kawayi.org.utopia.core.resource.ResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.async.AsyncLoggerConfig;
import org.apache.logging.log4j.core.async.AsyncLoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.filter.ThresholdFilter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * log设置
 */
public class LogUtil {

    /**
     * 控制台日志样式
     */
    private static final String CONSOLE_LOG_PATTERN =
            "[%d{HH:mm:ss.SSS}] [%t] " +
            "[%highlight{%-5level}{FATAL=Bright red, ERROR=Red, WARN=Yellow, INFO=Black, DEBUG=Blue, TRACE=Cyan}] " +
            "[%logger{36}]:%msg %exception%n";

    /**
     * 滚动文件输出格式
     */
    private static final String ROLLING_FILE_PATTERN =
            "[%d{HH:mm:ss.SSS}] [%t] [%highlight{%-5level}{${highlight_color}}] [%logger{36}]:%msg%exception%n";

    /**
     * 日志上下文名称
     */
    public static final String CONTEXT_NAME = "utopia-system-log-context";

    /**
     * 默认xml配置
     */
    public static final String XML_CONFIG_FILE =
"""
<?xml version="1.0" encoding="UTF-8"?>

<Configuration>

  <Properties>
    <Property name="rolling-pattern" value=
    "[%d{HH:mm:ss.SSS}] [%t] [%highlight{%-5level}{${highlight_color}}] [%logger{36}]:%msg%exception%n" />
    <Property name="console-pattern" value=\"""" +
        "[%d{HH:mm:ss.SSS}] [%t] " +
        "[%highlight{%-5level}{FATAL=Bright red, ERROR=Red, WARN=Yellow, INFO=Black, DEBUG=Blue, TRACE=Cyan}] " +
        "[%logger{36}]:%msg %exception%n\""+
        """
  />
  </Properties>
  
  <Appenders>
    <RollingFile name="rolling" fileName="logs/latest-log.txt" filePattern="logs/archived-%d{yyyy-MM-dd}-%i.gz" >
        <PatternLayout pattern="${rolling-pattern}"/>
        
        <Policies>
            <TimeBasedTriggeringPolicy interval="6" modulate="true"/>
             <SizeBasedTriggeringPolicy size="32 MB"/>
        </Policies>
        
        <DefaultRolloverStrategy max="20" fileIndex="max" />
        
    </RollingFile>
    
    <console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="${console-pattern}"/>
    </console>
    
  </Appenders>
  
  <Loggers>
    <Root level="trace">
      <AppenderRef ref="rolling"/>
      <AppenderRef ref="console"/>
    </Root>
  </Loggers>
  
</Configuration>
""";

    /**
     * xml配置路径。基于utopia-root
     */
    public static final String XML_CONFIG_FILE_PATH = "log-config.xml";

    /**
     * 配置日志器
     * @throws java.io.IOException 无法设置配置文件
     */
    public static synchronized void configureLog() throws java.io.IOException{

        if(!Files.exists(
                ResourceManager.getPath(XML_CONFIG_FILE_PATH)
        )){
            Files.writeString(
                    ResourceManager.getPath(XML_CONFIG_FILE_PATH),
                    XML_CONFIG_FILE,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE
            );
        }

        Configurator.initialize(
                    CONTEXT_NAME,
                    ClassLoader.getSystemClassLoader(),
                    ResourceManager.getPath(XML_CONFIG_FILE_PATH).toString()
                );

        Configurator.initialize(
                CONTEXT_NAME,
                ResourceManager.getPath(XML_CONFIG_FILE_PATH).toString()
        );
    }

}
