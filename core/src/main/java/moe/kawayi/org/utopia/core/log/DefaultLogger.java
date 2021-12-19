//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DefaultLogger.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

/**
 * 默认日志器
 */
public class DefaultLogger implements Logger{


    @Override
    public String getName() {
        return "default logger";
    }

    @Override
    public void trace(String msg) {
        System.out.println(msg);
    }

    @Override
    public void trace(String msg, Object... objects) {
        System.out.println(msg);
    }

    @Override
    public void trace(String msg, Exception exception) {
        System.out.println(msg);
    }

    @Override
    public void debug(String msg) {
        System.out.println(msg);
    }

    @Override
    public void debug(String msg, Object... objects) {
        System.out.println(msg);
    }

    @Override
    public void debug(String msg, Exception exception) {
        System.out.println(msg);
    }

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void info(String msg, Object... objects) {
        System.out.println(msg);
    }

    @Override
    public void info(String msg, Exception exception) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg, Object... objects) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg, Exception exception) {
        System.out.println(msg);
    }

    @Override
    public void error(String msg) {
        System.out.println(msg);
    }

    @Override
    public void error(String msg, Object... objects) {
        System.out.println(msg);
    }

    @Override
    public void error(String msg, Exception exception) {
        System.out.println(msg);
    }
}
