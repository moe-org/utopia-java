// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Version.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.util;

public final class Version {

    public final int major;

    public final int minor;

    public final int patch;

    @Nullable
    public final String extra;

    public Version(int major, int minor, int patch, @Nullable String extra) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.extra = extra;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch
                + ((extra == null || extra.isBlank() || extra.isEmpty()) ? "" : ("-" + extra));
    }
}
