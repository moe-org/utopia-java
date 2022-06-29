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
import moe.kawayi.org.utopia.desktop.graphics.Window;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
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
     */
    public void start() {
        GL.createCapabilities();

        GL11.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        // 准备我们的代码
        

        while (!window.isCloseNeeded()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            window.swapBuffer();

            glfwPollEvents();
        }

        window.destroy();

        glfwTerminate();

        var callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
            callback.close();
        }
    }

}
