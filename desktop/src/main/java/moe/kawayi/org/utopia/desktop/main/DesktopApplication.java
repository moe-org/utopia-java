//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopApplication.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.LogStream;
import moe.kawayi.org.utopia.core.log.Logger;
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

import java.io.PrintStream;
import java.nio.IntBuffer;

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
     * 最小窗口高度
     */
    public static final int MIN_HEIGHT = 768;

    /**
     * 最小窗口宽度
     */
    public static final int MIN_WIDTH = 1024;

    /**
     * 宽高比
     */
    private static final double W_H_RATIO = (double) MIN_WIDTH / (double) MIN_HEIGHT;

    /**
     * 小图标(32x32)路径。基于Utopia Root。
     */
    public static final String SMALL_ICON_PATH = "Utopia(32x32).png";

    /**
     * 大图标(128x128)路径。基于Utopia Root。
     */
    public static final String LARGE_ICON_PATH = "Utopia(128x128).png";

    /**
     * 日志器
     */
    public final Logger logger = LogManagers.getLogger(this.getClass());

    /**
     * 窗口句柄
     */
    public Window window;

    /**
     * 初始化客户端
     */
    public void init() throws OpenGLException {
        LogStream stream = new LogStream(logger, System.Logger.Level.ERROR);
        GLFWErrorCallback.createPrint(new PrintStream(stream)).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        var builder = new Window.Builder("utopia", 300, 300);
        builder.setOption(() -> {
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        });

        window = builder.build();

        glfwSetKeyCallback(window.getHandle(), (w, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(w, true);
        });

        window.makeCurrentContext();
        window.enableVsync();
        window.show();
    }

    /**
     * 启动客户端
     */
    public void start() {
        GL.createCapabilities();

        GL11.glClearColor(0.0f, 0.0f, 1.0f, 0.0f);

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
