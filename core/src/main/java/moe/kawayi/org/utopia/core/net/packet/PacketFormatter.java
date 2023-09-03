// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The PacketFormatter.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.net.packet;

import moe.kawayi.org.utopia.core.util.NotNull;

public interface PacketFormatter<PT extends UbfPacket> {

    @NotNull
    PT parse();

    void write(@NotNull PT pt);
}
