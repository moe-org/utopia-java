//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Window.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics;

import moe.kawayi.org.utopia.core.util.NotNull;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * 代表一个窗口（通常是GLFW创建的）
 */
public class Window {

    private final long handle;

    /**
     * 使用handle初始化此类。窗口通常由builder创建。
     */
    private Window(long handle) {
        this.handle = handle;
    }

    /**
     * 获取窗口句柄
     *
     * @return 窗口句柄
     */
    public long getHandle() {
        return handle;
    }

    /**
     * 对当前窗口调用{@link GLFW#glfwMakeContextCurrent(long)}
     */
    public void makeCurrentContext() {
        glfwMakeContextCurrent(handle);
    }

    /**
     * 调用{@link GLFW#glfwShowWindow(long)}
     */
    public void show() {
        glfwShowWindow(handle);
    }

    /**
     * 启用垂直同步
     */
    public void enableVsync() {
        glfwSwapInterval(1);
    }

    /**
     * 获取窗口的大小
     *
     * @return 数组的第一个元素即窗口宽度，第二个元素为窗口高度
     */
    public int[] getSize() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(handle, pWidth, pHeight);

            return new int[]{pWidth.get(0), pHeight.get(0)};
        }
    }

    /**
     * 交换缓冲区
     */
    public void swapBuffer() {
        GLFW.glfwSwapBuffers(handle);
    }

    /**
     * 检查窗口是否需要关闭
     *
     * @return 如果需要返回true，否则返回false
     */
    public boolean isCloseNeeded() {
        return GLFW.glfwWindowShouldClose(handle);
    }

    /**
     * 销毁窗口
     */
    public void destroy() {
        Callbacks.glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
    }


    /**
     * 窗口构造者
     */
    public static class Builder {
        private String title;
        private int width, height;

        /**
         * 使用此函数来设置窗口
         */
        private Runnable setup;

        /**
         * 新的默认窗口
         */
        public Builder() {
            title = "window";
            width = 300;
            height = 200;
        }

        /**
         * 使用初始化参数构造窗口
         *
         * @param title  标题
         * @param width  宽度
         * @param height 高度
         */
        public Builder(@NotNull String title, int width, int height) {
            if (width <= 0) {
                throw new IllegalArgumentException("width of window less than zero");
            }
            if (height <= 0) {
                throw new IllegalArgumentException("height of window less than zero");
            }

            this.title = Objects.requireNonNull(title);
            this.width = width;
            this.height = height;
        }

        /**
         * 设置标题
         *
         * @param title 窗口标题
         */
        public void setTitle(@NotNull String title) {
            this.title = Objects.requireNonNull(title);
        }

        /**
         * 设置窗口大小
         *
         * @param width  窗口宽度
         * @param height 窗口高度
         */
        public void setSize(int width, int height) {
            if (width <= 0) {
                throw new IllegalArgumentException("width of window less than zero");
            }
            if (height <= 0) {
                throw new IllegalArgumentException("height of window less than zero");
            }

            this.width = width;
            this.height = height;
        }

        /**
         * 设置窗口初始化函数
         * <p>
         * 例如：
         * <pre>
         * {@code
         * window.setOption(() -> {
         *     glfwDefaultWindowHints();
         *     glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
         *     glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
         * });
         * }
         * </pre>
         *
         * @param runnable 可以运行的函数，用于设置窗口选项。
         */
        public void setOption(@NotNull Runnable runnable) {
            this.setup = Objects.requireNonNull(runnable);
        }

        /**
         * 生成窗口
         */
        public Window build() throws OpenGLException {
            glfwDefaultWindowHints();

            this.setup.run();

            var handle = org.lwjgl.glfw.GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);

            if (handle == MemoryUtil.NULL) {
                throw new OpenGLException("failed to create window");
            }

            return new Window(handle);
        }


    }
}
