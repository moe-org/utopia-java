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
import moe.kawayi.org.utopia.core.util.Timer;
import moe.kawayi.org.utopia.desktop.graphics.OpenGLException;
import moe.kawayi.org.utopia.desktop.graphics.Program;
import moe.kawayi.org.utopia.desktop.graphics.Texture;
import moe.kawayi.org.utopia.desktop.graphics.Window;
import moe.kawayi.org.utopia.desktop.util.FpsCounter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL43;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT;
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
     * 是否开启opengl debug模式
     */
    public boolean openglDebug = false;

    /**
     * 是否使用线框模式。这个值支持在运行时进行更改。
     */
    public final AtomicBoolean wireframeMode = new AtomicBoolean(false);

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

            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

            if (forwardCompat) {
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
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(w, true);
            } else if (key == GLFW_KEY_F12 && action == GLFW_RELEASE) {
                this.wireframeMode.set(!this.wireframeMode.get());
            }
        });

        window.enableOpenGL();
        window.enableVsync();
        window.enableAutoViewport();
        window.show();

        if (openglDebug) {
            GL43.glEnable(GL_DEBUG_OUTPUT);

            GL43.glDebugMessageCallback(
                    (source, type, id, severity, length, message, userParam) -> {
                        StringBuilder msg = new StringBuilder();
                        msg.append("OpenGL debug message: ");
                        msg.append("source: ").append(source).append(", ");
                        msg.append("type: ").append(type).append(", ");
                        msg.append("id: ").append(id).append(", ");
                        msg.append("severity: ").append(severity).append(", ");
                        msg.append("length: ").append(length).append(", ");
                        msg.append("message: ").append(message);
                        logger.error("{}", msg.toString());
                    },
                    0);
        }
    }

    /**
     * 启动客户端
     *
     * @throws OpenGLException opengl错误
     */
    public void start() throws OpenGLException {

        GL11.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);

        final var vao = GL33.glGenVertexArrays();
        GL33.glBindVertexArray(vao);

        float[] vertices = {
                0.5f,
                0.5f,
                0.0f,
                1.0f,
                1.0f, // 右上
                0.5f,
                -0.5f,
                0.0f,
                1.0f,
                0.0f, // 右下
                -0.5f,
                -0.5f,
                0.0f,
                0.0f,
                0.0f, // 左下
                -0.5f,
                0.5f,
                0.0f,
                0.0f,
                1.0f // 左上
        };

        int[] indices = {
                0, 1, 3,
                1, 2, 3
        };

        final var vbo = GL33.glGenBuffers();

        GL33.glBindBuffer(GL_ARRAY_BUFFER, vbo);

        GL33.glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        GL33.glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 5, NULL);
        GL33.glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 5, 3 * Float.BYTES);
        GL33.glEnableVertexAttribArray(0);
        GL33.glEnableVertexAttribArray(1);

        final var ebo = GL33.glGenBuffers();

        GL33.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

        GL33.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        GL33.glBindVertexArray(0);
        GL33.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        GL33.glBindBuffer(GL_ARRAY_BUFFER, 0);

        Program program = new Program(
                """
                        #version 330 core
                        layout (location = 0) in vec3 aPos;
                        layout (location = 1) in vec2 aTexCoord;

                        out vec2 TexCoord;

                        void main()
                        {
                            gl_Position = vec4(aPos, 1.0);
                            TexCoord = aTexCoord;
                        }
                        """,
                """
                        #version 330 core
                        out vec4 FragColor;

                        in vec2 TexCoord;

                        uniform sampler2D ourTexture;
                        uniform vec3 textureColor;

                        void main()
                        {
                            FragColor = texture(ourTexture, TexCoord) * vec4(textureColor, 1.0);
                        }
                        """);

        window.getResizeEvent().register((event) -> {
            window.swapBuffer();
        });

        Random r = new Random();

        // 加载我们的纹理
        Texture texture;
        try (var stack = MemoryStack.stackPush()) {
            var xh = stack.mallocInt(1);
            var yh = stack.mallocInt(1);
            var channelHandle = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);

            var image =
                    STBImage.stbi_load(ResourceManager.getPath("texture.jpg").toString(), xh, yh, channelHandle, 4);

            if (image == null) {
                throw new IllegalStateException("failed to load texture");
            }
            try {
                texture = new Texture(
                        xh.get(0), yh.get(0), image, true, Texture.Wrap.MIRRORED_REPEAT, Texture.Filter.LINEAR);

            } finally {
                STBImage.stbi_image_free(image);
            }
        }

        Timer timer = new Timer();
        FpsCounter counter = new FpsCounter();

        while (!window.isCloseNeeded()) {
            counter.tick();
            timer.tick();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            if (wireframeMode.get()) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            } else {
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            }

            program.use();
            GL33.glBindVertexArray(vao);
            texture.bind();

            var location = program.getUniform("textureColor");
            r.setSeed((System.currentTimeMillis() % 2000) + System.nanoTime());
            GL33.glUniform3f(location, r.nextFloat(), r.nextFloat(), r.nextFloat());

            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

            window.swapBuffer();
            glfwPollEvents();
        }
        logger.info("fps: {}", counter.getLastFps());

        GL33.glDeleteVertexArrays(vao);
        GL33.glDeleteBuffers(vbo);
        program.close();

        window.destroy();

        var callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }

        glfwTerminate();
    }
}
