//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The GenerateVersion.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.gradle.tasks;

import moe.kawayi.org.utopia.gradle.ProjectDefinition;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.JavaPluginExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class GenerateVersion extends DefaultTask {

    public GenerateVersion(){}

    public void generate(Project project) throws IOException {
        Objects.requireNonNull(project);

        var java = project.getExtensions().getByType(JavaPluginExtension.class);

        var path =
        project.getProjectDir().toPath().resolve("src/main/resources/" +
                ProjectDefinition.VERSION_PROPERTY_FILE);

        Files.writeString(
                path,
                ProjectDefinition.VERSION_PROPERTY_KEY + "=" + ProjectDefinition.getVersion(),
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE
        );
    }
}
