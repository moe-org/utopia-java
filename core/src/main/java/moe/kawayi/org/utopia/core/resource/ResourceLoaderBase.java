//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ResourceLoaderBase.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.resource;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * 基础资源加载器
 *
 * 负责加载jar,file,http,https,sys-path。如果并非这些协议，返回empty。
 *
 * 对于jar,file,http,https将会返回{@link URL#openConnection()}的结果。
 *
 * 对于sys-path，将对host name和path基于当前工作目录进行组合，获取一个路径{@link java.nio.file.Path}。
 */
public class ResourceLoaderBase implements ResourceLoader{
    /**
     * 路径协议名称
     */
    public static final String PROTOCOL = "sys-path";

    @Override
    public String getName() {
        return "Basic Resource Loader";
    }

    @Override
    public Optional<Object> getResource(URL url) throws Exception{
        return switch (url.getProtocol()) {
            case "jar", "file", "http", "https" -> Optional.ofNullable(url.openConnection());
            case PROTOCOL -> Optional.of(Paths.get(".",url.getHost(),url.getPath()));
            default -> Optional.empty();
        };
    }
}
