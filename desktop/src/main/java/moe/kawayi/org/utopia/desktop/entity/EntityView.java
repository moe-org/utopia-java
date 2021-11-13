//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Entity.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.entity;

import com.badlogic.gdx.graphics.Texture;
import moe.kawayi.org.utopia.core.util.NotNull;

/**
 * 客户端上的实体。
 *
 * 实现要求线程安全。
 */
public interface EntityView {

    /**
     * 绘图
     * @return 贴图
     */
    @NotNull
    Texture getTexture();


}
