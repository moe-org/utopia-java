//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Texture.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics;

import moe.kawayi.org.utopia.core.util.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;

/**
 * 代表一个OpenGL 2D Texture
 */
public class Texture {

    /**
     * 环绕方式
     */
    public enum Wrap {
        REPEAT,
        MIRRORED_REPEAT,
        CLAMP_TO_EDGE,
    }

    /**
     * 过滤器
     */
    public enum Filter {
        NEAREST,
        LINEAR,
    }

    private final int textureId;

    /**
     * 从已有的texture中构造一个texture
     *
     * @param textureId 已有的texture
     */
    public Texture(int textureId) {
        this.textureId = textureId;
    }

    /**
     * 构造一个OpenGL Texture
     *
     * @param width  纹理的宽度
     * @param height 纹理的高度
     * @param pixels 纹理的数据，格式为RGBA
     * @param mipmap 指示是否使用多级纹理
     * @param filter 纹理过滤器选项
     * @param wrap   纹理拉伸选项
     */
    public Texture(
            int width, int height,
            @NotNull ByteBuffer pixels,
            boolean mipmap, Wrap wrap, Filter filter) {
        textureId = GL33.glGenTextures();

        GL33.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        switch (wrap) {
            case REPEAT -> {
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
            }
            case CLAMP_TO_EDGE -> {
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            }
            case MIRRORED_REPEAT -> {
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
            }
        }

        switch (filter) {
            case LINEAR -> {
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            }
            case NEAREST -> {
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                GL33.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            }
        }

        GL33.glTexImage2D(
                textureId, 0, GL11.GL_RGBA,
                width, height, 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
                pixels);

        if (mipmap) {
            GL33.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        }

        GL33.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    /**
     * 获取纹理id
     *
     * @return 纹理id
     */
    public int getTextureId() {
        return textureId;
    }
}
