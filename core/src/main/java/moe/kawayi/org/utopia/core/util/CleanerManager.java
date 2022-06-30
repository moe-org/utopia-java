//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The CleanerManager.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

import java.lang.ref.Cleaner;
import java.util.ArrayList;

/**
 * 清洁器管理器。
 */
public final class CleanerManager {

    /**
     * 所有清洁器
     */
    private static final ArrayList<Cleaner> CLEANERS = new ArrayList<>();

    /**
     * 最大清洁器数量
     */
    private static int maxCleaner = 1;

    /**
     * 当前清洁器指针
     */
    private static int currentPtr = 0;

    /**
     * private
     */
    private CleanerManager() {
    }

    /**
     * 设置最大清洁器数量
     *
     * @param max 最大清洁器数量
     */
    public static void setMax(final int max) {
        synchronized (CLEANERS) {
            if (max < 0) {
                throw new IllegalArgumentException("max must more than 0");
            }
            maxCleaner = max;

            while (CLEANERS.size() < max) {
                CLEANERS.add(Cleaner.create());
            }

            while (CLEANERS.size() > max) {
                CLEANERS.remove(CLEANERS.size() - 1);
            }
        }
    }

    /**
     * 获取最大清洁器数量
     *
     * @return 最大清洁器数量
     */
    public static int getMax() {
        return maxCleaner;
    }

    /**
     * 获取清洁器。最好每次使用都获取，不持有清洁器。
     *
     * @return 返回一个清洁器
     */
    public static Cleaner getCleaner() {
        setMax(getMax());

        synchronized (CLEANERS) {
            var cleaner = CLEANERS.get(currentPtr);

            currentPtr += 1;

            if (currentPtr >= maxCleaner) {
                currentPtr = 0;
            }

            return cleaner;
        }
    }


}
