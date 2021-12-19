//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DefaultLogger.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 默认日志器
 */
public class DefaultLogger implements Logger{


    @Override
    @NotNull
    public String getName() {
        return "default logger";
    }

    @Override
    @NotNull
    public void trace(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void trace(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void trace(@NotNull String msg, @NotNull Exception exception) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void debug(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void debug(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void debug(@NotNull String msg, @NotNull Exception exception) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void info(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void info(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void info(@NotNull String msg,@NotNull  Exception exception) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void warn(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void warn(@NotNull String msg, @NotNull Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void warn(@NotNull String msg, @NotNull Exception exception) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void error(@NotNull String msg) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void error(@NotNull String msg,@NotNull  Object... objects) {
        System.out.println(msg);
    }

    @Override
    @NotNull
    public void error(@NotNull String msg, @NotNull Exception exception) {
        System.out.println(msg);
    }
}
