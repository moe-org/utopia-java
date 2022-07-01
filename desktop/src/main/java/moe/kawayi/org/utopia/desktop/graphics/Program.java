// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Program.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics;

import java.lang.ref.Cleaner;
import java.util.Objects;

import moe.kawayi.org.utopia.core.util.CleanerManager;
import moe.kawayi.org.utopia.core.util.NotNull;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * 着色器
 */
public class Program implements AutoCloseable {

    private int[] programId;

    private final Cleaner.Cleanable cleanable;

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
        int[] nonThisProgramId;
        int fragmentId = GL33.glCreateShader(GL_FRAGMENT_SHADER);

        if (fragmentId == 0) {
            throw new OpenGLException("failed to create fragment shader");
        }
        try {
            int vertexId = GL33.glCreateShader(GL_VERTEX_SHADER);

            if (vertexId == 0) {
                throw new OpenGLException("failed to create vertex shader");
            }

            try {
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
                        throw new OpenGLException(
                                "fail to compile OpenGL vertex shader", GL33.glGetShaderInfoLog(vertexId));
                    }
                }

                // 链接
                nonThisProgramId = new int[] {GL33.glCreateProgram()};
                programId = nonThisProgramId;
                if (nonThisProgramId[0] == 0) {
                    throw new OpenGLException("failed to create OpenGL program");
                }
                try {
                    GL33.glAttachShader(nonThisProgramId[0], fragmentId);
                    GL33.glAttachShader(nonThisProgramId[0], vertexId);

                    GL33.glLinkProgram(nonThisProgramId[0]);

                    // 检查错误
                    try (MemoryStack stack = stackPush()) {
                        var buf = stack.mallocInt(1);
                        GL33.glGetProgramiv(nonThisProgramId[0], GL_LINK_STATUS, buf);

                        if (buf.get(0) == GL_FALSE) {
                            throw new OpenGLException(
                                    "fail to link OpenGL program", GL33.glGetProgramInfoLog(nonThisProgramId[0]));
                        }
                    }
                } catch (Throwable t) {
                    GL33.glDeleteProgram(nonThisProgramId[0]);
                    throw t;
                }
            } catch (Throwable t) {
                GL33.glDeleteShader(vertexId);
                throw t;
            }
        } catch (Throwable t) {
            GL33.glDeleteShader(fragmentId);
            throw t;
        }

        // 注册清洁器
        cleanable = CleanerManager.getCleaner().register(this, () -> {
            if (nonThisProgramId[0] != 0) {
                GL20.glDeleteProgram(nonThisProgramId[0]);
                nonThisProgramId[0] = 0;
            }
        });
    }

    /**
     * 获取着色器程序id
     *
     * @return 着色器程度的id
     */
    public int getProgramId() {
        if (programId[0] == 0) throw new IllegalStateException("use after free");

        return programId[0];
    }

    /**
     * 使用opengl程序
     */
    public void use() {
        if (programId[0] == 0) throw new IllegalStateException("use after free");

        GL33.glUseProgram(programId[0]);
    }

    /**
     * 获取uniform的location
     *
     * @param location location
     * @return 到的location
     * @throws OpenGLException 如果location不存在
     */
    public int getUniform(@NotNull String location) throws OpenGLException {
        if (programId[0] == 0) throw new IllegalStateException("use after free");

        var value = GL33.glGetUniformLocation(programId[0], Objects.requireNonNull(location));

        if (value == -1) {
            throw new OpenGLException("unknown uniform location: " + location);
        } else {
            return value;
        }
    }

    /**
     * 多次调用是允许的。
     */
    @Override
    public void close() {
        cleanable.clean();
    }
}
