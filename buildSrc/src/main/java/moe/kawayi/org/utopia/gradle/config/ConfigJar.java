//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ConfigJar.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.gradle.config;

import moe.kawayi.org.utopia.gradle.ProjectDefinition;
import org.gradle.jvm.tasks.Jar;

import java.io.IOException;

public class ConfigJar {

    public ConfigJar(){

    }

    private static void configJarTaskFor(Jar jar) {
        jar.getArchiveVersion().set(ProjectDefinition.getVersion());
        jar.getArchiveClassifier().set("release");
    }

    public static void configJarForServer(Jar jar){
        configJarTaskFor(jar);

        jar.getArchiveBaseName().set("server");

        jar.getDestinationDirectory().set(
                ProjectDefinition.getServerReleaseDir()
        );

        jar.getManifest().getAttributes().put("Main-Class","moe.kawayi.org.utopia.server.main.Main");
    }

    public static void configJarForDesktop(Jar jar){
        configJarTaskFor(jar);

        jar.getArchiveBaseName().set("desktop");

        jar.getDestinationDirectory().set(
                ProjectDefinition.getDesktopReleaseDir()
        );

        jar.getManifest().getAttributes().put("Main-Class","moe.kawayi.org.utopia.desktop.main.DesktopLauncher");
    }
}
