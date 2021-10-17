//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceLoaderBase.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.resource;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.Optional;

/**
 * 基础资源加载器
 *
 * 负责加载jar,file,http,https。如果并非这些协议，返回empty。
 *
 * 将会返回{@link URL#openConnection()}的结果。
 */
public class ResourceLoaderBase implements ResourceLoader{
    @Override
    public String getName() {
        return "Basic Resource Loader";
    }

    @Override
    public Optional<Object> getResource(URL url) throws Exception{
        return switch (url.getProtocol()) {
            case "jar", "file", "http", "https" -> Optional.of(url.openConnection());
            default -> Optional.empty();
        };
    }
}
