//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ProjectDefinition.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.gradle.tasks;

import moe.kawayi.org.utopia.gradle.ProjectDefinition;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.provider.Property;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class ReleaseTask extends DefaultTask {

    private Project project;

    // God save us!
    // From https://stackoverflow.com/questions/29076439/java-8-copy-directory-recursively/60621544#60621544
    private static void copyFolder(Path source, Path target, CopyOption... options)
            throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                Files.createDirectories(target.resolve(source.relativize(dir).toString()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.copy(file, target.resolve(source.relativize(file).toString()), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public ReleaseTask(){
        this.setDescription("A tasks to release utopia");
    }

    private File releasePath = null;

    public void initializeForProject(Project project,File releasePath){
        this.project=project;
        this.releasePath = releasePath;
    }

    private File getDes(File target,File root){
        var des = root.toPath().relativize(target.toPath());

        des = this.releasePath.toPath().resolve(des);

        des.toFile().getParentFile().mkdirs();

        return des.toFile();
    }

    public void releaseFile(File file, File root) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(root);

        final var des = getDes(file,root);

        project.getLogger().info("release file from `{}` to `{}`",
                file,des);

        Files.copy(
                file.toPath(),
                des.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );
    }

    public void releaseDir(File dir,File root) throws IOException {
        Objects.requireNonNull(dir);
        Objects.requireNonNull(root);

        final var des = getDes(dir,root);

        project.getLogger().info("release directory from `{}` to `{}`",
                dir,des);

        copyFolder(
                dir.toPath(),
                des.toPath(),
                null,
                StandardCopyOption.REPLACE_EXISTING
        );
    }
}
