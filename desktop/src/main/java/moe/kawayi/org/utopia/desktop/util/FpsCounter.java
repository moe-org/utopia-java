//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FpsCounter.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.util;

import moe.kawayi.org.utopia.core.util.Timer;

/**
 * FPS统计器
 */
public class FpsCounter {

    /**
     * 计时器
     */
    private final Timer timer = new Timer();

    /**
     * 计数
     */
    private long count = 1;

    /**
     * 上次的帧数
     */
    private long lastCount = 0;

    /**
     * 默认构造函数
     */
    public FpsCounter() {
        timer.tick();
    }

    /**
     * 新的一帧
     */
    public void tick() {
        var elapsed = timer.getElapsedTime();

        if (elapsed >= 1000000000) {
            lastCount = count;
            count = 1;
            timer.tick();
        } else {
            count++;
        }
    }

    /**
     * 获取最近一秒的FPS
     *
     * @return fps
     */
    public long getLastFps() {
        return lastCount;
    }

}
