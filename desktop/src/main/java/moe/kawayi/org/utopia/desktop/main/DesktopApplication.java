//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopApplication.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.LogStream;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.resource.ResourceManager;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.desktop.graphics.OpenGLException;
import moe.kawayi.org.utopia.desktop.graphics.Program;
import moe.kawayi.org.utopia.desktop.graphics.Window;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

import java.io.IOException;
import java.io.PrintStream;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * LWJGL3程序
 */
public class DesktopApplication {

    /**
     * 默认构造函数
     */
    public DesktopApplication() {
    }

    /**
     * 日志器
     */
    public final Logger logger = LogManagers.getLogger(this.getClass());

    /**
     * 窗口句柄
     */
    private Window window = null;

    /**
     * 使用小图标
     */
    public boolean useSmallIcon = false;

    /**
     * 是否启用向前兼容，即{@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_FORWARD_COMPAT}
     */
    public boolean forwardCompat = false;

    /**
     * 获取游戏主要窗口
     *
     * @return 游戏窗口
     */
    @NotNull
    public Window getMainWindow() {
        // 如果为null则说明窗口尚未初始化
        // 我们抛出异常来提前检查
        if (this.window == null) {
            throw new NullPointerException("main window not initialized");
        }
        return this.window;
    }

    /**
     * 初始化客户端
     *
     * @throws OpenGLException opengl错误
     */
    public void init() throws OpenGLException {
        LogStream stream = new LogStream(logger, System.Logger.Level.ERROR);
        GLFWErrorCallback.createPrint(new PrintStream(stream)).set();

        if (!glfwInit()) {
            throw new OpenGLException("Unable to initialize GLFW");
        }

        var builder = new Window.Builder("utopia", Application.MIN_WIDTH, Application.MIN_HEIGHT);
        builder.setOption(() -> {
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

            if(forwardCompat){
                glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
            }
        });

        try {
            if (useSmallIcon) {
                builder.setIcon(ResourceManager.getPath(Application.SMALL_ICON_PATH));
            } else {
                builder.setIcon(ResourceManager.getPath(Application.LARGE_ICON_PATH));
            }
        } catch (IOException exception) {
            logger.error("couldn't set the icon of the window", exception);
        }

        window = builder.build();

        glfwSetKeyCallback(window.getHandle(), (w, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(w, true);
        });

        window.makeCurrentContext();
        window.enableVsync();
        window.enableAutoViewport();
        window.show();
    }

    /**
     * 启动客户端
     * @throws OpenGLException opengl错误
     */
    public void start() throws OpenGLException {
        GL.createCapabilities();

        GL11.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);

        final var VAO = GL33.glGenVertexArrays();
        GL33.glBindVertexArray(VAO);

        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };

        final var vbo = GL33.glGenBuffers();

        GL33.glBindBuffer(GL_ARRAY_BUFFER, vbo);

        GL33.glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        GL33.glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 3, NULL);
        GL33.glEnableVertexAttribArray(0);

        GL33.glBindBuffer(GL_ARRAY_BUFFER, 0);
        GL33.glBindVertexArray(0);

        Program program = new Program(
"""
#version 330 core
layout (location = 0) in vec3 aPos;

void main()
{
    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
}
""",
"""
#version 330 core
out vec4 FragColor;

void main()
{
    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);
}
"""
        );

        while (!window.isCloseNeeded()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            program.use();
            GL33.glBindVertexArray(VAO);
            glDrawArrays(GL_TRIANGLES, 0, 3);

            window.swapBuffer();
            glfwPollEvents();
        }

        GL33.glDeleteVertexArrays(VAO);
        GL33.glDeleteBuffers(vbo);
        program.delete();

        window.destroy();

        var callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }

        glfwTerminate();
    }

}
