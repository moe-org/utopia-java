//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ProjectDefinition.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.gradle;

import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ProjectDefinition {

    public static final String GROUP_NAME = "moe.kawayi.org";

    public static final String DESKTOP_START_CLASS = "moe.kawayi.org.utopia.desktop.main.DesktopLauncher";

    public static final String VERSION_PROPERTY_FILE = "utopia-version.properties";

    public static final String VERSION_MAJOR_KEY = "UtopiaVersionMajor";

    public static final String VERSION_MINOR_KEY = "UtopiaVersionMinor";

    public static final String VERSION_PATCH_KEY = "UtopiaVersionPatch";

    public static final String VERSION_EXTRA_KEY = "UtopiaVersionExtra";

    //===========================================

    public static final String SNAPSHOT_BUILD_KEY = "SnapshotBuild";

    //===========================================
    public static final int JAVA_VERSION = 20;
    public static final String GUICE_VERSION = "7.0.0";
    public static final String JANSI_VERSION = "2.4.0";
    public static final String LWJGL_VERSION = "3.3.3";
    public static final String LIBGDX_VERSION = "1.12.0";
    public static final String LOG4J2_VERSION = "2.20.0";
    public static final String NETTY_VERSION = "4.1.94.Final";
    public static final String JUNIT_VERSION = "5.9.3";
    public static final String JACKSON_VERSION = "2.15.2";
    public static final String HOCON_VERSION = "1.4.2";
    public static final String JACOCO_VERSION = "0.8.10";
    public static final String CHECKSTYLE_VERSION = "10.12.1";
    public static final String TUNINGFORK_VERSION = "4.0.0";

    //===========================================
    private static final String RELEASE_DIR_NAME = "release";
    private static final String DESKTOP_RELEASE_DIR_NAME = "desktop";
    private static final String SERVER_RELEASE_DIR_NAME = "server";

    //===========================================
    private static File rootDir = new File(".");
    private static Project rootProject = null;

    public static File getReleaseDir(){
        return new File(rootDir,RELEASE_DIR_NAME);
    }

    public static File getDesktopReleaseDir(){
        return new File(getReleaseDir(),DESKTOP_RELEASE_DIR_NAME);
    }

    public static File getServerReleaseDir(){
        return new File(getReleaseDir(),SERVER_RELEASE_DIR_NAME);
    }

    public static File getVersionFile(){
        return new File(rootDir,"info/version.txt");
    }

    public static File getJavadocDir(){
        return new File(rootDir,"docs");
    }

    public static File getIconDir(){
        return new File(rootDir,"icon");
    }

    public static File getAssetsDir(){
        return new File(rootDir,"assets");
    }

    public static File getDocsOverviewFile(){
        return Path.of(rootDir.toString(),"config","overview.html").toFile();
    }

    public static String getAllJavadocJarName(){
        return "utopia-javadoc-" + rootProject.getVersion() + ".jar";
    }

    public static String getAllSourceJarName(){
        return "utopia-source-" + rootProject.getVersion() + ".jar";
    }

    private static String generateVersion() throws IOException{
        var ver = Files.readString(getVersionFile().toPath());

        var subs = ver.split("\\.");

        if(subs.length != 3){
            throw new NumberFormatException("The version number is wrong.");
        }

        majorVersion = Integer.parseInt(subs[0]);
        minorVersion = Integer.parseInt(subs[1]);
        patchVersion = Integer.parseInt(subs[2]);

        if(rootProject.getProperties().containsKey(SNAPSHOT_BUILD_KEY)){
            ver = ver + "-" + rootProject.getProperties().get(SNAPSHOT_BUILD_KEY).toString()
                    + "-SNAPSHOT";
            extraVersion = "SNAPSHOT";
        }
        else{
            extraVersion = "";
        }

        return ver;
    }

    private static String version = null;

    public static int majorVersion;

    public static int minorVersion;

    public static int patchVersion;

    public static String extraVersion;

    public static String getVersion() {
        return version;
    }

    public static void initializeForProject(Project project) throws IOException {
        rootProject = Objects.requireNonNull(project).getRootProject();
        rootDir = rootProject.getRootDir();
        version = generateVersion();
    }

}
