//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The AllJavadocTask.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.gradle.tasks;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.javadoc.Javadoc;
import moe.kawayi.org.utopia.gradle.ProjectDefinition;

import java.util.Objects;

public class AllJavadocTask extends Javadoc {

    public void initializeForRootProject(Project project){
        Objects.requireNonNull(project);
        this.setDescription("Collect all subproject javadoc into one directory");
        var subs = project.getSubprojects();

        for(var proj : subs){
            var java = proj.getExtensions().getByType(JavaPluginExtension.class);
            this.getSource().plus(java.getSourceSets().getByName("main").getAllJava());
            this.getClasspath().plus(java.getSourceSets().getByName("main").getCompileClasspath());
        }

        this.setDestinationDir(ProjectDefinition.getJavadocDir());
        this.setFailOnError(true);
        this.getOptions().jFlags("-Dfile.encoding=UTF-8", "-Duser.language=es");
        this.getOptions().encoding("UTF-8");
        this.getOptions().locale("zh-cn");
        this.getOptions().overview(ProjectDefinition.getDocsOverviewFile().toString());
        this.getOptions().setWindowTitle("Utopia Doc");
        this.setTitle("Utopia Javadoc ver." + project.getVersion());
    }
}
