// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The OpenGLException.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import org.lwjgl.opengl.GL11;

/**
 * OpenGL异常
 */
@SuppressWarnings("serial")
public class OpenGLException extends Exception {

    /**
     * 异常详细（通常来自Opengl报告）
     */
    @Nullable
    private final String detail;

    /**
     * 异常信息
     */
    private final String message;

    /**
     * 获取最后一个Opengl错误
     *
     * @return 最后一个opengl错误，永远不返回null
     */
    @NotNull
    private static String getLastError() {
        var code = GL11.glGetError();

        return code == 0 ? "no opengl error got" : "opengl error code: " + code;
    }

    /**
     * 构造一个opengl异常
     *
     * @param message 异常信息
     */
    public OpenGLException(@NotNull String message) {
        super(message);
        detail = getLastError();
        this.message = message;
    }

    /**
     * 构造一个opengl异常
     *
     * @param message 异常信息
     * @param inner   内部异常
     */
    public OpenGLException(@NotNull String message, @NotNull Exception inner) {
        super(message, inner);
        detail = getLastError();
        this.message = message;
    }

    /**
     * 构造一个opengl异常
     *
     * @param message 异常信息
     * @param detail  异常的详细信息，通常是opengl返回的log等
     */
    public OpenGLException(@NotNull String message, @NotNull String detail) {
        super(message);
        this.detail = getLastError() + "\ndetail: " + detail;
        this.message = message;
    }

    @Override
    @NotNull
    public String getMessage() {
        if (detail != null) {
            return this.message + ":" + this.detail;
        } else {
            return this.message;
        }
    }
}
