// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The FontSourceImpl.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle.movabletype;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import moe.kawayi.org.utopia.core.util.NotNull;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.harfbuzz.HarfBuzz;

public class FontSourceImpl implements FontSource {

    private final ByteBuffer buffer;
    private final long harfbuzzBlob;

    /**
     *
     * @param buffer 注意,这个缓冲区必须被独占.
     */
    private FontSourceImpl(@NotNull ByteBuffer buffer) throws HarfbuzzException {
        this.buffer = Objects.requireNonNull(buffer);

        this.harfbuzzBlob = HarfBuzz.hb_blob_create(buffer, HarfBuzz.HB_MEMORY_MODE_READONLY, 0, null);

        if (harfbuzzBlob == 0) {
            throw new HarfbuzzException("hb_blob_create return null");
        }
    }

    public static FontSource fromFile(@NotNull Path path) throws IOException, HarfbuzzException {
        var read = Files.readAllBytes(Objects.requireNonNull(path));

        return fromMemory(read);
    }

    public static FontSource fromMemory(@NotNull byte[] bytes) throws HarfbuzzException {
        var buffer = MemoryUtil.memAlloc(Objects.requireNonNull(bytes).length);

        buffer.put(bytes, 0, bytes.length);
        buffer.rewind();

        return new FontSourceImpl(buffer);
    }

    @Override
    public long getHarfbuzzBlob() {
        return harfbuzzBlob;
    }

    @Override
    public ByteBuffer getBuffer() {
        return this.buffer.asReadOnlyBuffer();
    }

    @Override
    public void close() throws Exception {
        HarfBuzz.hb_blob_destroy(this.harfbuzzBlob);
        MemoryUtil.memFree(this.buffer);
    }
}
