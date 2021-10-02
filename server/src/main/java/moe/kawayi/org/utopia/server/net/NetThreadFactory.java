//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NetThreadFactory.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

import io.netty.util.concurrent.FastThreadLocalThread;
import moe.kawayi.org.utopia.server.util.NotNull;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网络线程工厂
 */
public final class NetThreadFactory implements ThreadFactory {

    /**
     * 线程计数
     */
    @NotNull
    private final AtomicInteger threadCount = new AtomicInteger(0);

    /**
     * 线程名称前缀
     */
    @NotNull
    private final String threadNamePrefix;

    /**
     * 构造一个网络线程工厂
     *
     * @param threadNamePrefix 线程名称前缀。最终线程名称将会在前缀加上已经构造的线程数量。
     *                         如{@code threadNamePrefix}为"thread"。输出则为:"Thread0" "Thread1" "Thread2" 以此类推
     */
    public NetThreadFactory(@NotNull String threadNamePrefix) {
        Objects.requireNonNull(threadNamePrefix, "threadNamePrefix must not be null");

        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Objects.requireNonNull(r, "r must not be null");

        // use FastThreadLocalThread to use FastThreadLocal
        FastThreadLocalThread t = new FastThreadLocalThread(r);
        t.setName(threadNamePrefix + threadCount.getAndAdd(1));

        return t;
    }
}
