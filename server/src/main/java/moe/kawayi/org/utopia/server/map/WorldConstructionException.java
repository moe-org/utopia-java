//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The WorldConstructionException.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * {@link World}构造函数异常
 */
public class WorldConstructionException extends Exception {


    /**
     * @param msg 异常信息
     * @see Exception#Exception(String)
     */
    public WorldConstructionException(@NotNull String msg) {
        super(msg);
    }

}
