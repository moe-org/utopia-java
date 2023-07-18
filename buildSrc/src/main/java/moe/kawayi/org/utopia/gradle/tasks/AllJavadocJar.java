//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The AllJavadocJar.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.gradle.tasks;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.jvm.tasks.Jar;
import moe.kawayi.org.utopia.gradle.ProjectDefinition;

import java.util.ArrayList;
import java.util.Objects;

public class AllJavadocJar extends Jar {

    public AllJavadocJar(){

    }

    public void initializeForRootProject(Project project){
        this.setDescription("Collect all subproject javadoc into one jar");

        this.from(
                ProjectDefinition.getJavadocDir().toString()
        );

        this.dependsOn("allJavadoc");
        this.getDestinationDirectory().set(ProjectDefinition.getReleaseDir());
        this.getArchiveFileName().set(ProjectDefinition.getAllJavadocJarName());
    }
}
