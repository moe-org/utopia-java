//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The AllJavaSourceJar.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.gradle.tasks;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.jvm.tasks.Jar;
import moe.kawayi.org.utopia.gradle.ProjectDefinition;

public class AllJavaSourceJar extends Jar {

    public AllJavaSourceJar(){

    }

    public void initializeForRootProject(Project project){
        this.setDescription("Collect all subproject source code into one jar");

        var subs = project.getSubprojects();

        for(var proj : subs){
            var java = proj.getExtensions().getByType(JavaPluginExtension.class);
            this.getSource().plus(java.getSourceSets().getByName("main").getAllJava());
        }

        this.getDestinationDirectory().set(ProjectDefinition.getReleaseDir());
        this.getArchiveFileName().set(ProjectDefinition.getAllSourceJarName());
    }

}
