// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Timer.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

/**
 * 高精度计时器
 */
public class Timer {

    /**
     * 默认构造函数
     */
    public Timer() {}

    private long lastTickTime = System.nanoTime();

    /**
     * 进行一次tick
     */
    public void tick() {
        lastTickTime = System.nanoTime();
    }

    /**
     * 获取当前时间和上次tick时间的差值
     *
     * @return 差值，单位为纳秒
     */
    public long getElapsedTime() {
        return System.nanoTime() - lastTickTime;
    }

    /**
     * {@link #getElapsedTime()}的高级函数。传入一个预期时间。
     * 如果{@link #getElapsedTime()}短于预期时间，则忙等待到预期时间。
     * 如果{@link #getElapsedTime()}长于预期时间，则返回超出的部分。
     *
     * @param nanoSeconds 此轮回需要消耗的时间数量，单位为纳秒
     * @return 如果超时，返回超时的纳秒数
     */
    public long waitTurn(long nanoSeconds) {
        if (getElapsedTime() >= nanoSeconds) {
            return getElapsedTime() - nanoSeconds;
        } else {
            while (getElapsedTime() < nanoSeconds) {
                Thread.yield();
            }
            return 0;
        }
    }
}
