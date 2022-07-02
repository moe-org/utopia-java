//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Timer.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

/**
 * 高精度计时器
 */
public class Timer {

    /**
     * 默认构造函数
     */
    public Timer() {
    }

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
     * @param sleep       指示是否允许sleep，如果sleep则误差较大，但系统资源消耗较小
     * @return 如果超时，返回超市的纳秒数
     */
    public long waitTurn(long nanoSeconds, boolean sleep) {
        if (getElapsedTime() >= nanoSeconds) {
            return getElapsedTime() - nanoSeconds;
        } else {
            while (getElapsedTime() < nanoSeconds) {
                if (sleep) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // InterruptedException应该是不允许的
                        throw new RuntimeException("thread shouldn't be interrupted.", e);
                    }
                } else {
                    Thread.onSpinWait();
                }
            }
            return 0;
        }
    }
}
