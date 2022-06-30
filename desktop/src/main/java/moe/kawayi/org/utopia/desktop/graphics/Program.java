// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Program.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics;

import java.util.Objects;

import moe.kawayi.org.utopia.core.util.NotNull;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * 着色器
 */
public class Program {

    private final int programId;

    /**
     * 构造一个着色器程序
     *
     * @param vertex   顶点着色器源代码
     * @param fragment 片段着色器源代码
     * @throws OpenGLException 编译时出现错误
     */
    public Program(@NotNull String vertex, @NotNull String fragment) throws OpenGLException {
        Objects.requireNonNull(vertex);
        Objects.requireNonNull(fragment);

        // 编译着色器
        int fragmentId = GL33.glCreateShader(GL_FRAGMENT_SHADER);
        int vertexId = GL33.glCreateShader(GL_VERTEX_SHADER);

        GL33.glShaderSource(fragmentId, fragment);
        GL33.glShaderSource(vertexId, vertex);

        GL33.glCompileShader(fragmentId);
        GL33.glCompileShader(vertexId);

        // 检查错误
        try (MemoryStack stack = stackPush()) {
            var buf = stack.mallocInt(1);
            GL33.glGetShaderiv(fragmentId, GL_COMPILE_STATUS, buf);

            if (buf.get(0) == GL_FALSE) {
                throw new OpenGLException(
                        "fail to compile OpenGL fragment shader", GL33.glGetShaderInfoLog(fragmentId));
            }

            GL33.glGetShaderiv(vertexId, GL_COMPILE_STATUS, buf);

            if (buf.get(0) == GL_FALSE) {
                throw new OpenGLException("fail to compile OpenGL vertex shader", GL33.glGetShaderInfoLog(vertexId));
            }
        }

        // 链接
        programId = GL33.glCreateProgram();
        GL33.glAttachShader(programId, fragmentId);
        GL33.glAttachShader(programId, vertexId);

        GL33.glLinkProgram(programId);

        // 检查错误
        try (MemoryStack stack = stackPush()) {
            var buf = stack.mallocInt(1);
            GL33.glGetProgramiv(programId, GL_LINK_STATUS, buf);

            if (buf.get(0) == GL_FALSE) {
                throw new OpenGLException("fail to link OpenGL program", GL33.glGetProgramInfoLog(programId));
            }
        }
    }

    /**
     * 获取着色器程序id
     *
     * @return 着色器程度的id
     */
    public int getProgramId() {
        return programId;
    }

    /**
     * 使用opengl程序
     */
    public void use() {
        GL33.glUseProgram(programId);
    }

    /**
     * 获取uniform的location
     *
     * @param location location
     * @return 到的location
     */
    public int getUniform(@NotNull String location) {
        return GL33.glGetUniformLocation(programId, Objects.requireNonNull(location));
    }

    /**
     * 删除程序自己
     */
    public void delete() {
        GL20.glDeleteProgram(getProgramId());
    }
}
